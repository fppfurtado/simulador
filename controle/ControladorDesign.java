/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.controle;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedHashMap;
import java.util.Map;
import simulador.modelo.Ambiente;
import simulador.modelo.Obstaculo;
import simulador.modelo.PontoReceptor;
import simulador.modelo.Radio;
import simulador.visao.BarraFerramentas;
import simulador.visao.JanelaPrincipal;
import simulador.visao.ObjetoAmbiente2D;
import simulador.visao.Obstaculo2D;
import simulador.visao.Radio2D;
import simulador.visao.PainelDesign;

/**
 *
 * @author Fernando
 */
public class ControladorDesign implements Controlador {

    public static boolean DEBUG = false;
    private final PropertyChangeSupport notificadorListeners = new PropertyChangeSupport(this);
    private final Ambiente ambiente;
    private PainelDesign painelDesign;
    private ObjetoAmbiente2D objetoSelecionado = null;

    public ControladorDesign(Ambiente ambiente) {

        if (DEBUG) {
            System.out.println("new ControladorDesign");
        }

        // this.painelDesign = painelDesign;
//        this.btExcluir = janelaPrincipal.getBarraFerramentas().obterReferencia(BarraFerramentas.BT_EXCLUIR);
        this.ambiente = ambiente;

    }

    @Override
    public void estabelecerControle(JanelaPrincipal janelaPrincipal) {

        if (DEBUG) {
            System.out.println("ControladorDesign.estabelecerControle");
        }

        AmbienteListener ambienteListener = new AmbienteListener();

        ambiente.adicionarListener("pontosReceptores", ambienteListener);
        ambiente.adicionarListener("radios", ambienteListener);
        ambiente.adicionarListener("obstaculos", ambienteListener);
        ambiente.adicionarListener("objetos", ambienteListener);
        ambiente.adicionarListener("pontosRx", ambienteListener);
        ambiente.getCanal().adicionarListener("transmissao", ambienteListener);

        //btExcluir.addActionListener(this);
        PainelDesignListener painelDesignListener = new PainelDesignListener();

        this.painelDesign = janelaPrincipal.getPainelDesign();

        painelDesign.addPropertyChangeListener(painelDesignListener);
        
        gerarCelulasPainel();
        painelDesign.pintarCelulas();

    }

    protected static double converterCoordenadas(double dimensao, double limiteEntrada, double limiteSaida) {

        if (false) {
            System.out.println("ControladorDesign.converterCoordenadas");
        }

        return (dimensao * (limiteSaida / limiteEntrada));

    }

    private void gerarCelulasPainel() {

        if (DEBUG) {
            System.out.println("ControladorDesign.gerarCelulasPainel");
        }

        int larguraImagem = painelDesign.getImagem().getWidth();
        int alturaImagem = painelDesign.getImagem().getHeight();
        
        int deslocamentoX = Math.round(ambiente.getDistanciaPontos() * larguraImagem / ambiente.getLargura());
        int deslocamentoY = Math.round(ambiente.getDistanciaPontos() * alturaImagem / ambiente.getComprimento());

        Map<Integer, Map<Integer, PontoReceptor>> mapaPontosReceptores = ambiente.getMatrizPontosReceptores();
        Map<Integer, Map<Integer, PainelDesign.Celula>> mapCelulasPainel = painelDesign.getMatrizCelulas();

        mapCelulasPainel.clear();

        for (Map.Entry<Integer, Map<Integer, PontoReceptor>> linhaPontos : mapaPontosReceptores.entrySet()) {

            Map<Integer, PainelDesign.Celula> linhaCelulas = new LinkedHashMap();
            int linha = linhaPontos.getKey();

            for (Map.Entry<Integer, PontoReceptor> colunaPontos : linhaPontos.getValue().entrySet()) {

                PainelDesign.Celula cel = new PainelDesign.Celula();
                
                float xPonto = colunaPontos.getValue().x;
                float yPonto = colunaPontos.getValue().y;
                
                int xCel = (int)Math.round(converterCoordenadas(
                        xPonto,
                        ambiente.getLargura(),
                        larguraImagem
                ));
                
                int yCel = (int)Math.round(converterCoordenadas(
                        yPonto,
                        ambiente.getComprimento(),
                        alturaImagem
                ));

                //System.out.println("ponto(" + ponto.x + ", " + ponto.y + ")");
                int coluna = colunaPontos.getKey();

                cel.setBounds(xCel, yCel, deslocamentoX, deslocamentoY);
                linhaCelulas.put(coluna, cel);

                //System.out.println("celula(" + cel.x + ", " + cel.y + ")");
            }

            mapCelulasPainel.put(linha, linhaCelulas);

        }

        //painel.setCelulas(celulas);
    }

    private void atualizarCelulasPainel() {
        atualizarValoresCelulas();
        definirCorCelulas();
    }
    
    private void atualizarValoresCelulas() {

        if (DEBUG) {
            System.out.println("ControladorDesign.atualizarValoresCelulas");
        }

        Map<Integer, Map<Integer, PainelDesign.Celula>> mapaCelulas = painelDesign.getMatrizCelulas();

        for (Map.Entry<Integer, Map<Integer, PainelDesign.Celula>> linhaCelulas : mapaCelulas.entrySet()) {

            for (Map.Entry<Integer, PainelDesign.Celula> colunaCelulas : linhaCelulas.getValue().entrySet()) {

                int linhaPonto = linhaCelulas.getKey();
                int colunaPonto = colunaCelulas.getKey();

                PontoReceptor ponto = ambiente.getMatrizPontosReceptores().get(linhaPonto).get(colunaPonto);
                PainelDesign.Celula cel = colunaCelulas.getValue();

                if (ponto.getSinal() != null) {
                    cel.adicionarValor("potencia", ponto.getSinal().getPotencia());
                    cel.adicionarValor("sensibilidade", ponto.getSensibilidade());
                } else {
                    cel.getValores().clear();
                }

            }

        }

    }

    private void definirCorCelulas() {

        if (DEBUG) {
            System.out.println("ControladorDesign.definirCorCelulas");
        }

        Map<Integer, Map<Integer, PainelDesign.Celula>> mapaCelulas = painelDesign.getMatrizCelulas();

        for (Map.Entry<Integer, Map<Integer, PainelDesign.Celula>> linhaCelulas : mapaCelulas.entrySet()) {

            for (Map.Entry<Integer, PainelDesign.Celula> colunaCelulas : linhaCelulas.getValue().entrySet()) {

                PainelDesign.Celula cel = colunaCelulas.getValue();

                Color corFundo = mapearCorPotencia(cel.obterValor("potencia"), cel.obterValor("sensibilidade"));
                cel.setCorFundoRGB(corFundo.getRGB());
                cel.setCorBordaRGB(corFundo.getRGB());

            }

        }

    }

    private Color mapearCorPotencia(Float potencia, Float sensibilidade) {

        if (DEBUG) {
            System.out.println("ControladorDesign.mapearCorPotencia: " + potencia + ", " + sensibilidade);
        }

        if (potencia != null) {

            if (potencia < sensibilidade) {
                //            System.out.println("retorno: BRANCO");
                return Color.WHITE;
            }

            int VERMELHO, AZUL;
            float x1, y1, x2, y2, a, b, h;

            // Cores no Sistema HSV/HSB
            VERMELHO = 0;
            AZUL = 240;

            // Para mapear valores de potencia em valores de cores, vamos a função
            // linear correspondente a esse mapeamento
            //Definido componentes da equação da reta reduzida
            x1 = Ambiente.POTENCIA_MIN;                                         // X1
            y1 = AZUL;                                                              // Y1
            x2 = Ambiente.POTENCIA_MAX;                                         // X2
            y2 = VERMELHO;                                                          // Y2
            a = (y2 - y1) / (x2 - x1);                                                // Coeficiente Angular
            b = y1 - a * x1;                                                          // Coeficiente Linear

            // Matiz
            h = (a * potencia + b);

            return new Color(Color.HSBtoRGB(h / 360, 1, 1));

        } else {
            return new Color(PainelDesign.Celula.COR_FUNDO_PADRAO);
        }

    }
    
    private void adicionarRadioAmbiente(Point localizacao) {
    
        Point2D pontoMetros = new Point2D.Float(
                            (float) converterCoordenadas(localizacao.x,
                                    painelDesign.getImagem().getWidth(),
                                    ambiente.getLargura()
                            ),
                            (float) converterCoordenadas(localizacao.y,
                                    painelDesign.getImagem().getHeight(),
                                    ambiente.getComprimento()
                            )
                    );

                    //Point2D pontoMetros = converterCoordenadas(pontoMouse.x, pontoMouse.y);
                    Radio radio = new Radio((float) pontoMetros.getX(), (float) pontoMetros.getY());

                    ambiente.adicionarRadio(radio);
    
    }
    
    private void adicionarObstaculoAmbiente(Point pInicial, Point pFinal) {
    
        Point2D p1Metros = new Point2D.Float(
                            (float) converterCoordenadas(pInicial.x,
                                    painelDesign.getImagem().getWidth(),
                                    ambiente.getLargura()
                            ),
                            (float) converterCoordenadas(pInicial.y,
                                    painelDesign.getImagem().getHeight(),
                                    ambiente.getComprimento()
                            )
                    );

                    Point2D p2Metros = new Point2D.Float(
                            (float) converterCoordenadas(pFinal.x,
                                    painelDesign.getImagem().getWidth(),
                                    ambiente.getLargura()
                            ),
                            (float) converterCoordenadas(pFinal.y,
                                    painelDesign.getImagem().getHeight(),
                                    ambiente.getComprimento()
                            )
                    );

                    Obstaculo obstaculo = new Obstaculo(
                            (float) p1Metros.getX(),
                            (float) p1Metros.getY(),
                            (float) p2Metros.getX(),
                            (float) p2Metros.getY()
                    );

                   // ambiente.interromperTransmissao();
                    ambiente.adicionarObstaculo(obstaculo);
    
    }
    
    private void adicionarRadioPainel(Radio radio) {
    
                        Point2D localizacaoRadio = radio.getLocalizacao();

                        Point pontoPainel = new Point(
                                (int) converterCoordenadas(
                                        localizacaoRadio.getX(),
                                        ambiente.getLargura(),
                                        painelDesign.getImagem().getWidth()
                                ),
                                (int) converterCoordenadas(
                                        localizacaoRadio.getY(),
                                        ambiente.getComprimento(),
                                        painelDesign.getImagem().getHeight()
                                )
                        );

                        Radio2D r2D = new Radio2D(pontoPainel);
                        r2D.setObjetoAmbiente(radio);

                        painelDesign.adicionarRadio2D(r2D);
    
    }
    
    private void adicionarObstaculoPainel(Obstaculo obstaculo) {
    
        Line2D localObstaculo = obstaculo.getLocalizacao();

                        Point p1Painel = new Point(
                                (int) converterCoordenadas(localObstaculo.getX1(),
                                        ambiente.getLargura(),
                                        painelDesign.getImagem().getWidth()
                                ),
                                (int) converterCoordenadas(localObstaculo.getY1(),
                                        ambiente.getComprimento(),
                                        painelDesign.getImagem().getHeight()
                                )
                        );

                        Point p2Painel = new Point(
                                (int) converterCoordenadas(localObstaculo.getX2(),
                                        ambiente.getLargura(),
                                        painelDesign.getImagem().getWidth()
                                ),
                                (int) converterCoordenadas(localObstaculo.getY2(),
                                        ambiente.getComprimento(),
                                        painelDesign.getImagem().getHeight()
                                )
                        );

                        Obstaculo2D o2D = new Obstaculo2D(p1Painel, p2Painel);
                        o2D.setObjetoAmbiente(obstaculo);

                        painelDesign.adicionarObstaculo2D(o2D);
    
    }

    protected void adicionarListener(String nomePropriedade, PropertyChangeListener listener) {

        if (DEBUG) {
            System.out.println("ControladorDesign.adicionarListener");
        }

        notificadorListeners.addPropertyChangeListener(nomePropriedade, listener);

    }

    class PainelDesignListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {

            if (DEBUG) {
                System.out.println("ControladorDesign.PainelDesignListener.propertyChange: " + evt.getPropertyName());
            }

            String comando = evt.getPropertyName();

            switch (comando) {

                case BarraFerramentas.BT_RADIO:

                    Point pontoMouse = (Point) evt.getNewValue();                    
                    adicionarRadioAmbiente(pontoMouse);
                    
                    break;

                case BarraFerramentas.BT_OBSTACULO:

                    Point pInicial = (Point) evt.getOldValue();
                    Point pFinal = (Point) evt.getNewValue();
                    
                    adicionarObstaculoAmbiente(pInicial, pFinal);

                    break;

                case "objetos2D":

                    painelDesign.selecionarObjeto2D((ObjetoAmbiente2D) evt.getNewValue());
                    ambiente.interromperTransmissao();
                    break;

                case "objetoSelecionado":

                    objetoSelecionado = (ObjetoAmbiente2D) evt.getNewValue();
                    break;

                case "moved":

                    if (painelDesign.isFlagSimulacao()) {
                        int[] coordCel = painelDesign.buscarCelula((Point) evt.getNewValue());
                        PainelDesign.Celula cel = painelDesign.getMatrizCelulas().get(coordCel[0]).get(coordCel[1]);
                        painelDesign.setToolTipText("Potência: " + cel.obterValor("potencia") + "dBm");
                        //painelDesign.setToolTipText("cel(" + coordCel[0] + ", " + coordCel[1] +")");
                    }

                    break;

                case "flagSimulacao":

                    if (!painelDesign.isFlagSimulacao()) {
                        painelDesign.setToolTipText(null);
                    }

                    break;

            }

        }

    }

    class AmbienteListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {

            if (DEBUG) {
                System.out.println("ControladorDesign.AmbienteListener.propertyChange: " + evt.getPropertyName());
            }

            String nomePropriedade = evt.getPropertyName();

            switch (nomePropriedade) {

                case "radios":

                    if (evt.getNewValue() != null) {

                        Radio radio = (Radio) evt.getNewValue();
                        adicionarRadioPainel(radio);

//                        //TESTES
//                        int[] coord = painelDesign.buscarCelula(pontoPainel);
//                        painelDesign.getMapaCelulas().get(coord[0]).get(coord[1]).setCorFundoRGB(Color.CYAN.getRGB());
//                        //TESTES

                    } else {
                        painelDesign.removerRadio2D((Radio2D) objetoSelecionado);
                    }

                    //painel.setFlagSimulacao(false);
                    break;

                case "obstaculos":

                    if (evt.getNewValue() != null) {

                        Obstaculo obstaculo = (Obstaculo) evt.getNewValue();
                        adicionarObstaculoPainel(obstaculo);

                    } else {
                        painelDesign.removerObstaculo2D((Obstaculo2D) objetoSelecionado);
                    }

                    //painel.setFlagSimulacao(false);
                    break;

                case "objetos":
                    painelDesign.limparObjetos2D();
                    //painel.setFlagSimulacao(false);
                    //atualizarCelulasPainel();
                    break;

                case "transmissao":
                case "pontosRx":
                    //painel.setFlagSimulacao(true);
                    //atualizarValoresCelulas();
                    if (evt.getNewValue() != null) {
                        painelDesign.setFlagSimulacao(true);
                    } else {
                        System.out.println("Transmissao Interrompida!");
                        painelDesign.setFlagSimulacao(false);
                    }

                    atualizarCelulasPainel();
                    //definirCorCelulas();
                    break;

                case "pontosReceptores":
                    gerarCelulasPainel();
                    painelDesign.setFlagSimulacao(false);
                    break;

            }

            painelDesign.pintarCelulas();
            
//            if(painelDesign.isFlagSimulacao())
//                painelDesign.depurarPinturaCelulas();
            
            painelDesign.pintarObjetos2D();
            painelDesign.repaint();
            painelDesign.atualizarFrameInicial();

        }

    }

}
