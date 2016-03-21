/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.modelo;

import java.awt.geom.Point2D;

/**
 *
 * @author Fernando
 */
// Essa classe é uma abstração dos pontos no espaço dentro do ambiente em que
// será estimado o sinal da cobertura
public class PontoReceptor extends Point2D.Float implements
        Ambiente.Conteudo, SistemaWireless.Receptor {

    public static boolean DEBUG = false;                                         //chave para ativar a depuração
    private SinalRadioFrequencia sinal = null;                                  // estimativa do sinal de radio frequencia no ponto
    private float sensibilidade = Ambiente.POTENCIA_MIN;

    public PontoReceptor(float x, float y) {

        super(x, y);

        if (DEBUG) {
            System.out.println("new PontoReceptor(" + x + ", " + y + ")");
        }

    }

    //Gets & Sets
    // implementação deste método especifica que a localização deste objeto é
    // descrita por um ponto apenas
    @Override
    public Point2D getLocalizacao() {

        if (DEBUG) {
            System.out.println("PontoPropagacao.getLocalizacao");
        }

        return this;

    }

    public void setLocalizacao(float x, float y) {

        if (DEBUG) {
            System.out.println("PontoPropagacao.setLocalizacao");
        }

        setLocation(x, y);

    }

//    public java.lang.Float getPotenciaSinal() {
//        return sinal == null ? null : sinal.getPotencia();
//    }
    public SinalRadioFrequencia getSinal() {

        if (DEBUG) {
            System.out.println("PontoPropagacao.getSinal");
        }

        return sinal;

    }

    public void setSensibilidade(float sensibilidade) {
        this.sensibilidade = sensibilidade;
    }

    @Override
    public float getSensibilidade() {
        return sensibilidade;
    }

    @Override
    public void receberSinal(SinalRadioFrequencia sinalRecebido) {
        //System.out.println("PontoRx.receberSinal - sinal recebido: "+sinalRecebido);
        if (sinalRecebido != null) {
            if (this.sinal == null) {
                if (sinalRecebido.getPotencia() > getSensibilidade()) {
                    this.sinal = sinalRecebido;
                }
            } else {
                if (sinalRecebido.getPotencia() > sinal.getPotencia()) {
                    this.sinal = sinalRecebido;
                }
            }
        } else {
            this.sinal = null;
        }

    }

}
