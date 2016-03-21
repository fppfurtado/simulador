/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.controle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Fernando
 */
public class GraficoCategorias {

    public static boolean DEBUG = true;
    
    //public static JFreeChart gerarGrafico(Map<Integer, List<PainelDesign.Celula>> mapaCelulas, PainelDesign painelDesign, Ambiente ambiente) {
    public static JFreeChart gerarGrafico(Map<String, Map<Float, Float>> dados) {

        if (DEBUG) {
            System.out.println("GraficoRadiais.gerarGrafico");
        }
        
        String tituloGrafico = "Potência do Sinal Vs Distância";
        String tituloEixoX = "Distância (m)";
        String tituloEixoY = "Potência (dBm)";

        //XYSeriesCollection dadosGrafico = criarDataset(mapaCelulas, painelDesign, ambiente);
        XYSeriesCollection dadosGrafico = criarDataset(dados);

        JFreeChart grafico = ChartFactory.createXYLineChart(
                tituloGrafico,
                tituloEixoX,
                tituloEixoY,
                dadosGrafico,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        //renderizarGrafico(grafico);

        return grafico;

    }
    
    private static XYSeriesCollection criarDataset(Map<String, Map<Float, Float>> dadosRadios) {

        if (DEBUG) {
            System.out.println("GraficoRadiais.criarDataset");
        }
  
        XYSeriesCollection colecaoParesXY = new XYSeriesCollection();

        for (Map.Entry<String, Map<Float, Float>> parDadosRadios : dadosRadios.entrySet()) {
            
            Map<Float, Float> dadosRadio = parDadosRadios.getValue();
            XYSeries paresXY = new XYSeries(parDadosRadios.getKey());
            
            for (Map.Entry<Float, Float> parDadosRadio : dadosRadio.entrySet()) {
                
                float d = parDadosRadio.getKey();
                float p = parDadosRadio.getValue();
                
                //System.out.println("d = "+d);
                //System.out.println("p = "+p);
                
                 paresXY.add(
                         parDadosRadio.getKey(),
                         parDadosRadio.getValue()
                 );
                
            }
            
         colecaoParesXY.addSeries(paresXY);

        }

        return colecaoParesXY;

    }
    



    private static void renderizarGrafico(JFreeChart grafico) {

        if (DEBUG) {
            System.out.println("GraficoRadiais.renderizarGrafico");
        }
        
        XYLineAndShapeRenderer renderizador = new XYLineAndShapeRenderer();
        int qtdSeries, aux;

        qtdSeries = grafico.getXYPlot().getDataset().getSeriesCount();
        //aux = (Color.WHITE.getRGB() - Color.BLACK.getRGB())/qtdSeries;   
        aux = 360 / qtdSeries;

        //System.out.println("aux = "+aux);
        for (int i = 0; i < qtdSeries; i++) {
            renderizador.setSeriesPaint(i, new Color(Color.HSBtoRGB((aux * i) / 360.0f, 1.0f, 1.0f)));
            renderizador.setSeriesStroke(i, new BasicStroke(1.0f));
        }
        
        //renderizador.setSeriesLinesVisible(0, false);

        grafico.getXYPlot().setRenderer(renderizador);

    }

}
