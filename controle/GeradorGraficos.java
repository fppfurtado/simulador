/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.controle;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import org.jfree.chart.JFreeChart;
import simulador.modelo.Ambiente;
import simulador.visao.BarraFerramentas;
import simulador.visao.GradeProjecao;
import simulador.visao.JanelaGraficos;
import simulador.visao.JanelaPrincipal;
import simulador.visao.PainelDesign;
import simulador.visao.Radio2D;

/**
 *
 * @author Fernando
 */
public class GeradorGraficos implements Controlador, PropertyChangeListener {

    //private final Ambiente ambiente;
    public static boolean DEBUG = true;
    private final Ambiente ambiente;
    private PainelDesign painelDesign;
    //private final GradeProjecao gradeProjecao;

    public GeradorGraficos(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    private void criarGraficoRadiais(Map<Integer, Point> pontosIniciais, Point pontoFinal) {

        if (DEBUG) {
            System.out.println("GeradorGraficos.criarGraficosRadiais");
        }

        Map<String, Map<Float, Float>> dadosRadiais = new HashMap();

        for (Map.Entry<Integer, Point> parRadioSelecionado : pontosIniciais.entrySet()) {

            //int[] pontoIncial = painelDesign.buscarCelula(parRadioSelecionado.getValue());
            Map<Float, Float> dadosRadial = obterValoresCelulasRadial(parRadioSelecionado.getValue(), pontoFinal);
            dadosRadiais.put("Radio " + parRadioSelecionado.getKey(), dadosRadial);

        }

        JFreeChart grafico = GraficoCategorias.gerarGrafico(dadosRadiais);
        JanelaGraficos janelaGrafico = new JanelaGraficos(grafico);
        janelaGrafico.setVisible(true);

    }

    private Map<Float, Float> obterValoresCelulasRadial(Point pontoInicial, Point pontoFinal) {

        if (DEBUG) {
            System.out.println("GeradorGraficos.obterValoresCelulasRadial");
        }

       // System.out.println("pontoInicial: ("+pontoInicial.x+", "+pontoInicial.y+")");
       // System.out.println("pontoFinal: ("+pontoFinal.x+", "+pontoFinal.y+")");
        float distanciaMaxPixels = (float) new Point(0, 0).distance(new Point(painelDesign.getImagem().getWidth(), painelDesign.getImagem().getHeight()));
        float distanciaMaxMetros = (float) new Point2D.Float(0, 0).distance(new Point2D.Float(ambiente.getLargura(), ambiente.getComprimento()));

        Map<Float, Float> valoresCelulasRadial = new HashMap();

        Line2D radial = new Line2D.Float(pontoInicial, pontoFinal);
        Rectangle boxRadial = radial.getBounds();

        int[] celRadialIncial = painelDesign.buscarCelula(new Point(boxRadial.x, boxRadial.y));
        int[] celRadialFinal = painelDesign.buscarCelula(new Point((int)boxRadial.getMaxX(), (int)boxRadial.getMaxY()));
        
       // System.out.println("celRadialInicial["+celRadialIncial[0]+"]["+celRadialIncial[1]+"]");
       // System.out.println("celRadialFinal["+celRadialFinal[0]+"]["+celRadialFinal[1]+"]");
        
        int linIncialRadial = celRadialIncial[0];
        int colIncialRadial = celRadialIncial[1];
        int linFinalRadial = celRadialFinal[0];
        int colFinalRadial = celRadialFinal[1];        
        
        int[] coordCelPontoInicial = painelDesign.buscarCelula(pontoInicial);
        PainelDesign.Celula celPontoInicial = painelDesign.getMatrizCelulas().get(
                coordCelPontoInicial[0]).get(coordCelPontoInicial[1]);

        for (int i = linIncialRadial; i <= linFinalRadial; i++) {
            for (int j = colIncialRadial; j <= colFinalRadial; j++) {

                PainelDesign.Celula cel = painelDesign.getMatrizCelulas().get(i).get(j);

                  //System.out.println("cel["+i+"]["+j+"] é interceptada ?");
                
                  if (radial.intersects(cel)) {

                    float distanciaPontosPixels = (float) new Point(celPontoInicial.x, celPontoInicial.y)
                            .distance(new Point(cel.x, cel.y));
                    float distanciaPontosMetros = (float) ControladorDesign.converterCoordenadas(
                            distanciaPontosPixels,
                            distanciaMaxPixels,
                            distanciaMaxMetros
                    );
                    
                    //System.out.println("sim");
                   // System.out.println("potencia: "+cel.obterValor("potencia"));
                   // System.out.println("distancia: "+distanciaPontosMetros);
                    
                    
                    //cel.setCorFundoRGB(Color.BLUE.getRGB());
                    
                    valoresCelulasRadial.put(
                            distanciaPontosMetros,//cel.obterValor("distancia"),
                            cel.obterValor("potencia")
                    );

                }

            }
        }

        return valoresCelulasRadial;

    }

    @Override
    public void estabelecerControle(JanelaPrincipal janelaPrincipal) {

        this.painelDesign = janelaPrincipal.getPainelDesign();

        GradeProjecao gradeProjecao = janelaPrincipal.getGradeProjecao();
        gradeProjecao.addPropertyChangeListener(BarraFerramentas.BT_SELECAO, this);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (DEBUG) {
            System.out.println("GeradorGraficos.propertyChange: " + evt.getPropertyName());
        }

        switch (evt.getPropertyName()) {

            case BarraFerramentas.BT_SELECAO:

                Map<Integer, Point> pontosRadios = new HashMap();

                Map<Integer, Boolean> valoresCheckbox = (Map) evt.getNewValue();
                Map<Integer, Radio2D> radios2D = painelDesign.getRadios2D();

                for (Map.Entry<Integer, Boolean> parCheckbox : valoresCheckbox.entrySet()) {
                    System.out.println("parCheckbox[" + parCheckbox.getKey() + ", " + parCheckbox.getValue() + "]");

                    if (parCheckbox.getValue()) {

                        Radio2D r2D = radios2D.get(parCheckbox.getKey());
                        Rectangle forma = (Rectangle) r2D.getForma();
                        Point localizacao = new Point((int) forma.getCenterX(), (int) forma.getCenterY());
                        System.out.println("radioCheckBox - localizacao: (" + localizacao.x + ", " + localizacao.y + ")");
                        pontosRadios.put(parCheckbox.getKey(), localizacao);

                    }
                }

                //int[] coordCelPontoClicado = painelDesign.buscarCelula((Point) evt.getOldValue());
                //PainelDesign.Celula cel = painelDesign.getMapaCelulas().get(coordCelPontoClicado[0]).get(coordCelPontoClicado[1]);
                //Point pontoFinal = new Point(cel.x, cel.y);
                Point pontoClicado = (Point) evt.getOldValue();

                criarGraficoRadiais(pontosRadios, pontoClicado);

                break;

        }

    }

}
