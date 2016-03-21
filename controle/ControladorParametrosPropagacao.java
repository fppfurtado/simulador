/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import simulador.modelo.Ambiente;
import simulador.modelo.propagacao.ModeloEmpiricoPropagacao;
import simulador.visao.GradeProjecao;
import simulador.visao.GradePropriedades;
import simulador.visao.JanelaPrincipal;
import simulador.visao.PainelPropagacao;

/**
 *
 * @author Fernando
 */
public class ControladorParametrosPropagacao implements Controlador {
    
    public static boolean DEBUG = false;
    private final Ambiente ambiente;
    private PainelPropagacao painelPropagacao;
    private GradePropriedades gradeModeloPropagacao;    
    //private final Map<String, ModeloEmpiricoPropagacao> modelosPropagacao = new LinkedHashMap();

    public ControladorParametrosPropagacao(Ambiente ambiente) {
        
        //this.painelPropagacao = painelParamPropag;
        //this.gradeModeloPropagacao = janelaPrincipal.getGradeModeloPropagacao();
        this.ambiente = ambiente;
        
    }
    
    @Override
    public void estabelecerControle(JanelaPrincipal janelaPrincipal){
        
        this.painelPropagacao = janelaPrincipal.getPainelModeloProp();
        this.gradeModeloPropagacao = janelaPrincipal.getGradeModeloPropagacao();
        
        Map<String, ModeloEmpiricoPropagacao> modelosPropagacao = ambiente.getCanal().getModelosPropagacao();
        JComboBox combo = (JComboBox)painelPropagacao.obterReferencia(PainelPropagacao.CAMPO_MODELO_PROPAGACAO);
        JTextField campoTexto = (JTextField)painelPropagacao.obterReferencia(PainelPropagacao.CAMPO_FORMULA);
        
        ModeloEmpiricoPropagacao modelo = null;
        
        for (Map.Entry<String, ModeloEmpiricoPropagacao> entrySet : modelosPropagacao.entrySet()) {            
            modelo = entrySet.getValue();
            combo.addItem(modelo.getNome());
        }
        
        modelo = ambiente.getCanal().getModeloPropagacao();
        campoTexto.setText(modelo.getFormula());
        combo.addActionListener(new ComboBoxListener());
        
        gradeModeloPropagacao.exibirPropriedadesObjeto(modelo);
        
    }
    
    class ComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            //System.out.println("ComboBoxListener.actionPerformed");
            
            JComboBox combo = (JComboBox) e.getSource();
            JTextField campoTexto = (JTextField)painelPropagacao.obterReferencia(PainelPropagacao.CAMPO_FORMULA);
            
            //System.out.println(combo.getSelectedItem());
            ambiente.setModeloPropagacao((String)combo.getSelectedItem());
            ModeloEmpiricoPropagacao modeloPropagacao = ambiente.getCanal().getModeloPropagacao();
            campoTexto.setText(modeloPropagacao.getFormula());
            gradeModeloPropagacao.exibirPropriedadesObjeto(modeloPropagacao);
            //System.out.println("modelo ambiente: "+ambiente.getModeloPropagacao());
            
        }
    
    }
    
}