/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.visao;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Fernando
 */
public class DialogoDoisCampos extends JDialog {
    
    public static boolean DEBUG = false;
    private JLabel rotulo1;
    private JLabel rotulo2;
    private JTextField campo1;
    private JTextField campo2;
    private JButton btCancelar;
    private JButton btOk;
    
    public DialogoDoisCampos(Frame janelaPai, String titulo, String nomeCampo1, String nomeCampo2) {
             
        super(janelaPai);       
        
        setTitle(titulo);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(300, 150));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        inicializarComponentes(nomeCampo1, nomeCampo2);
        configurarComponentes();
        anexarComponentes();
        
    }    

    private void inicializarComponentes(String nomeCampo1, String nomeCampo2) {
        
        rotulo1 = new JLabel(nomeCampo1.toUpperCase());
        rotulo2 = new JLabel(nomeCampo2.toUpperCase());
        
        campo1 = new JTextField(10);
        campo2 = new JTextField(10);
        
        btCancelar = new JButton("Cancelar");
        btOk = new JButton("OK");
        
    }
    
    private void configurarComponentes(){
        
        getRootPane().setDefaultButton(btOk);
   
        btCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

            
    }

    private void anexarComponentes() {
        
        Container container;
        JPanel painelJuncao, painelRotulos, painelCampos, painelBotoes;
        
        container = new Container();        
        painelJuncao = new JPanel();
        painelRotulos = new JPanel(new GridLayout(2, 1));
        painelCampos = new JPanel(new GridLayout(2, 1));    
        painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        painelRotulos.add(rotulo1);
        painelRotulos.add(rotulo2);
        painelCampos.add(campo1);
        painelCampos.add(campo2);
        
        painelJuncao.setBorder(BorderFactory.createEtchedBorder());
        painelJuncao.setLayout(new BoxLayout(painelJuncao, BoxLayout.LINE_AXIS));
        painelJuncao.setPreferredSize(new Dimension((int)(getWidth()*0.85), (int)(getHeight()*0.35)));
        
        painelJuncao.add(painelRotulos);
        painelJuncao.add(painelCampos);        
        
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        container.add(painelJuncao);
        
        painelBotoes.add(btCancelar);
        painelBotoes.add(btOk);
        
        add(container);
        add(painelBotoes, BorderLayout.SOUTH);
        
        revalidate();
        //repaint();
        
    }

    public JTextField getCampo1() {
        return campo1;
    }

    public JTextField getCampo2() {
        return campo2;
    }

    public JButton getBtCancelar() {
        return btCancelar;
    }

    public JButton getBtOk() {
        return btOk;
    }

}
