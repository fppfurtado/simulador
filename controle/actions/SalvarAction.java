/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.controle.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class SalvarAction extends IOAction {
    
    public static boolean DEBUG = false;

    public SalvarAction(Ambiente ambiente) {
        super("Salvar", IconesFactory.criarIconeSalvar(BarraFerramentas.TAM_BOTAO), ambiente);
    }    
    
    private File criarArquivo() throws IOException{
    
        File arquivo = null;
        JFileChooser fileChooser = new JFileChooser();
        int retornoFileChooser = fileChooser.showSaveDialog(null);
        
        if(retornoFileChooser == JFileChooser.APPROVE_OPTION){        
            arquivo = fileChooser.getSelectedFile();
            arquivo.createNewFile();                
        }

        return arquivo;
    
    }
    
    private List<String> reunirConteudo() {
    
        List<String> linhasArquivo = new ArrayList();
        
        linhasArquivo.add(Ambiente.class.getSimpleName());
        linhasArquivo.add(SEPARADOR_ELEMENTOS_LINHA);
        linhasArquivo.add(String.valueOf(ambiente.getComprimento()));
        linhasArquivo.add(SEPARADOR_ELEMENTOS_LINHA);
        linhasArquivo.add(String.valueOf(ambiente.getLargura()));
        linhasArquivo.add(SEPARADOR_ELEMENTOS_LINHA);
        linhasArquivo.add(String.valueOf(ambiente.getDistanciaPontos()));
        linhasArquivo.add(SEPARADOR_ELEMENTOS_LINHA);
        linhasArquivo.add(ambiente.getModeloPropagacao());
        linhasArquivo.add(SEPARADOR_LINHAS);
        
        Map<Integer, Radio> radios = ambiente.getRadios();
        
        for (Map.Entry<Integer, Radio> entrySet : radios.entrySet()) {
            
            Radio radio = entrySet.getValue();
            
            linhasArquivo.add(Radio.class.getSimpleName());
            linhasArquivo.add(SEPARADOR_ELEMENTOS_LINHA);
            linhasArquivo.add(String.valueOf(radio.getLocalizacao().getX()));
            linhasArquivo.add(SEPARADOR_ELEMENTOS_LINHA);
            linhasArquivo.add(String.valueOf(radio.getLocalizacao().getY()));
            linhasArquivo.add(SEPARADOR_ELEMENTOS_LINHA);
            linhasArquivo.add(String.valueOf(radio.getFrequencia()));
            linhasArquivo.add(SEPARADOR_ELEMENTOS_LINHA);
            linhasArquivo.add(String.valueOf(radio.getPotencia()));
            linhasArquivo.add(SEPARADOR_ELEMENTOS_LINHA);
            linhasArquivo.add(String.valueOf(radio.getGanho()));
            linhasArquivo.add(SEPARADOR_ELEMENTOS_LINHA);
            linhasArquivo.add(String.valueOf(radio.isFlagTransmissao()));
            linhasArquivo.add(SEPARADOR_LINHAS);
            
        }
        
        Map<Integer, Obstaculo> obstaculos = ambiente.getObstaculos();
        
        for (Map.Entry<Integer, Obstaculo> entrySet : obstaculos.entrySet()) {
            
            Obstaculo obstaculo = entrySet.getValue();
            
            linhasArquivo.add(Obstaculo.class.getSimpleName());
            linhasArquivo.add(SEPARADOR_ELEMENTOS_LINHA);
            linhasArquivo.add(String.valueOf(obstaculo.getLocalizacao().getX1()));
            linhasArquivo.add(SEPARADOR_ELEMENTOS_LINHA);
            linhasArquivo.add(String.valueOf(obstaculo.getLocalizacao().getY1()));
            linhasArquivo.add(SEPARADOR_ELEMENTOS_LINHA);
            linhasArquivo.add(String.valueOf(obstaculo.getLocalizacao().getX2()));
            linhasArquivo.add(SEPARADOR_ELEMENTOS_LINHA);
            linhasArquivo.add(String.valueOf(obstaculo.getLocalizacao().getY2()));
            linhasArquivo.add(SEPARADOR_ELEMENTOS_LINHA);
            linhasArquivo.add(String.valueOf(obstaculo.getTipo()));
            linhasArquivo.add(SEPARADOR_ELEMENTOS_LINHA);
            linhasArquivo.add(String.valueOf(obstaculo.getTipoMaterial()));
            linhasArquivo.add(SEPARADOR_LINHAS);
            
        }
        
        return linhasArquivo;
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        try {
            
            File novoArquivo = criarArquivo();
            PrintWriter escritorArquivo = new PrintWriter(novoArquivo);
            List<String> conteudoArquivo = reunirConteudo();
            
            for(String elementoConteudo : conteudoArquivo) {
                
                if(!elementoConteudo.equals(SEPARADOR_LINHAS)){
                    escritorArquivo.append(elementoConteudo);                    
                } else {
                    escritorArquivo.println();
                }
                
            }
            
            escritorArquivo.flush();
            escritorArquivo.close();
            
            JOptionPane.showMessageDialog(null, "Arquivo salvo com sucesso");
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível salvar o arquivo");
            ex.printStackTrace();
        }       
        
    }
    
}
