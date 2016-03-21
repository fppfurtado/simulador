/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.controle.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import simulador.Simulador;
import simulador.visao.BarraFerramentas;
import simulador.visao.IconesFactory;

/**
 *
 * @author Fernando
 */
public class NovoAction extends AbstractAction {

    public static boolean DEBUG = false;
    private final Simulador simulador;

    public NovoAction(Simulador simulador) {
        super("Novo", IconesFactory.criarIconeNovo(BarraFerramentas.TAM_BOTAO));
        this.simulador = simulador;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        simulador.reiniciar();
    }
    
}
