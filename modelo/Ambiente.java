/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.modelo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Fernando
 */
// Essa classe representará o ambiente de interesse para a simulação
public class Ambiente {

    public static boolean DEBUG = false;                                         // chave para ativar depuração
    public static float DISTANCIA_PONTOS_PADRAO = 1;                                         // chave para ativar depuração
    public static float POTENCIA_MAX = -15;
    public static float POTENCIA_MIN = -90;
    private final PropertyChangeSupport notificadorListeners = new PropertyChangeSupport(this);  // responsável por implementar o pattern Observer e gerenciar eventos
    private float comprimento = 1;                                                  // comprimento em metros
    private float largura = 1;                                                      // largura em metros
    private float distanciaPontos = 1;                                              // distância entre os pontos de propagação, em metros
    private final Map<Integer, Radio> radios = new HashMap();                                   // radios mapeados no ambiente por seu respectivo id
    private final Map<Integer, Obstaculo> obstaculos = new HashMap();                           // paredes ou objetos mapeados no ambiente por seu respectivo id
    private final CanalComunicacao canal = new CanalComunicacao(this);                                       // canal de propagação
    //private List<PontoReceptor> matrizPontosReceptores = new ArrayList();                               // pontos do ambiente onde o sinal será estimado
    private final Map<Integer, Map<Integer, PontoReceptor>> matrizPontosReceptores = new LinkedHashMap();                               // pontos do ambiente onde o sinal será estimado

    public Ambiente() {
        gerarPontosReceptores();
    }

// Gets & Sets
    public float getComprimento() {
        if (false) {
            System.out.println("Ambiente.getComprimento: " + comprimento);
        }

        return comprimento;
    }

    // Proibido valores negativos
    public final void setComprimento(float comprimento) throws IllegalArgumentException {

        if (DEBUG) {
            System.out.println("Ambiente.setComprimento: " + comprimento);
        }

        if (comprimento <= 0) {
            throw new IllegalArgumentException("Valor nulo ou negativo em Ambiente.setComprimento");
        }

        float antigoValor = this.comprimento;
        this.comprimento = comprimento;

        gerarPontosReceptores();
        notificadorListeners.firePropertyChange("comprimento", antigoValor, comprimento);

    }

    public float getLargura() {

        if (false) {
            System.out.println("Ambiente.getLargura: " + largura);
        }

        return largura;

    }

    // Proibido valores negativos
    public final void setLargura(float largura) throws IllegalArgumentException {

        if (DEBUG) {
            System.out.println("Ambiente.setLargura: " + largura);
        }

        if (largura <= 0) {
            throw new IllegalArgumentException("Valor nulo ou negativo em Ambiente.setLargura");
        }

        float antigoValor = this.largura;
        this.largura = largura;

        gerarPontosReceptores();
        notificadorListeners.firePropertyChange("largura", antigoValor, largura);

    }

    public float getDistanciaPontos() {

        if (DEBUG) {
            System.out.println("Ambiente.getDistanciaPontos: " + distanciaPontos);
        }

        return distanciaPontos;

    }

    // Proibido valores negativos
    public final void setDistanciaPontos(float distanciaPontos) throws IllegalArgumentException {

        if (DEBUG) {
            System.out.println("Ambiente.setDistanciaPontos: " + distanciaPontos);
        }

        if (distanciaPontos <= 0 && distanciaPontos < Math.max(comprimento, largura)) {
            throw new IllegalArgumentException("Valor nulo ou negativo em Ambiente.setDistanciaPontos");
        }

        float antigoValor = this.distanciaPontos;
        this.distanciaPontos = distanciaPontos;

        gerarPontosReceptores();
        notificadorListeners.firePropertyChange("distanciaPontos", antigoValor, distanciaPontos);

    }

    public Map<Integer, Radio> getRadios() {

        if (DEBUG) {
            System.out.println("Ambiente.getRadios: " + radios.size() + " radios");
        }

        return radios;

    }

    public Map<Integer, Obstaculo> getObstaculos() {

        if (DEBUG) {
            System.out.println("Ambiente.getObstaculos: " + obstaculos.size() + " obstaculos");
        }

        return obstaculos;

    }

    public Map<Integer, Map<Integer, PontoReceptor>> getMatrizPontosReceptores() {
        return matrizPontosReceptores;
    }
    
    public CanalComunicacao getCanal() {
        return canal;
    }

    public String getModeloPropagacao() {
        return canal.getModeloPropagacao().getNome();
    }

    public void setModeloPropagacao(String nomeModelo) {
        canal.setModeloPropagacao(canal.getModelosPropagacao().get(nomeModelo));
    }

    //public void adicionarRadio(float x, float y) throws IllegalArgumentException {
    public void adicionarRadio(Radio radio) throws IllegalArgumentException {

        if (DEBUG) {
            System.out.println("Ambiente.adicionarRadio");
        }

        //Radio radio = new Radio(x, y);
        radio.adicionarListener("transmissao", canal);                           // Ambiente observa mudanças em Radio
        radios.put(radio.getId(), radio);

        notificadorListeners.addPropertyChangeListener("comprimento", radio);   // Radio observa mudança em Ambiente
        notificadorListeners.addPropertyChangeListener("largura", radio);       // Radio observa mudança em Ambiente
        notificadorListeners.firePropertyChange("radios", null, radio);

    }

    public void removerRadio(Radio radio) {

        if (DEBUG) {
            System.out.println("Ambiente.removerRadio");
        }

        radios.remove(radio.getId());
        notificadorListeners.firePropertyChange("radios", radio, null);

    }

    //public void adicionarObstaculo(float x1, float y1, float x2, float y2) throws IllegalArgumentException {
    public void adicionarObstaculo(Obstaculo obstaculo) throws IllegalArgumentException {

        if (DEBUG) {
            System.out.println("Ambiente.adicionarObstaculo");
        }

        //Obstaculo obstaculo = new Obstaculo(x1, y1, x2, y2);
        obstaculos.put(obstaculo.getId(), obstaculo);

        notificadorListeners.addPropertyChangeListener("comprimento", obstaculo);  // Radio observa mudança em Ambiente
        notificadorListeners.addPropertyChangeListener("largura", obstaculo);      // Radio observa mudança em Ambiente
        notificadorListeners.firePropertyChange("obstaculos", null, obstaculo);

    }

    public void removerObstaculo(Obstaculo obstaculo) {

        if (DEBUG) {
            System.out.println("Ambiente.removerObstaculo");
        }

        obstaculos.remove(obstaculo.getId());
        notificadorListeners.firePropertyChange("obstaculos", obstaculo, null);

    }

    public void limparObjetos() {

        if (DEBUG) {
            System.out.println("Ambiente.limpar");
        }
        
        interromperTransmissao();

        radios.clear();
        obstaculos.clear();
        //interromperTransmissao();
        notificadorListeners.firePropertyChange("objetos", this, null);

    }

    public final void gerarPontosReceptores() {
        criarPontos();
        notificadorListeners.firePropertyChange("pontosReceptores", null, matrizPontosReceptores);
    }

    // Função responsável por estabelecer pontos no ambiente para estimar o sinal transmitido.
    // Os pontos são separados por 'distanciaPontos' em ambos os eixos
    // A quantidade de pontos varia conforme o valor da variavel 'distanciaPontos'.
    private void criarPontos() {

        if (DEBUG) {
            System.out.println("Ambiente.criarPontos");
        }

        //JOptionPane.showMessageDialog(null, "criando pontos...");
        //float x, y;
        float x = 0;
        float y = 0;
        
        int contadorLinha = 0;
        int contadorColuna = 0;
        //ArrayList<PontoReceptor> linhaPontos;
        Map<Integer, PontoReceptor> linhaPontos;

        // Enquanto y não ultrapassa o comprimento do ambiente...
        while (y < comprimento) {

            linhaPontos = new LinkedHashMap();

            // Enquanto x não ultrapassa a largura do ambiente...
            while (x < largura) {
                //System.out.println("distancia: "+distanciaPontos);
                // novo colunaPontos é criado em (x, y)
                PontoReceptor ponto = new PontoReceptor(x, y);
                linhaPontos.put(contadorColuna, ponto);
                // x é incrementado em 'distanciaPontos'
                x = new BigDecimal(String.valueOf(x)).add(
                                new BigDecimal(distanciaPontos), MathContext.DECIMAL32
                        ).floatValue();
                
                contadorColuna++;

            }

            matrizPontosReceptores.put(contadorLinha, linhaPontos);
            // recomeça o deslocamento de x (variável na dimensão 'largura')
            x = 0;
            // y é incrementado em 'distanciaPontos'
            y = new BigDecimal(String.valueOf(y)).add(
                    new BigDecimal(distanciaPontos), MathContext.DECIMAL32
            //new MathContext(2, RoundingMode.HALF_DOWN)
            ).floatValue();
            
            contadorColuna = 0;
            contadorLinha++;            

        }

    }

//    private void receberSinal(SinalRadioFrequencia sinal, Point2D localizacaoTx) {
//
//        if (DEBUG) {
//            System.out.println("Ambiente.receberSinal: "+sinal);
//        }
//
//        float distancia;
//
//        // para cada colunaPontos de propagação no ambiente...
////        for (PontoReceptor colunaPontos : matrizPontosReceptores) {
////        for (Map.Entry<Integer, Map<Integer, PontoReceptor>> linhaPontos : matrizPontosReceptores.parRadio()) {
//            
//  //          for (Map.Entry<Integer, PontoReceptor> colunaPontos : linhaPontos.getValue().parRadio()) {
//                
//    //            PontoReceptor ponto = colunaPontos.getValue();
//
//                if (sinal != null) {
//                    //System.out.println("sinal != null");
//                    SinalRadioFrequencia copiaSinal = sinal.clonar();
//                    // calcula-se a distancia entre o Transmissor e o Ponto
//                    //distancia = (float) localizacaoTx.distance(ponto.getLocalizacao());
//                //System.out.println("distancia: "+distancia);
//                    // o sinal sofre atenuação de acordo com a distância
//                    canal.atenuarSinal(copiaSinal, localizacaoTx);
//                // colunaPontos recebe o sinal, caso a potência do novo sinal seja maior do
//                    // que antigo
//                    ponto.receberSinal(copiaSinal);
//                } else {
//                    ponto.receberSinal(sinal);
//                }
//
//            }
//
//        }
//
//        notificadorListeners.firePropertyChange("transmissao", null, matrizPontosReceptores);
//
//    }

    public void interromperTransmissao() {
        
        if (true) {
            System.out.println("Ambiente.interromperTransmissao");
        }

        for (Map.Entry<Integer, Map<Integer, PontoReceptor>> linhaPontos : matrizPontosReceptores.entrySet()) {
            for (Map.Entry<Integer, PontoReceptor> colunaPontos : linhaPontos.getValue().entrySet()) {
                colunaPontos.getValue().receberSinal(null);
            }
        }
        
        notificadorListeners.firePropertyChange("pontosRx", this, null);            
        
//        for (Map.Entry<Integer, Radio> parRadio : radios.entrySet()) {
//            notificadorListeners.firePropertyChange("transmissao", parRadio.getValue(), null);            
//        }       
        

    }

    // Método para adicionar interessados nas mudanças em Ambiente
    public void adicionarListener(String nomePropriedade, PropertyChangeListener listener) {

        if (DEBUG) {
            System.out.println("Ambiente.adicionarObserver: " + listener.getClass().getSimpleName());
        }

        notificadorListeners.addPropertyChangeListener(nomePropriedade, listener);

    }

    // Método executado quando alguma propriedade de interesse é alterada em
    // algum objeto observado
//    @Override
//    public void propertyChange(PropertyChangeEvent evt) {
//
//        //System.out.println("Ambiente.propertyChange");
//
//        switch (evt.getPropertyName()) {
//
//            case "transmissao":
//
//                if (DEBUG) {
//                    System.out.println("Ambiente.propertyChange: " + evt.getPropertyName());
//                }
//
//                // Tratando o processo de transmissão notificado por um Transmissor
//                SinalRadioFrequencia sinal;
//                Conteudo transmissor;
//                Point2D localizacaoTx;
//
//                sinal = (SinalRadioFrequencia) evt.getNewValue();
//                transmissor = (Conteudo) evt.getSource();
//                localizacaoTx = (Point2D) transmissor.getLocalizacao();
//
//                receberSinal(sinal, localizacaoTx);
//
//                break;
//
//        }
//
//    }

    // Interface necessária para objetos que representem algo dentro do ambiente
    public static interface Conteudo {

        // O mínimo é a localização do objeto. A origem (colunaPontos de referência) é
        public Object getLocalizacao();
    }

}
