/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.modelo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Resource;

/**
 *
 * @author Fernando
 */
// Uma classe abstrata para representar de forma genérica um objeto físico 
// qualquer dentro do ambiente
public abstract class ObjetoAmbiente implements Ambiente.Conteudo{
    
    // responsável por implementar o pattern Observer e gerenciar eventos
    protected final PropertyChangeSupport notificadorListeners = new PropertyChangeSupport(this);

    public void adicionarListener(String nomePropriedade, PropertyChangeListener listener){    
        notificadorListeners.addPropertyChangeListener(listener);    
    }

}
