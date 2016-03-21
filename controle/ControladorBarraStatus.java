/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.controle;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import simulador.modelo.Ambiente;
import simulador.visao.JanelaPrincipal;
import simulador.visao.PainelDesign;

/**
 *
 * @author Fernando
 */
public class ControladorBarraStatus implements Controlador, PropertyChangeListener{
    
    public static boolean DEBUG = false;
    private final Ambiente ambiente;
    private final JLabel rotulo = new JLabel("Pronto");
    private final DecimalFormat df = new DecimalFormat("#.##");        
    
    public ControladorBarraStatus(Ambiente ambiente) {
        
        if(DEBUG){
            System.out.println("new GerenciadorBarraStatus");
        }
        
        this.ambiente = ambiente;
        
    }    
    
    @Override
    public void estabelecerControle(JanelaPrincipal janelaPrincipal) {     
        
        JPanel barraStatus = janelaPrincipal.getBarraStatus();
        PainelDesign painelDesign = janelaPrincipal.getPainelDesign();
        
        barraStatus.add(rotulo);        
        painelDesign.addPropertyChangeListener("moved", this);
        //controladorDesign.adicionarListener("moved", this);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        
        if(DEBUG){
            System.out.println("GerenciadorBarraStatus.propertyChange");
        }
        
        PainelDesign painelDesign = (PainelDesign)evt.getSource();
        Point p = (Point)evt.getNewValue();
        
        float xMetro = (float)ControladorDesign.converterCoordenadas(
                p.x,
                painelDesign.getImagem().getWidth(),
                ambiente.getLargura()
        );
        float yMetro = (float)ControladorDesign.converterCoordenadas(
                p.y,
                painelDesign.getImagem().getHeight(),
                ambiente.getComprimento()
        );
        
        rotulo.setText("comprimento: "+df.format(yMetro)+"m - largura: "+df.format(xMetro)+" m");
        
    }
    
}
