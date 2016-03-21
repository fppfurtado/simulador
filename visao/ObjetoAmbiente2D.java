/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.visao;

import java.awt.Graphics;
import java.awt.Shape;

/**
 *
 * @author Fernando
 */
public abstract class ObjetoAmbiente2D {    
    //public Object getObjetoOrigem();
    //private final AtomicInteger contadorId= new AtomicInteger(0);
    protected final int id;
    protected final Shape forma;
    protected Object objetoAmbiente;

    public ObjetoAmbiente2D(int id, Shape forma) {
        this.id = id;
        this.forma = forma;
    }

    public int getId() {
        return id;
    }

    public Shape getForma() {
        return forma;
    }   

    public Object getObjetoAmbiente() {
        return objetoAmbiente;
    }

    public void setObjetoAmbiente(Object objetoAmbiente) {
        this.objetoAmbiente = objetoAmbiente;
    }
    
    public abstract void setFoco(boolean opcao);
    public abstract void pintarObjeto(Graphics contextoGrafico);
    //public boolean intercepta(Shape obj) ;
}
