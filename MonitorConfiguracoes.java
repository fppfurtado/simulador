/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Fernando
 */
public class MonitorConfiguracoes {

    private static final boolean DEBUG = true;
    private final Properties defaultProps;
    private final Properties appProps;

    public MonitorConfiguracoes() throws IOException {

        if (DEBUG) {
            System.out.println("new MonitorConfiguracoes");
        }

        InputStream fluxoEntradaProperties;

        fluxoEntradaProperties = getClass().getResourceAsStream("default.properties");

        defaultProps = new Properties();
        defaultProps.load(fluxoEntradaProperties);
        fluxoEntradaProperties.close();

        fluxoEntradaProperties = getClass().getResourceAsStream("default.properties");       
        
        appProps = new Properties(defaultProps);
        appProps.load(fluxoEntradaProperties);
        fluxoEntradaProperties.close();

    }
    
    public String getValor(String propriedade){
        return appProps.getProperty(propriedade);
    }

}
