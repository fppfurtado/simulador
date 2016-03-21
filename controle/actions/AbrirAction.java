/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.controle.actions;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import simulador.modelo.Ambiente;
import simulador.modelo.Obstaculo;
import simulador.modelo.Radio;
import simulador.visao.BarraFerramentas;
import simulador.visao.IconesFactory;

/**
 *
 * @author Fernando
 */
public class AbrirAction extends IOAction {

    public static boolean DEBUG = false;

    public AbrirAction(Ambiente ambiente) {
        
        super("Abrir", IconesFactory.criarIconeAbrir(BarraFerramentas.TAM_BOTAO), ambiente);
        
        if(DEBUG) {
            System.out.println("new AbrirAction");
        }
        
    }
    
    private void carregarArquivo(File arquivo) throws FileNotFoundException, IOException {
        
        if(DEBUG) {
            System.out.println("AbrirAction.carregarArquivo");
        }
        
        BufferedReader leitorArquivo = new BufferedReader(new FileReader(arquivo));
        String linhaArquivo = leitorArquivo.readLine();
        
        while(!Objects.isNull(linhaArquivo)) {
        
            String[] elementosLinha = linhaArquivo.split(SEPARADOR_ELEMENTOS_LINHA);
            String classeObjeto = elementosLinha[0];
            
            if(classeObjeto.equals(Ambiente.class.getSimpleName())) {
            
                ambiente.setComprimento(Float.parseFloat(elementosLinha[1]));
                ambiente.setLargura(Float.parseFloat(elementosLinha[2]));
                ambiente.setDistanciaPontos(Float.parseFloat(elementosLinha[3]));
                ambiente.setModeloPropagacao(elementosLinha[4]);
                
            } else if(classeObjeto.equals(Radio.class.getSimpleName())) {
                
                Radio radio = new Radio(
                        Float.parseFloat(elementosLinha[1]),
                        Float.parseFloat(elementosLinha[2])
                );
                
                radio.setFrequencia(Float.parseFloat(elementosLinha[3]));
                radio.setPotencia(Float.parseFloat(elementosLinha[4]));
                radio.setGanho(Float.parseFloat(elementosLinha[5]));
                radio.setFlagTransmissao(Boolean.parseBoolean(elementosLinha[6]));
                
                ambiente.adicionarRadio(radio);
            
            } else if(classeObjeto.equals(Obstaculo.class.getSimpleName())) {
                
                Obstaculo obstaculo = new Obstaculo(
                        Float.parseFloat(elementosLinha[1]),
                        Float.parseFloat(elementosLinha[2]),
                        Float.parseFloat(elementosLinha[3]),
                        Float.parseFloat(elementosLinha[4])
                );
                
                obstaculo.setTipo(Obstaculo.TipoObstaculo.valueOf(elementosLinha[5]));
                obstaculo.setTipoMaterial(Obstaculo.TipoMaterial.valueOf(elementosLinha[6]));
                
                ambiente.adicionarObstaculo(obstaculo);
            
            }
            
            linhaArquivo = leitorArquivo.readLine();
            
        }
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(DEBUG) {
            System.out.println("AbrirAction.actionPerformed");
        }
        
        JFileChooser fileChooser = new JFileChooser();        
        int retornoFileChooser = fileChooser.showOpenDialog(null);
        
        if(retornoFileChooser == JFileChooser.APPROVE_OPTION) {
            try {
                carregarArquivo(fileChooser.getSelectedFile());
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Arquivo não encontrado");
                ex.printStackTrace();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Falha ao carregar arquivo");
                ex.printStackTrace();
            }
        }
        
    }

}
