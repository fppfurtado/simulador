/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.modelo;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Resource;

/**
 *
 * @author Fernando
 *
 */
// Essa classe foi criada para representar uma parede ou um objeto qualquer
// presente no ambiente, que seja considerado um obstaculo na comunicação wireless
public class Obstaculo extends ObjetoAmbiente implements PropertyChangeListener {
    
    public static boolean DEBUG = false;                                         // chave para ativar a depuração
    private static final AtomicInteger contadorId = new AtomicInteger(0);       // Mecanismo estático gerador de numeros identificadores
    @Resource
    private final int id = contadorId.getAndIncrement();                        // numero identificador do objeto
    private Line2D localizacao;                                                 // localização modelada por dois pontos extremos de um segmento de reta
    @Resource
    private TipoObstaculo tipo;                                                 // parede, objeto, etc..
    @Resource
    private TipoMaterial tipoMaterial;                                          // madeira, aluminio, concreto, etc...

    public Obstaculo(float x1, float y1, float x2, float y2) throws IllegalArgumentException{

        if (DEBUG) {
            System.out.println("new Obstaculo(" + x1 + ", " + y1 + ", " + x2 + ", " + y2);
        }

        this.tipo = TipoObstaculo.PAREDE;
        this.tipoMaterial = TipoMaterial.CONCRETO;
        setLocalizacao(x1, y1, x2, y2);

    }

    // Gets & Sets
    
    public int getId() {

        if (DEBUG) {
            System.out.println("Obstaculo.getId: " + id);
        }
        
        return id;

    }

    public TipoObstaculo getTipo() {
    
        if (DEBUG) {
            System.out.println("Obstaculo.getTipo: " + tipo);
        }

        return tipo;
    }

    public void setTipo(TipoObstaculo tipo) {

        if (DEBUG) {
            System.out.println("Obstaculo.setTipo: " + tipo);
        }

        this.tipo = tipo;

    }

    public TipoMaterial getTipoMaterial() {

        if (DEBUG) {
            System.out.println("Obstaculo.getTipoMaterial: " + tipoMaterial);
        }

        return tipoMaterial;

    }

    public void setTipoMaterial(TipoMaterial tipoMaterial) {

        if (DEBUG) {
            System.out.println("Obstaculo.setTipoMaterial: " + tipoMaterial);
        }

        this.tipoMaterial = tipoMaterial;

    }

    public float getTamanho() {

        if (DEBUG) {
            System.out.println("Obstaculo.getTamanho");
        }

        return (float) localizacao.getP1().distance(localizacao.getP2());

    }

    // implementação deste método especifica que a localização deste objeto é
    // descrita por uma coleção de pontos, e não apenas um
    @Override
    public Line2D getLocalizacao() {

        if (DEBUG) {
            System.out.println("Obstaculo.getLocalizacao");
        }

        return localizacao;

    }

    // Proibido valores negativos
    public final void setLocalizacao(float x1, float y1, float x2, float y2) throws IllegalArgumentException {

        if (DEBUG) {
            System.out.println("Obstaculo.setLocalizacao: (" + x1 + ", " + y1 + ", " + x2 + ", " + y2);
        }

        if (x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0) {
            throw new IllegalArgumentException("Valor negativo em Obstaculo.setLocalizacao");
        }

        Point2D pInicial = new Point2D.Float(x1, y1);
        Point2D pFinal = new Point2D.Float(x2, y2);
        
        this.localizacao = new Line2D.Float(pInicial, pFinal);
        
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        float antigoValor, novoValor;

        antigoValor = (float) evt.getOldValue();
        novoValor = (float) evt.getNewValue();

        switch (evt.getPropertyName()) {
            
            case "comprimento":

                setLocalizacao(
                        (float) localizacao.getX1(),
                        (float) localizacao.getY1() * (novoValor / antigoValor),
                        (float) localizacao.getX2(),
                        (float) localizacao.getY2() * (novoValor / antigoValor)
                );
                break;

            case "largura":

                setLocalizacao(
                        (float) localizacao.getX1() * (novoValor / antigoValor),
                        (float) localizacao.getY1(),
                        (float) localizacao.getX2() * (novoValor / antigoValor),
                        (float) localizacao.getY2()
                );
                break;

        }

    }

    // Enumeração para conter os diferentes tipos de materiais  considerados pelos
    // modelos de propagação
    public static enum TipoMaterial {

        MADEIRA, CONCRETO, ALUMINIO
    }

    // Enumeração para conter os diferentes tipos de objetos considerados pelos
    // modelos de propagação
    public static enum TipoObstaculo {

        OBJETO, PAREDE, ANDAR
    }

}
