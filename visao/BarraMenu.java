/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.visao;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

/**
 *
 * @author Fernando
 */
public class BarraMenu extends JMenuBar implements BuscaComponenteInterno {

    public static boolean DEBUG = false;
    public static final String MN_ARQUIVO = "Arquivo";
    public static final String MN_EDITAR = "Editar";
    public static final String MN_AJUDA = "Ajuda";
    public static final String SM_NOVO = "Novo";
    public static final String SM_ABRIR = "Abrir";
    public static final String SM_SALVAR = "Salvar";
    public static final String SM_SAIR = "Sair";
    public static final String SM_DIMENSOES_AMBIENTE = "Dimensões Ambiente";
    public static final String SM_DISTANCIA_PONTOS = "Distância Pontos";
    //public static final String SM_QTD_CORES_SIMULACAO = "Quantidade Cores";
    public static final String SM_EXIBICAO_GRADE = "Exibir Grade";
    public static final String SM_SOBRE = "Sobre";
    private final Map<String, JMenuItem> referenciasMenus = new HashMap();

    public BarraMenu() {

        if (DEBUG) {
            System.out.println("new BarraMenu");
        }

        criarMenus();
        configurarMenus();
        anexarMenus();

    }

    private void criarMenus() {

        if (DEBUG) {
            System.out.println("BarraMenu.criarMenus");
        }

        // Armazenando referencias dos menus principais
        referenciasMenus.put(MN_ARQUIVO, new JMenu(MN_ARQUIVO));
        referenciasMenus.put(MN_EDITAR, new JMenu(MN_EDITAR));
        referenciasMenus.put(MN_AJUDA, new JMenu(MN_AJUDA));

        // DEFINIDO SUBMENUS
        // Menu_1
        referenciasMenus.put(SM_NOVO, new JMenuItem(SM_NOVO));
        referenciasMenus.put(SM_ABRIR, new JMenuItem(SM_ABRIR));
        referenciasMenus.put(SM_SALVAR, new JMenuItem(SM_SALVAR));        
        referenciasMenus.put(SM_SAIR, new JMenuItem(SM_SAIR));
        // Menu_2
        referenciasMenus.put(SM_DIMENSOES_AMBIENTE, new JMenuItem(SM_DIMENSOES_AMBIENTE));
        referenciasMenus.put(SM_DISTANCIA_PONTOS, new JMenuItem(SM_DISTANCIA_PONTOS));
//        referenciasMenus.put(SM_QTD_CORES_SIMULACAO, new JMenuItem(SM_QTD_CORES_SIMULACAO));
        referenciasMenus.put(SM_EXIBICAO_GRADE, new JCheckBoxMenuItem(SM_EXIBICAO_GRADE));
        //Menu_3
        referenciasMenus.put(SM_SOBRE, new JMenuItem(SM_SOBRE));       

    }
    
    private void configurarMenus(){
    
        JMenuItem m1, m2, m3;
        JMenuItem sm_1_1, sm_1_2, sm_1_3, sm_1_4;
        JMenuItem sm_2_1, sm_2_2, sm_2_3, sm_2_4;
        JMenuItem sm_3_1;

        m1 = obterReferencia(MN_ARQUIVO);
        m2 = obterReferencia(MN_EDITAR);
        m3 = obterReferencia(MN_AJUDA);
        sm_1_1 = obterReferencia(SM_NOVO);
        sm_1_2 = obterReferencia(SM_ABRIR);
        sm_1_3 = obterReferencia(SM_SALVAR);
        sm_1_4 = obterReferencia(SM_SAIR);
        sm_2_1 = obterReferencia(SM_DIMENSOES_AMBIENTE);
        sm_2_2 = obterReferencia(SM_DISTANCIA_PONTOS);
//        sm_2_3 = obterReferencia(SM_QTD_CORES_SIMULACAO);
        sm_2_4 = obterReferencia(SM_EXIBICAO_GRADE);
        sm_3_1 = obterReferencia(SM_SOBRE);
        
        m1.setActionCommand(MN_ARQUIVO);
        m2.setActionCommand(MN_EDITAR);
        m3.setActionCommand(MN_AJUDA);
        sm_1_1.setActionCommand(SM_NOVO);
        sm_1_2.setActionCommand(SM_ABRIR);
        sm_1_3.setActionCommand(SM_SALVAR);
        sm_1_4.setActionCommand(SM_SAIR);
        sm_2_1.setActionCommand(SM_DIMENSOES_AMBIENTE);
        sm_2_2.setActionCommand(SM_DISTANCIA_PONTOS);
//        sm_2_3.setActionCommand(SM_QTD_CORES_SIMULACAO);
        sm_2_4.setActionCommand(SM_EXIBICAO_GRADE);
        sm_3_1.setActionCommand(SM_SOBRE);
    
    }
    
    private void anexarMenus(){
        
        if (DEBUG) {
            System.out.println("BarraMenu.anexarMenus");
        }
        
        JMenuItem menu1, menu2, menu3;

        menu1 = obterReferencia(MN_ARQUIVO);
        menu2 = obterReferencia(MN_EDITAR);
        menu3 = obterReferencia(MN_AJUDA);
        
        // Anexando submenus ao Menu_1
        menu1.add(obterReferencia(SM_NOVO));
        menu1.add(obterReferencia(SM_ABRIR));
        menu1.add(obterReferencia(SM_SALVAR));
        menu1.add(new JSeparator());
        menu1.add(obterReferencia(SM_SAIR));
        
        // Anexando submenus ao Menu_2
        menu2.add(obterReferencia(SM_DIMENSOES_AMBIENTE));
        menu2.add(obterReferencia(SM_DISTANCIA_PONTOS));
//        menu2.add(obterReferencia(SM_QTD_CORES_SIMULACAO));
        menu2.add(new JSeparator());
        menu2.add(obterReferencia(SM_EXIBICAO_GRADE));
        
        // Anexando submenus ao Menu_3
        menu3.add(obterReferencia(SM_SOBRE));        
    
        // Anexando Menus à Barra de Menus
        add(menu1);
        add(menu2);
        add(menu3);
    
    }

    @Override
    public JMenuItem obterReferencia(String chave) {

        if (DEBUG) {
            System.out.println("BarraMenu.obterItemMenu: "+chave);
        }

        return referenciasMenus.get(chave);

    }

}
