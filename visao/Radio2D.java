/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.visao;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.ImageIcon;

/**
 *
 * @author Fernando
 */
public class Radio2D extends ObjetoAmbiente2D{

    public static boolean DEBUG = false;
    private static final AtomicInteger contadorId = new AtomicInteger(0);
    private static final int TAMANHO = 30;
    private static final int GAP_CENTRALIZAR_ICONE = TAMANHO/2;
    private static final int COR_COM_FOCO = Color.RED.getRGB();
    private final ImageIcon icone = IconesFactory.criarIconeRadio(TAMANHO);
    private boolean foco = false;

    public Radio2D(Point p) {
        
        super(
                contadorId.getAndIncrement(),
                new Rectangle(p.x-GAP_CENTRALIZAR_ICONE, p.y-GAP_CENTRALIZAR_ICONE, TAMANHO, TAMANHO)
        );
        //this.objOrigem = origem;        
        
        if(DEBUG){
            System.out.println("radio criado em "+p);
        }
        
        if(DEBUG){
            System.out.println("new Radio2D");
        }
        
    }   

    @Override
    public void pintarObjeto(Graphics contextoGrafico){
        
        if(DEBUG){
            System.out.println("Radio2D.pintarObjeto");            
        }        
        
        icone.paintIcon(null, contextoGrafico, forma.getBounds().x, forma.getBounds().y);            
        
        Graphics2D contexto2D = (Graphics2D) contextoGrafico;
        
        if(foco){
            contexto2D.setStroke(new BasicStroke(1));
            contexto2D.setColor(new Color(COR_COM_FOCO));
            contexto2D.drawRect(
                    forma.getBounds().x,
                    forma.getBounds().y,
                    forma.getBounds().width,
                    forma.getBounds().width
            );
        }
        
    }

//    @Override
//    public Object getObjetoOrigem() {
//        
//        if(DEBUG){
//            System.out.println("Radio2D.getObjetoOrigem");
//        }
//        
//        return objOrigem;
//        
//    }

    @Override
    public void setFoco(boolean opcao) {
        this.foco = opcao;
    }

//    @Override
//    public boolean intercepta(Shape obj) {
//        if (obj instanceof Rectangle) {
//            return this.intersects((Rectangle)obj);
//        } else if(obj instanceof Line2D){
//            return this.intersectsLine((Line2D)obj);
//        }
//        return false;
//    }

}
