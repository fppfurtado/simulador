/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.visao;

import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

/**
 *
 * @author Fernando
 */
public class BarraFerramentas extends JToolBar implements BuscaComponenteInterno {

    public static boolean DEBUG = false;
    public static final int TAM_BOTAO = 25;
    public static final String BT_NOVO = "novo";
    public static final String BT_ABRIR = "abrir";
    public static final String BT_SALVAR = "salvar";
    public static final String BT_SELECAO = "selecao";
    public static final String BT_RADIO = "radio";
    public static final String BT_OBSTACULO = "obstaculo";
    public static final String BT_EXCLUIR = "excluir";
    private final Map<String, AbstractButton> referenciasBotoes = new HashMap();
    private final ButtonGroup pulsadores = new ButtonGroup();
    private final ButtonGroup retentores = new ButtonGroup();

    public BarraFerramentas() {

        super();
        
        if(DEBUG){
            System.out.println("new BarraFerramentas");
        }

        criarPulsadores();
        criarRetentores();
        configurarBotoes();
        anexarBotoes();
        
        setFloatable(false);

    }

    private void criarPulsadores() {
        
        if(DEBUG){
            System.out.println("BarraFerramentas.criarPulsadores");
        }

        referenciasBotoes.put(BT_NOVO, new JButton(IconesFactory.criarIconeNovo(TAM_BOTAO)));
        referenciasBotoes.put(BT_ABRIR, new JButton(IconesFactory.criarIconeAbrir(TAM_BOTAO)));
        referenciasBotoes.put(BT_SALVAR, new JButton(IconesFactory.criarIconeSalvar(TAM_BOTAO)));
        referenciasBotoes.put(BT_EXCLUIR, new JButton(IconesFactory.criarIconeExcluir(TAM_BOTAO)));
        
        

    }

    private void criarRetentores() {
        
        if(DEBUG){
            System.out.println("BarraFerramentas.criarRetentores");
        }
        
        JToggleButton retentor1, retentor2, retentor3;
        
        retentor1 = new JToggleButton(IconesFactory.criarIconeSelecao(TAM_BOTAO));
        retentor2 = new JToggleButton(IconesFactory.criarIconeRadio(TAM_BOTAO));
        retentor3 = new JToggleButton(IconesFactory.criarIconeObstaculo(TAM_BOTAO));                
        
        retentor1.setActionCommand(BT_SELECAO);
        retentor2.setActionCommand(BT_RADIO);
        retentor3.setActionCommand(BT_OBSTACULO);

        referenciasBotoes.put(BT_SELECAO, retentor1);
        referenciasBotoes.put(BT_RADIO, retentor2);
        referenciasBotoes.put(BT_OBSTACULO, retentor3);

    }

    private void configurarBotoes() {
        
        if(DEBUG){
            System.out.println("BarraFerramentas.configurarBotoes");
        }
        
        obterReferencia(BT_NOVO).setActionCommand(BT_NOVO);
        obterReferencia(BT_ABRIR).setActionCommand(BT_ABRIR);
        obterReferencia(BT_SALVAR).setActionCommand(BT_SALVAR);
        obterReferencia(BT_SELECAO).setActionCommand(BT_SELECAO);
        obterReferencia(BT_RADIO).setActionCommand(BT_RADIO);
        obterReferencia(BT_OBSTACULO).setActionCommand(BT_OBSTACULO);
        obterReferencia(BT_EXCLUIR).setActionCommand(BT_EXCLUIR);        
        
    }
    
    private void anexarBotoes() {
        
        if(DEBUG){
            System.out.println("BarraFerramentas.anexarBotoes");
        }
        
        // Anexando botãos ao grupo Pulsadores
        pulsadores.add(obterReferencia(BT_NOVO));
        pulsadores.add(obterReferencia(BT_ABRIR));
        pulsadores.add(obterReferencia(BT_SALVAR));
        pulsadores.add(obterReferencia(BT_NOVO));
        pulsadores.add(obterReferencia(BT_EXCLUIR));
        
        // Anexando botãos ao grupo Retentores
        retentores.add(obterReferencia(BT_SELECAO));
        retentores.add(obterReferencia(BT_RADIO));
        retentores.add(obterReferencia(BT_OBSTACULO));
        
        // Anexando botoes à Barra de Ferramentas
        add(obterReferencia(BT_NOVO));                                             
        add(obterReferencia(BT_ABRIR));                                            
        add(obterReferencia(BT_SALVAR));                                           
        add(new JSeparator(VERTICAL));
        add(new JSeparator(VERTICAL));
        add(obterReferencia(BT_SELECAO));                                             
        add(obterReferencia(BT_RADIO));                                             
        add(obterReferencia(BT_OBSTACULO));                                         
        add(obterReferencia(BT_EXCLUIR));                                           

    }
    
    @Override
    public AbstractButton obterReferencia(String chave) {
        
        if(DEBUG){
            System.out.println("BarraFerramentas.obterReferencias: "+chave);
        }
        
        return referenciasBotoes.get(chave);
        
    }

}
