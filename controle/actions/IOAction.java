/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.controle.actions;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import simulador.modelo.Ambiente;

/**
 *
 * @author Fernando
 */
public abstract class IOAction extends AbstractAction {
    
    public static final String SEPARADOR_ELEMENTOS_LINHA = ",";
    public static final String SEPARADOR_LINHAS = ";";
    protected final Ambiente ambiente;

    public IOAction(String texto, Icon icone, Ambiente ambiente) {
        super(texto, icone);
        this.ambiente = ambiente;
    }
    
}
