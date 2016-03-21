/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.visao;

import java.awt.Component;
import java.awt.Container;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Fernando
 */
public class PainelPropagacao extends JPanel implements BuscaComponenteInterno {
    
    public static boolean DEBUG = false;
    public static final String CAMPO_MODELO_PROPAGACAO = "cmp";
    public static final String CAMPO_FORMULA = "cf";
    private final Map<String, JComponent> referenciasRotulos = new LinkedHashMap();
    private final Map<String, JComponent> referenciasCampos = new LinkedHashMap();

    public PainelPropagacao() {
        
        super();
        
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEtchedBorder());
        
        criarCampos();
        anexarCampos();
        
    }    

    private void criarCampos() {       
        
        JLabel r1, r2;
        
        r1 = new JLabel("Modelo de Propagação: ");
        r2 = new JLabel("Fórmula: ");
        
        referenciasRotulos.put(CAMPO_MODELO_PROPAGACAO, r1);
        referenciasRotulos.put(CAMPO_FORMULA, r2);
        
        referenciasCampos.put(CAMPO_MODELO_PROPAGACAO, new JComboBox());
        referenciasCampos.put(CAMPO_FORMULA, new JTextField());
        
    }

    private void anexarCampos() {
        
        Container ctn1, ctn2;
        
        ctn1 = new Container();
        ctn2 = new Container();
        
        ctn1.setLayout(new BoxLayout(ctn1, BoxLayout.Y_AXIS));
        ctn2.setLayout(new BoxLayout(ctn2, BoxLayout.Y_AXIS));
        
        for (Map.Entry<String, JComponent> referencia : referenciasRotulos.entrySet()) {
            ctn1.add(referencia.getValue());
        }
        
        for (Map.Entry<String, JComponent> referencia : referenciasCampos.entrySet()) {
            ctn2.add(referencia.getValue());
        }

        add(ctn1);
        add(ctn2);
        
    }  

    @Override
    public Component obterReferencia(String chave) {
        return referenciasCampos.get(chave);
    }

}
