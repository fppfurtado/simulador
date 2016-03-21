/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.modelo;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import simulador.modelo.propagacao.ChanAndRazaqpur;
import simulador.modelo.propagacao.FreeSpacePathLoss;
import simulador.modelo.propagacao.LogNormal;
import simulador.modelo.propagacao.ModeloEmpiricoPropagacao;
import simulador.modelo.propagacao.SeidelAndRappaport;

/**
 *
 * @author Fernando
 */
// Classe responsável por modelar o canal de comunicação em um
// Sistema Wireless
public class CanalComunicacao implements PropertyChangeListener {

    public static boolean DEBUG = false;
    private final PropertyChangeSupport notificadorListeners = new PropertyChangeSupport(this);  // responsável por implementar o pattern Observer e gerenciar eventos
    private final Ambiente ambiente;
    private final Map<String, ModeloEmpiricoPropagacao> modelosPropagacao = new LinkedHashMap();
    private ModeloEmpiricoPropagacao modeloPropagacao;

    public CanalComunicacao(Ambiente ambiente) {

        if (DEBUG) {
            System.out.println("new CanalPropagacao");
        }

        this.ambiente = ambiente;
        this.modeloPropagacao = new FreeSpacePathLoss();

        //ambiente.adicionarListener("transmissao", this);

        List<ModeloEmpiricoPropagacao> listaModelos = new ArrayList();

        FreeSpacePathLoss modeloFSPL = new FreeSpacePathLoss();

        // Definindo Modelos de Propagação
        listaModelos.add(new FreeSpacePathLoss());
        listaModelos.add(new LogNormal(modeloFSPL));
        listaModelos.add(new ChanAndRazaqpur(modeloFSPL));
        listaModelos.add(new SeidelAndRappaport(modeloFSPL));

        for (ModeloEmpiricoPropagacao modeloPropagacao : listaModelos) {
            modelosPropagacao.put(modeloPropagacao.getNome(), modeloPropagacao);
        }

    }

    public ModeloEmpiricoPropagacao getModeloPropagacao() {

        if (DEBUG) {
            System.out.println("CanalPropagacao.getModeloPropagacao: " + modeloPropagacao.getNome());
        }

        return modeloPropagacao;

    }

    public void setModeloPropagacao(ModeloEmpiricoPropagacao modeloPropagacao) {

        if (DEBUG) {
            System.out.println("CanalPropagacao.setPontosPropagacao: " + modeloPropagacao.getNome());
        }

        this.modeloPropagacao = modeloPropagacao;

    }

    public Map<String, ModeloEmpiricoPropagacao> getModelosPropagacao() {
        return modelosPropagacao;
    }

    // Método responsável modificar o sinal de acordo com a perda calculada pelo modelo de propagação
    public void atenuarSinal(SinalRadioFrequencia sinal, Point2D pontoTx) {

        if (DEBUG) {
            System.out.println("CanalComunicacao.atenuarSinal: " + modeloPropagacao.getNome());
        }

        Map<Integer, Map<Integer, PontoReceptor>> matrizPontosRx = ambiente.getMatrizPontosReceptores();

        for (Map.Entry<Integer, Map<Integer, PontoReceptor>> linhaPontosRx : matrizPontosRx.entrySet()) {

            Map<Integer, PontoReceptor> colunasPontoRx = linhaPontosRx.getValue();

            for (Map.Entry<Integer, PontoReceptor> parPontoRx : colunasPontoRx.entrySet()) {

                PontoReceptor pontoRx = parPontoRx.getValue();
                //System.out.println("sinal = " + sinal);

                if (sinal != null) {

                    //Map<PontoReceptor, Integer> mapaIntersecaoParedes = obterIntersecaoObstaculos(pontoTx, pontoRx);
                    List<Obstaculo> listaIntersecaoObstaculos = obterIntersecaoObstaculos(pontoTx, pontoRx);
                    //for(Obstaculo obstaculo : listaIntersecaoObstaculos) 

                    //for (Map.Entry<PontoReceptor, Integer> parIntersecao : mapaIntersecaoParedes.entrySet()) {
                        //PontoReceptor pontoRx = parIntersecao.getKey();
                    ModeloEmpiricoPropagacao copiaModeloPropagacao = this.modeloPropagacao.clonar();

                    SinalRadioFrequencia copiaSinal = sinal.clonar();

                       // int numParedes = parIntersecao.getValue();
                    //System.out.println(numParedes + " paredes encontradas");
                    float distancia = (float) pontoTx.distance(pontoRx);
                    float perda = copiaModeloPropagacao.obterPerdaPropagacao(
                            distancia, copiaSinal.getFrequencia(), listaIntersecaoObstaculos, null);
                    float potenciaAtenuada = copiaSinal.getPotencia() - perda;
                    // nova potencia, agora atenuada            
                    copiaSinal.setPotencia(potenciaAtenuada);
                    pontoRx.receberSinal(copiaSinal);

                   // }
                } else {

                    //PontoReceptor pontoRx = parPontoRx.getValue();
                    pontoRx.receberSinal(sinal);

                }

            }

        }

        notificadorListeners.firePropertyChange("transmissao", this, sinal);

    }

    private List<Obstaculo> obterIntersecaoObstaculos(Point2D pontoTx, PontoReceptor pontoRx) {

        Map<Integer, Obstaculo> mapObstaculos = ambiente.getObstaculos();
        // Map<Integer, Map<Integer, PontoReceptor>> matrizPontosRx = ambiente.getMatrizPontosReceptores();
        //Map<PontoReceptor, Integer> mapaIntersecaoObstaculos = new LinkedHashMap();
        List<Obstaculo> listaIntersecao = new ArrayList();

        //for (Map.Entry<Integer, Map<Integer, PontoReceptor>> linhaPontosRx : matrizPontosRx.entrySet()) {
          //  Map<Integer, PontoReceptor> colunasPontoRx = linhaPontosRx.getValue();
           // for (Map.Entry<Integer, PontoReceptor> parPontoRx : colunasPontoRx.entrySet()) {
               // PontoReceptor pontoRx = parPontoRx.getValue();
        //int contadorParedes = 0;
        for (Map.Entry<Integer, Obstaculo> parObstaculo : mapObstaculos.entrySet()) {

            Line2D linhaPontos = new Line2D.Float(pontoTx, pontoRx);
            Line2D linhaObstaculo = parObstaculo.getValue().getLocalizacao();

            if (linhaPontos.intersectsLine(linhaObstaculo)) {
                //contadorParedes++;
                listaIntersecao.add(parObstaculo.getValue());
            }

        }

                //mapaIntersecaoObstaculos.put(pontoRx, contadorParedes);
          //  }
     //   }
        return listaIntersecao;

    }

    // Método para adicionar interessados nas mudanças em Ambiente
    public void adicionarListener(String nomePropriedade, PropertyChangeListener listener) {

        if (DEBUG) {
            System.out.println("CanalComunicacao.adicionarObserver: " + listener.getClass().getSimpleName());
        }

        notificadorListeners.addPropertyChangeListener(nomePropriedade, listener);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch (evt.getPropertyName()) {

            case "transmissao":

                if (DEBUG) {
                    System.out.println("CanalComunicacao.propertyChange: " + evt.getPropertyName());
                }

                SinalRadioFrequencia sinal = (SinalRadioFrequencia) evt.getNewValue();

                Radio transmissor;
                Point2D localizacaoTx;

                if (evt.getOldValue() != null) {
                    // Tratando o processo de transmissão notificado por um Transmissor
                    transmissor = (Radio) evt.getOldValue();
                    localizacaoTx = (Point2D) transmissor.getLocalizacao();

                } else {
                    transmissor = null;
                    localizacaoTx = null;
                }

                atenuarSinal(sinal, localizacaoTx);

                break;

        }

    }

}
