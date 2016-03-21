/*
 * To change this license header, choose License Headers fluxoArquivo Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template fluxoArquivo the editor.
 */
package simulador;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import simulador.modelo.Ambiente;
import simulador.controle.Controlador;
import simulador.controle.ControladorBarraMenu;
import simulador.controle.ControladorBarraStatus;
import simulador.controle.ControladorBotoes;
import simulador.controle.ControladorGradeObjetoAmbiente;
import simulador.controle.ControladorDesign;
import simulador.controle.ControladorPainelInfoAmbiente;
import simulador.controle.ControladorParametrosPropagacao;
import simulador.controle.GeradorGraficos;
import simulador.controle.actions.AbrirAction;
import simulador.controle.actions.NovoAction;
import simulador.controle.actions.SalvarAction;
import simulador.visao.BarraFerramentas;
import simulador.visao.BarraMenu;
import simulador.visao.JanelaPrincipal;

/**
 *
 * @author Fernando
 */
public final class Simulador {

    public static boolean DEBUG = true;
    private Ambiente ambiente;
    private JanelaPrincipal janelaPrincipal;
    private List<Controlador> controladores = new ArrayList();

    public Simulador() {

        if (DEBUG) {
            System.out.println("new Simulador");
        }

        ambiente = new Ambiente();
        janelaPrincipal = new JanelaPrincipal(600, 400);
        
    }
    
    public void iniciar() {

        if (DEBUG) {
            System.out.println("Simulador.iniciar");
        }

        inicializarControladores();
        vincularGUI();
        lancarGUI();      

    }
    
    public void reiniciar(){
        
        janelaPrincipal.dispose();
        controladores.clear();        
    
        ambiente = new Ambiente();
        janelaPrincipal = new JanelaPrincipal(600, 400);
        
        inicializarControladores();
        vincularGUI();
        lancarGUI();
    
    }

    private void inicializarControladores() {

        if (DEBUG) {
            System.out.println("Simulador.inicializarControladores");
        }

        ControladorDesign cPainelDesign = new ControladorDesign(ambiente);        
        ControladorBarraMenu cBarraMenu = new ControladorBarraMenu(ambiente);
        ControladorPainelInfoAmbiente cPainelInfoAmbiente = new ControladorPainelInfoAmbiente(ambiente);
        //ControladorGradeProjecao cGradeProjecao = new ControladorGradeProjecao(ambiente);
        ControladorGradeObjetoAmbiente cGradeObjetoAmbiente = new ControladorGradeObjetoAmbiente();
        ControladorParametrosPropagacao cPainelPropagacao = new ControladorParametrosPropagacao(ambiente);
        ControladorBarraStatus cBarraStatus = new ControladorBarraStatus(ambiente);
        ControladorBotoes cBotoes = new ControladorBotoes(ambiente);         
        GeradorGraficos geradorGraficos = new GeradorGraficos(ambiente);

        controladores.add(cPainelDesign);
        controladores.add(cBarraMenu);
        controladores.add(cPainelInfoAmbiente);
        //controladores.add(cGradeProjecao);
        controladores.add(cGradeObjetoAmbiente);
        controladores.add(cPainelPropagacao);
        controladores.add(cBarraStatus);
        controladores.add(cBotoes);
        controladores.add(geradorGraficos);

    }

    private void vincularGUI() {

        if (DEBUG) {
            System.out.println("Simulador.vincularGUI");
        }

        for (Controlador controlador : controladores) {
            controlador.estabelecerControle(janelaPrincipal);
        }
        
        // Configurando botões/Menus e Actions
        
        Action novoAction = new NovoAction(this);
        Action abrirAction = new AbrirAction(ambiente);
        Action salvarAction = new SalvarAction(ambiente);
        
        JMenuItem smNovo = janelaPrincipal.getBarraMenu().obterReferencia(BarraMenu.SM_NOVO);
        JMenuItem smAbrir = janelaPrincipal.getBarraMenu().obterReferencia(BarraMenu.SM_ABRIR);
        JMenuItem smSalvar = janelaPrincipal.getBarraMenu().obterReferencia(BarraMenu.SM_SALVAR);
        AbstractButton btNovo = janelaPrincipal.getBarraFerramentas().obterReferencia(BarraFerramentas.BT_NOVO);
        AbstractButton btAbrir = janelaPrincipal.getBarraFerramentas().obterReferencia(BarraFerramentas.BT_ABRIR);
        AbstractButton btSalvar = janelaPrincipal.getBarraFerramentas().obterReferencia(BarraFerramentas.BT_SALVAR);
        
        smNovo.setAction(novoAction);
        btNovo.setAction(novoAction);
        smAbrir.setAction(abrirAction);
        btAbrir.setAction(abrirAction);
        smSalvar.setAction(salvarAction);
        btSalvar.setAction(salvarAction);        
        
        smNovo.setIcon(null);
        smAbrir.setIcon(null);
        smSalvar.setIcon(null);
        btNovo.setText("");
        btAbrir.setText("");
        btSalvar.setText("");
        
        //janelaPrincipal.getBarraFerramentas().obterReferencia(BarraFerramentas.BT_EXCLUIR).
          //      addActionListener((ActionListener)controladores.get(0));        
        
    }

    // Método para carregar configurações padrão
    private void lancarGUI() {

        if (DEBUG) {
            System.out.println("Simulador.lancarGUI");
        }

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                janelaPrincipal.setVisible(true);
            }

        });
    }

    public static void main(String[] args) {

        try {

            Simulador simulador = new Simulador();
            simulador.iniciar();

            //simulador.ambiente.setComprimento(30);
            //simulador.ambiente.setLargura(30);
            //simulador.ambiente.setDistanciaPontos(2f);

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "Erro na inicialização de Simuladora: Problema com argumentos ilegais.");
        }

    }

}
