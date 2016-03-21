/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.visao;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author fernandofurtado
 */
public class IconesFactory {
    
    public static ImageIcon criarIconeNovo(int tam) {
        
        ImageIcon icone = gerarIcone(IconeGUI.NOVO, tam);
        return icone;
    }   
    
    public static ImageIcon criarIconeAbrir(int tam) {
        
        ImageIcon icone = gerarIcone(IconeGUI.ABRIR, tam);
        return icone;
    }   
    
    public static ImageIcon criarIconeSalvar(int tam) {
        
        ImageIcon icone = gerarIcone(IconeGUI.SALVAR, tam);
        return icone;
    }   
    
    public static ImageIcon criarIconeSelecao(int tam) {
        
        ImageIcon icone = gerarIcone(IconeGUI.SELECAO, tam);
        return icone;
    }   
    
    public static ImageIcon criarIconeRadio(int tam) {
        
        ImageIcon icone = gerarIcone(IconeGUI.RADIO, tam);
        return icone;
    }   
    
    public static ImageIcon criarIconeObstaculo(int tam) {
        
        ImageIcon icone = gerarIcone(IconeGUI.PAREDE, tam);
        return icone;
    }   
    
    public static ImageIcon criarIconeExcluir(int tam) {
        
        ImageIcon icone = gerarIcone(IconeGUI.EXCLUIR, tam);
        return icone;
    }   
    
    private static ImageIcon gerarIcone(IconeGUI _icone, int tam){
        
        ImageIcon icone;
        Image imagem;
    
        icone = new ImageIcon(_icone.caminho);
        imagem = icone.getImage();
        imagem = imagem.getScaledInstance(tam, tam, Image.SCALE_DEFAULT);
        icone.setImage(imagem);
        
        return icone;
        
    }

    /**
     *
     * @author fernandofurtado
     */
    public static enum IconeGUI {

//        NOVO("src/simulador/visao/imagens/novo.png"),
//        ABRIR("src/simulador/visao/imagens/abrir.png"),
//        SALVAR("src/simulador/visao/imagens/salvar.png"),
//        SELECAO("src/simulador/visao/imagens/selecao.png"),
//        RADIO("src/simulador/visao/imagens/radio.png"),
//        PAREDE("src/simulador/visao/imagens/parede.png"),
//        EXCLUIR("src/simulador/visao/imagens/excluir.png");
        
        NOVO("imagens/novo.png"),
        ABRIR("imagens/abrir.png"),
        SALVAR("imagens/salvar.png"),
        SELECAO("imagens/selecao.png"),
        RADIO("imagens/radio.png"),
        PAREDE("imagens/parede.png"),
        EXCLUIR("imagens/excluir.png");
        
        private final String caminho;

        private IconeGUI(String caminho) {
            //this.caminho = new File(caminho).getAbsolutePath();
            this.caminho = getClass().getResource(caminho).getPath();
            //JOptionPane.showMessageDialog(null, this.caminho);
            //System.out.println(getClass().getResource("imagens"));
        }

    }
    
}
