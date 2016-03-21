/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.modelo;

/**
 *
 * @author Fernando
 */
public class SinalRadioFrequencia implements Comparable<SinalRadioFrequencia>, Prototype{
    
    public static boolean DEBUG = false;
    private final float frequencia;
    private float potencia;
    // Delegate Pattern
    //private final PropertyChangeSupport notificadorObserver = new PropertyChangeSupport(this);

    public SinalRadioFrequencia(float frequencia, float potencia) {
        
        if(DEBUG){
            System.out.println("new SinalRadioFrequencia("+frequencia+", "+potencia+")");
        }
        
        this.frequencia = frequencia;
        this.potencia = potencia;
        
    }
    
    protected SinalRadioFrequencia(SinalRadioFrequencia sinal) {
        
        if(DEBUG){
            System.out.println("new protected SinalRadioFrequencia");
        }
        
        this.frequencia = sinal.getFrequencia();
        this.potencia = sinal.getPotencia();
        
    }

    public float getFrequencia() {
        
        if(DEBUG){
            System.out.println("SinalRadioFrequencia.getFrequencia");
        }
        
        return frequencia;
        
    }

    public float getPotencia() {
        
        if(DEBUG){
            System.out.println("SinalRadioFrequencia.getPotencia");
        }
        
        return potencia;
        
    }

    public void setPotencia(float potencia) {
//        float antigoValor = this.potencia;
        
        if(DEBUG){
            System.out.println("SinalRadioFrequencia.setPotencia");
        }
        
        this.potencia = potencia;
        //notificadorObserver.firePropertyChange("potencia", antigoValor, potencia);
    }
    
//    public void adicionarObserver(String nomePropriedade, PropertyChangeListener observer){        
//        notificadorObserver.addPropertyChangeListener(nomePropriedade, observer);        
//    }

    @Override
    public int compareTo(SinalRadioFrequencia o) {        
        return Math.round(potencia - o.getPotencia());        
    }

    @Override
    public SinalRadioFrequencia clonar() {
        return new SinalRadioFrequencia(this);
    }
    
}
