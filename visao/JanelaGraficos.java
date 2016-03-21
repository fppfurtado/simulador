/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.visao;

import java.awt.HeadlessException;
import javax.swing.JFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author Fernando
 */
public class JanelaGraficos extends JFrame {
    
    public static boolean DEBUG = false;
    private final ChartPanel painel;

    public JanelaGraficos(JFreeChart grafico) throws HeadlessException {
        //super("")
        painel = new ChartPanel(grafico);
        add(painel);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setResizable(false);              
        pack();
        setLocationRelativeTo(null);
        //setVisible(true);
        
    }  
    
    
}
