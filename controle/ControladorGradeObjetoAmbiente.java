/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.controle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import simulador.visao.GradePropriedades;
import simulador.visao.JanelaPrincipal;
import simulador.visao.ObjetoAmbiente2D;
import simulador.visao.PainelDesign;

/**
 *
 * @author Fernando
 */
public class ControladorGradeObjetoAmbiente implements Controlador, PropertyChangeListener {

    public static boolean DEBUG = false;
    private GradePropriedades gradeObjetoAmbiente;

    @Override
    public void estabelecerControle(JanelaPrincipal janelaPrincipal) {
        
        this.gradeObjetoAmbiente = janelaPrincipal.getGradeObjetoAmbiente();
        
        PainelDesign painelDesign = janelaPrincipal.getPainelDesign();
        
        painelDesign.addPropertyChangeListener("objetoSelecionado", this);
       // painelDesign.addPropertyChangeListener("flagSimulacao", this);
        
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (DEBUG) {
            System.out.println("GerenciadorGradeObjetoAmbiente.propertyChange: "+evt.getPropertyName());
        }
        
        switch (evt.getPropertyName()) {

            case "objetoSelecionado":

                ObjetoAmbiente2D obj2D = (ObjetoAmbiente2D) evt.getNewValue();
                
                if (evt.getNewValue() != null) {
                    gradeObjetoAmbiente.exibirPropriedadesObjeto(obj2D.getObjetoAmbiente());
                } else {
                    gradeObjetoAmbiente.limparDados();
                }

                break;

        }

    }

}
