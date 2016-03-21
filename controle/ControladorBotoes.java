/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import simulador.modelo.Ambiente;
import simulador.modelo.ObjetoAmbiente;
import simulador.modelo.Obstaculo;
import simulador.modelo.Radio;
import simulador.visao.BarraFerramentas;
import simulador.visao.JanelaPrincipal;
import simulador.visao.ObjetoAmbiente2D;
import simulador.visao.PainelDesign;

/**
 *
 * @author Fernando
 */
public class ControladorBotoes implements Controlador, ActionListener, PropertyChangeListener {

    public static boolean DEBUG = false;
    private final Ambiente ambiente;
    private ObjetoAmbiente objSelecionado;

    public ControladorBotoes(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    @Override
    public void estabelecerControle(JanelaPrincipal janelaPrincipal) {
        
        AbstractButton btSimular = janelaPrincipal.getBtSimular();
        AbstractButton btLimpar = janelaPrincipal.getBtLimpar();
        AbstractButton btExcluir = janelaPrincipal.getBarraFerramentas().obterReferencia(BarraFerramentas.BT_EXCLUIR);
        PainelDesign painelDesign = janelaPrincipal.getPainelDesign();
        
        btSimular.addActionListener(this);
        btLimpar.addActionListener(this);
        btExcluir.addActionListener(this);
        painelDesign.addPropertyChangeListener("objetoSelecionado", this);
        //painelDesign.addPropertyChangeListener("objetos2D", this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (DEBUG) {
            System.out.println("GerenciadorBotoes.actionPerformed: " + e.getActionCommand());
        }

        String comando = e.getActionCommand();

        switch (comando) {

            case "simular":

                Map<Integer, Radio> radios = ambiente.getRadios();

                ambiente.interromperTransmissao();

                for (Map.Entry<Integer, Radio> entrySet : radios.entrySet()) {
                    entrySet.getValue().transmitirSinal();
                }

                break;

            case "limpar":

                ambiente.limparObjetos();
                break;

            case "excluir":

                try {
                    if (objSelecionado instanceof Radio) {
                        ambiente.removerRadio((Radio) objSelecionado);
                    } else {
                        ambiente.removerObstaculo((Obstaculo) objSelecionado);
                    }
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, "Não foi possível excluir o objeto!");
                    ex.printStackTrace();
                }

                break;

        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (DEBUG) {
            System.out.println("GerenciadorBotoes.propertyChange: " + evt.getPropertyName());
        }
        
        switch (evt.getPropertyName()) {

           // case "radios2D":
            //case "obstaculos2D":
           case "objetoSelecionado":
            
                if(evt.getNewValue() != null){
                    ObjetoAmbiente2D obj2D = (ObjetoAmbiente2D) evt.getNewValue();
                    objSelecionado = (ObjetoAmbiente)obj2D.getObjetoAmbiente();
                } else {
                    objSelecionado = null;
                }
                break;

        }

    }

}
