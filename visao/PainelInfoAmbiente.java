/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.visao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Fernando
 */
public class PainelInfoAmbiente extends JPanel implements BuscaComponenteInterno {

    public static boolean DEBUG = false;
    public static final String INFO_COMPRIMENTO = "Comprimento";
    public static final String INFO_LARGURA = "Largura";
    public static final String INFO_DISTANCIA_PONTOS = "Distancia Pontos";
    private static final String TITULO = "Ambiente";    
    private static final String FONTE_TEXTO = "Verdana";
    public static final int TAM_TEXTO = 3;
    public static final int COR_TEXTO_RGB = 0xEFEFEF;
    private final Map<String, Container> referenciasContaineres = new LinkedHashMap();
    private final Map<String, Rotulo> referenciasRotulos = new LinkedHashMap();

    public PainelInfoAmbiente() {

        super(new GridBagLayout());

        criarLetreiros();
        anexarLetreiros();
        
        TitledBorder titulo = BorderFactory.createTitledBorder(TITULO);
        titulo.setTitleColor(new Color(COR_TEXTO_RGB));
        titulo.setTitleJustification(TitledBorder.CENTER);
        
        setBorder(titulo);
        setBackground(new Color(110, 110, 110));

    }
    
    private void criarLetreiros() {
        
        //referenciasRotulos.clear();
        
        referenciasRotulos.put(INFO_COMPRIMENTO, new Rotulo(INFO_COMPRIMENTO+": "));
        referenciasRotulos.put(INFO_LARGURA, new Rotulo(INFO_LARGURA+": "));
        referenciasRotulos.put(INFO_DISTANCIA_PONTOS, new Rotulo(INFO_DISTANCIA_PONTOS+": "));        
        
//        System.out.println("criarLetreiros");
//        referenciasRotulos.clear();
//
//        for (Map.Entry<String, String> rotulo : rotulos.entrySet()) {
//            System.out.println("rotulo: "+rotulo.getValue());
//            referenciasRotulos.put(rotulo.getKey(), new Rotulo(rotulo.getValue()+": "));
//        }
//        
        //referenciasContaineres.clear();

        for (Map.Entry<String, Rotulo> referenciaRotulo : referenciasRotulos.entrySet()) {

            Container container = new Container();
            container.setLayout(new BorderLayout());
            container.add(referenciaRotulo.getValue());

            referenciasContaineres.put(referenciaRotulo.getKey(), container);

        }

    }

//    private void criarLetreiros() {
//
//        referenciasRotulos.put(INFO_COMPRIMENTO, new Rotulo(INFO_COMPRIMENTO+": "));
//        referenciasRotulos.put(INFO_LARGURA, new Rotulo(INFO_LARGURA+": "));
//        referenciasRotulos.put(INFO_DISTANCIA_PONTOS, new Rotulo(INFO_DISTANCIA_PONTOS+": "));
//        
//        for (Map.Entry<String, Rotulo> referenciaRotulo : referenciasRotulos.rotulo()) {
//
//            Container container = new Container();
//            container.setLayout(new BorderLayout());
//            container.add(referenciaRotulo.getValue());
//
//            referenciasContaineres.put(referenciaRotulo.getKey(), container);
//
//        }
//
//    }

    private void anexarLetreiros() {

        GridBagConstraints gbc = new GridBagConstraints();
        int x;

        x = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 7, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;

        for (Map.Entry<String, Container> container : referenciasContaineres.entrySet()) {
            gbc.gridx = x;
            add(container.getValue(), gbc);
            x++;
        }

    }

    public void atualizarInformacaoLetreiro(String rotulo, Object conteudo) {
        referenciasRotulos.get(rotulo).exibirInformacao(conteudo.toString());
    }

    @Override
    public JLabel obterReferencia(String chave) {
        return referenciasRotulos.get(chave);
    }

//    @Override
//    public void propertyChange(PropertyChangeEvent evt) {
//        atualizarInformacaoLetreiro(evt.getPropertyName(), String.valueOf(evt.getNewValue()));
//    }

    final class Rotulo extends JLabel {
        
        private final String informacaoFixa;

        public Rotulo(String informacaoFixa) {
            this.informacaoFixa = informacaoFixa;
            exibirInformacao("");
        }       

        public void exibirInformacao(String texto) {
            texto = informacaoFixa + texto;
            setText(formatarStrigHTML(texto, FONTE_TEXTO, TAM_TEXTO, COR_TEXTO_RGB));
        }

        public String formatarStrigHTML(String texto, String face, int size, int corRGB) {

            String inicio, fim, stringHTML;

            String stringCor = Integer.toHexString(corRGB);
            
            inicio = "<html><font face='" + face + "' size=" + size + " color=#" + stringCor + ">";
            fim = "</font></html>";

            stringHTML = inicio + texto + fim;

            return stringHTML;

        }

    }

}
