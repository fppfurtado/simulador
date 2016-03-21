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
public interface SistemaWireless {
    
    public static interface Transmissor extends SistemaWireless {

        public void transmitirSinal();
        
    }

    public static interface Receptor extends SistemaWireless {

        public float getSensibilidade();
        public void receberSinal(SinalRadioFrequencia sinal);
    }
    
}
