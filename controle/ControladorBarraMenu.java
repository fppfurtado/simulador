/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import simulador.modelo.Ambiente;
import simulador.visao.BarraMenu;
import simulador.visao.DialogoDoisCampos;
import simulador.visao.JanelaPrincipal;

/**
 *
 * @author Fernando
 */
public class ControladorBarraMenu implements Controlador {
    
    public static boolean DEBUG = true;
//    private final BarraMenu barraMenu;
    private final Ambiente ambiente;
//    private PainelDesign painelDesign;
    //private final Simulador simulador;
    
    public ControladorBarraMenu(Ambiente ambiente) {
//        this.barraMenu = janelaPrincipal.getBarraMenu();
//        this.painelDesign = janelaPrincipal.getPainelDesign();
        this.ambiente = ambiente;
        //this.simulador = simulador;
    }
    
    @Override
    public void estabelecerControle(JanelaPrincipal janelaPrincipal) {
        
        BarraMenu barraMenu = janelaPrincipal.getBarraMenu();
        
        barraMenu.obterReferencia(BarraMenu.SM_DIMENSOES_AMBIENTE).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (DEBUG) {
                    System.out.println("BarraMenu.SM_DIMENSOES_AMBIENTE.actionPerformed");
                }
                
                DialogoDoisCampos dlg = new DialogoDoisCampos(null, "Editar Ambiente", "Comprimento", "Largura");
                dlg.getBtOk().addActionListener(new ActionListener() {
                    
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        String valor1 = dlg.getCampo1().getText();
                        String valor2 = dlg.getCampo2().getText();
                        
                        dlg.dispose();
                        
                        try {
                            ambiente.setComprimento(Float.parseFloat(valor1));
                            ambiente.setLargura(Float.parseFloat(valor2));
                        } catch (IllegalArgumentException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                        
                    }
                });
                
            }
        });
        
        barraMenu.obterReferencia(BarraMenu.SM_DISTANCIA_PONTOS).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String valor = JOptionPane.showInputDialog(
                        null,
                        "Nova distância entre pontos receptores:",
                        "Editar Ambiente",
                        JOptionPane.QUESTION_MESSAGE
                );
                ambiente.setDistanciaPontos(Float.parseFloat(valor));
            }
        });

//        barraMenu.obterReferencia(BarraMenu.SM_QTD_CORES_SIMULACAO).addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String valor = JOptionPane.showInputDialog(
//                        null,
//                        "Quantidade de Cores para a Simulação:",
//                        "Editar Simulação",
//                        JOptionPane.QUESTION_MESSAGE
//                );
//                painelDesign.set(Float.parseFloat(valor));
//            }
//        });
        barraMenu.obterReferencia(BarraMenu.SM_EXIBICAO_GRADE).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // implementar opcao de desenho com ou sem grade no painel design
            }
        });
        
        barraMenu.obterReferencia(BarraMenu.SM_SAIR).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);                
            }
        });

        //barraMenu.obterReferencia(BarraMenu.SM_NOVO).setAction(new NovoAction(simulador));        
        //barraMenu.obterReferencia(BarraMenu.SM_ABRIR).setAction(new AbrirAction(ambiente));
        //barraMenu.obterReferencia(BarraMenu.SM_SALVAR).setAction(new SalvarAction(ambiente));
    }
    
}
