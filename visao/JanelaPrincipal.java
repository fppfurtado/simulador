/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.visao;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Fernando
 */
public class JanelaPrincipal extends JFrame {

    public static boolean DEBUG = false;
    private static final String CONTAINER_NORTE = "cs";
    private static final String CONTAINER_CENTRO = "cc";
    private static final String CONTAINER_CENTRO_ESQUERDA = "cce";
    private static final String CONTAINER_CENTRO_DIREITA = "ccd";
    private final Map<String, Container> referenciasContaineres = new LinkedHashMap();
    private BarraMenu barraMenu;
    private BarraFerramentas barraFerramentas;
    private PainelInfoAmbiente painelInfoAmbiente;
    private GradePropriedades gradeObjetoAmbiente;
    private GradeProjecao gradeProjecao;
    private GradePropriedades gradeModeloPropagacao;
    private PainelDesign painelDesign;
    private PainelPropagacao painelModeloProp;
    private JButton btLimpar;
    private JButton btSimular;
    private JPanel barraStatus;

    public JanelaPrincipal(int larguraPainel, int alturaPainel) {

        super("Simulador");

        if (DEBUG) {
            System.out.println("new JanelaPrincipal");
        }

        inicializarComponentes(larguraPainel, alturaPainel);
        configurarComponentes();
        criarContaineres();
        configurarContaineres();
        anexarComponentes();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);              
        pack();
        setLocationRelativeTo(null);
        
    }

    private void inicializarComponentes(int larguraPainel, int alturaPainel) {

        if (DEBUG) {
            System.out.println("JanelaPrincipal.inicializarComponentes");
        }
        
        painelDesign = new PainelDesign(larguraPainel, alturaPainel);        

        barraMenu = new BarraMenu();
        barraFerramentas = new BarraFerramentas();
        painelInfoAmbiente = new PainelInfoAmbiente();
        gradeObjetoAmbiente = new GradePropriedades("PROPRIEDADE","VALOR");
        gradeProjecao = new GradeProjecao("ID RADIO", "PROJETAR");
        gradeModeloPropagacao = new GradePropriedades("PARCELA", "INCLUIR");
        painelModeloProp = new PainelPropagacao();
        btLimpar = new JButton("Limpar");
        btSimular = new JButton("Simular");
        barraStatus = new JPanel(new BorderLayout());

    }
    
    private void configurarComponentes() {        
        
        barraFerramentas.obterReferencia(BarraFerramentas.BT_SELECAO).addActionListener(painelDesign);
        barraFerramentas.obterReferencia(BarraFerramentas.BT_RADIO).addActionListener(painelDesign);
        barraFerramentas.obterReferencia(BarraFerramentas.BT_OBSTACULO).addActionListener(painelDesign);        
        
        painelDesign.vincularControleDesenhoGrade((JCheckBoxMenuItem)barraMenu.obterReferencia(BarraMenu.SM_EXIBICAO_GRADE));
        
        painelDesign.addPropertyChangeListener(gradeProjecao);
        
        btLimpar.setActionCommand("limpar");
        btSimular.setActionCommand("simular");
        
        barraStatus.setBorder(BorderFactory.createEtchedBorder());
        
    }
    
    private void criarContaineres() {
        
        if (DEBUG) {
            System.out.println("JanelaPrincipal.criarContaineres");
        }

        Field[] variaveis;

        variaveis = getClass().getDeclaredFields();

        for (Field variavel : variaveis) {
            if (Modifier.isStatic(variavel.getModifiers())) {
                if (variavel.getName().contains("CONTAINER_")) {

                    variavel.setAccessible(true);
                    Container container = new Container();

                    try {
                        container.setName((String) variavel.get(this));
                        container.setLayout(new BorderLayout());
                        referenciasContaineres.put(container.getName(), container);
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        JOptionPane.showMessageDialog(this, "Containeres não puderam ser criados");
                        System.exit(1);
                    }

                }
            }
        }
    }

    private void configurarContaineres() {

        if (DEBUG) {
            System.out.println("JanelaPrincipal.configurarConteineres");
        }

        referenciasContaineres.get(CONTAINER_NORTE).setLayout(new BorderLayout(10, 1));
        referenciasContaineres.get(CONTAINER_CENTRO_ESQUERDA).setLayout(new GridLayout(3, 1));
        referenciasContaineres.get(CONTAINER_CENTRO_ESQUERDA).setPreferredSize(new Dimension(200, 1));
        
    }
    
    private void anexarComponentes() {

        if (DEBUG) {
            System.out.println("JanelaPrincipal.anexarComponentes");
        }

        Container ctnNorte,
                ctnCentro,
                ctnCentroEsquerda,
                ctnCentroDireita,
                ctnAux4,
                ctnAux5,
                ctnAux1,
                ctnAux2,
                ctnAux3;
        
        JPanel pnGrade1, pnGrade2, pnGrade3, pnBotoes;
        
        pnGrade1 = new JPanel();
        pnGrade2 = new JPanel();
        pnGrade3 = new JPanel();
        pnBotoes = new JPanel(new GridLayout(1, 2));                
        
        pnGrade1.add(new JLabel("OBJETO AMBIENTE"));
        pnGrade2.add(new JLabel("PROJETAR RADIAL"));
        pnGrade3.add(new JLabel("MODELO DE PROPAGAÇÃO"));
        pnBotoes.add(btLimpar);
        pnBotoes.add(btSimular);
        
        ctnAux1 = new Container();
        ctnAux2 = new Container();
        ctnAux3 = new Container();
        ctnAux4 = new Container();
        ctnAux5 = new Container();
        
        ctnAux1.setLayout(new BorderLayout());        
        ctnAux2.setLayout(new BorderLayout());
        ctnAux3.setLayout(new BorderLayout());
        ctnAux4.setLayout(new BorderLayout());
        ctnAux5.setLayout(new BorderLayout());
        
        ctnAux1.add(pnGrade1, BorderLayout.NORTH);
        ctnAux1.add(new JScrollPane(gradeObjetoAmbiente));        
        ctnAux2.add(pnGrade2, BorderLayout.NORTH);
        ctnAux2.add(new JScrollPane(gradeProjecao));        
        ctnAux3.add(pnGrade3, BorderLayout.NORTH);
        ctnAux3.add(new JScrollPane(gradeModeloPropagacao));        
        ctnAux4.add(painelModeloProp);
        ctnAux4.add(pnBotoes, BorderLayout.EAST);        
        ctnAux5.add(painelInfoAmbiente);
        
        ctnNorte = referenciasContaineres.get(CONTAINER_NORTE);
        ctnCentro = referenciasContaineres.get(CONTAINER_CENTRO);
        ctnCentroEsquerda = referenciasContaineres.get(CONTAINER_CENTRO_ESQUERDA);
        ctnCentroDireita = referenciasContaineres.get(CONTAINER_CENTRO_DIREITA);
        
        ctnCentroEsquerda.add(ctnAux1, BorderLayout.NORTH);
        ctnCentroEsquerda.add(ctnAux2);
        ctnCentroEsquerda.add(ctnAux3);
        ctnCentroDireita.add(painelDesign);
        ctnCentroDireita.add(ctnAux4, BorderLayout.SOUTH);
        
        ctnNorte.add(barraFerramentas, BorderLayout.WEST);
        ctnNorte.add(ctnAux5);
        ctnCentro.add(ctnCentroEsquerda, BorderLayout.WEST);
        ctnCentro.add(ctnCentroDireita);
        
        setJMenuBar(barraMenu);
        add(ctnNorte, BorderLayout.NORTH);
        add(ctnCentro);
        add(barraStatus, BorderLayout.SOUTH);

    }

    public BarraMenu getBarraMenu() {
        return barraMenu;
    }

    public BarraFerramentas getBarraFerramentas() {
        return barraFerramentas;
    }

    public PainelInfoAmbiente getPainelInfoAmbiente() {
        return painelInfoAmbiente;
    }

    public GradePropriedades getGradeObjetoAmbiente() {
        return gradeObjetoAmbiente;
    }

    public GradeProjecao getGradeProjecao() {
        return gradeProjecao;
    }

    public GradePropriedades getGradeModeloPropagacao() {
        return gradeModeloPropagacao;
    }

    public PainelDesign getPainelDesign() {
        return painelDesign;
    }

    public PainelPropagacao getPainelModeloProp() {
        return painelModeloProp;
    }

    public JButton getBtLimpar() {
        return btLimpar;
    }

    public JButton getBtSimular() {
        return btSimular;
    }

    public JPanel getBarraStatus() {
        return barraStatus;
    }        

}