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
import java.awt.geom.Line2D;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Fernando
 */
public class Obstaculo2D extends ObjetoAmbiente2D{
    
    public static boolean DEBUG = false;
    private static final AtomicInteger contadorId = new AtomicInteger(0);
    public static final int COR_SEM_FOCO = Color.BLACK.getRGB();
    public static final int COR_COM_FOCO = Color.RED.getRGB();
    public static final int ESPESSURA = 4;    
    private int corRGB = COR_SEM_FOCO;
    
    public Obstaculo2D(Point p1, Point p2) {
        
        super(
                contadorId.getAndIncrement(),
                new Line2D.Float(p1, p2)
        );
        //this.objOrigem = origem;
        
        if(DEBUG){
            System.out.println("new Obstaculo2D");
        }
        
    }
    
    public int getCorRGB() {
        
        if(DEBUG){
            System.out.println("Obstaculo2D.getCorRGB");
        }
        
        return corRGB;
        
    }

    public void setCorRGB(int corRGB) {
        
        if(DEBUG){
            System.out.println("Obstaculo2D.setCorRGB");
        }
        
        this.corRGB = corRGB;
        
    }

//    @Override
//    public Object getObjetoOrigem() {
//        
//        if(DEBUG){
//            System.out.println("Obstaculo2D.getObjetoOrigem");
//        }
//        
//        return objOrigem;
//        
//    }

    @Override
    public void pintarObjeto(Graphics contextoGrafico) {
        
        if(DEBUG){
            System.out.println("Obstaculo2D.pintarObjeto");
        }
        
        Graphics2D contexto2D = (Graphics2D) contextoGrafico;
        
        contexto2D.setStroke(new BasicStroke(ESPESSURA));
        contexto2D.setColor(new Color(corRGB));
        contexto2D.draw(forma);        
        //contexto2D.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
        
    }

    @Override
    public void setFoco(boolean opcao) {
        if(opcao){
            corRGB = COR_COM_FOCO;
        } else{
            corRGB = COR_SEM_FOCO;
        }
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
