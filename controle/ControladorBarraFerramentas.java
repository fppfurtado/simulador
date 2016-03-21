/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.controle;

import javax.swing.Action;
import simulador.visao.BarraFerramentas;
import simulador.visao.BarraMenu;
import simulador.visao.JanelaPrincipal;

/**
 *
 * @author Fernando
 */
public class ControladorBarraFerramentas implements Controlador {

    public static boolean DEBUG = false;
  
    @Override
    public void estabelecerControle(JanelaPrincipal janelaPrincipal) {
        
        BarraMenu barraMenu = janelaPrincipal.getBarraMenu();
        
        Action novoAction = barraMenu.obterReferencia(BarraMenu.SM_NOVO).getAction();
        Action abrirAction = barraMenu.obterReferencia(BarraMenu.SM_ABRIR).getAction();
        Action salvarAction = barraMenu.obterReferencia(BarraMenu.SM_SALVAR).getAction();
        
        BarraFerramentas barraFerramentas = janelaPrincipal.getBarraFerramentas();    
        
        barraFerramentas.obterReferencia(BarraFerramentas.BT_NOVO).setAction(novoAction);
        barraFerramentas.obterReferencia(BarraFerramentas.BT_ABRIR).setAction(abrirAction);
        barraFerramentas.obterReferencia(BarraFerramentas.BT_SALVAR).setAction(salvarAction);       
        
    }

}
