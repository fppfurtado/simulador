/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.controle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import simulador.modelo.Ambiente;
import simulador.visao.JanelaPrincipal;
import simulador.visao.PainelInfoAmbiente;

/**
 *
 * @author Fernando
 */
public class ControladorPainelInfoAmbiente implements Controlador, PropertyChangeListener{
    
    public static boolean DEBUG = false;
    private final Ambiente ambiente;
    private PainelInfoAmbiente painelInfo;
    
    public ControladorPainelInfoAmbiente(Ambiente ambiente) {        
        //this.painelInfo = painelInfo;
        this.ambiente = ambiente;
    }
    
    @Override
    public void estabelecerControle(JanelaPrincipal janelaPrincipal) {
    
        this.painelInfo = janelaPrincipal.getPainelInfoAmbiente();
        
        ambiente.adicionarListener("comprimento", this);
        ambiente.adicionarListener("largura", this);
        ambiente.adicionarListener("distanciaPontos", this);
        
        painelInfo.atualizarInformacaoLetreiro(PainelInfoAmbiente.INFO_COMPRIMENTO, ambiente.getComprimento());
        painelInfo.atualizarInformacaoLetreiro(PainelInfoAmbiente.INFO_LARGURA, ambiente.getLargura());
        painelInfo.atualizarInformacaoLetreiro(PainelInfoAmbiente.INFO_DISTANCIA_PONTOS, ambiente.getDistanciaPontos());
    
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        
        String infoAmbiente = String.valueOf(evt.getNewValue());
        
        switch(evt.getPropertyName()){
        
            case "comprimento":                
                painelInfo.atualizarInformacaoLetreiro(PainelInfoAmbiente.INFO_COMPRIMENTO, infoAmbiente+" m");
                break;
            case "largura":
                painelInfo.atualizarInformacaoLetreiro(PainelInfoAmbiente.INFO_LARGURA, infoAmbiente+" m");
                break;
            case "distanciaPontos":
                painelInfo.atualizarInformacaoLetreiro(PainelInfoAmbiente.INFO_DISTANCIA_PONTOS, infoAmbiente+" m");
                break;
        
        }
        
    }
    
}
