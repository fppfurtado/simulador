/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.visao;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Fernando
 */
public class PainelDesign extends JPanel implements ActionListener {
    
    public static boolean DEBUG = false;
    private final Map<Integer, Radio2D> radios2D = new LinkedHashMap();
    private final Map<Integer, Obstaculo2D> obstaculos2D = new LinkedHashMap();
    private final Map<Integer, Map<Integer, Celula>> matrizCelulas = new LinkedHashMap(2000);
    private final PainelDesignMouseListener mouseListener = new PainelDesignMouseListener();
    private String opcaoDesign = null;
    private boolean flagSimulacao = false;
    private BufferedImage imagem = null;
    private BufferedImage frameInicialAnimacao = null;
    
    public PainelDesign(int largura, int altura) {
        
        super(null);
        
        if (DEBUG) {
            System.out.println("PainelDesign.paintComponent");
        }
        
        this.imagem = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
        
        setSize(new Dimension(largura, altura));
        setPreferredSize(getSize());
        setBorder(BorderFactory.createEtchedBorder());
        
        addMouseListener((MouseListener) mouseListener);
        addMouseMotionListener((MouseMotionListener) mouseListener);

//        addComponentListener(
//                new ComponentAdapter() {
//                    @Override
//                    public void componentResized(ComponentEvent e) {
//                        imagem = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
//                        firePropertyChange("tamanhoImagem", this, imagem);
//                    }
//                }
//        );
    }
    
    public BufferedImage getImagem() {
        
        if (DEBUG) {
            System.out.println("PainelDesign.getImagem");
        }
        
        return imagem;
        
    }
    
    public void setImagem(BufferedImage imagem) {
        
        if (DEBUG) {
            System.out.println("PainelDesign.setImagem");
        }
        
        this.imagem = imagem;
        
    }

//    public ObjetoAmbiente2D getObjetoSelecionado() {
//        return objetoSelecionado;
//    }
    public String getOpcaoDesign() {
        
        if (DEBUG) {
            System.out.println("PainelDesign.getOpcaoDesign");
        }
        
        return opcaoDesign;
        
    }
    
    public boolean isFlagSimulacao() {
        
        if (DEBUG) {
            System.out.println("PainelDesign.isFlagSimulacao: " + flagSimulacao);
        }
        
        return flagSimulacao;
    }
    
    public void setFlagSimulacao(boolean flagSimulacao) {
        
        if (DEBUG) {
            System.out.println("PainelDesign.setFlagSimulacao: " + flagSimulacao);
        }
        
        this.flagSimulacao = flagSimulacao;
        firePropertyChange("flagSimulacao", this, flagSimulacao);
        
    }
    
    public Map<Integer, Map<Integer, Celula>> getMatrizCelulas() {
        
        if (DEBUG) {
            System.out.println("PainelDesign.getCelulas");
        }
        
        return matrizCelulas;
        
    }
    
    public Map<Integer, Radio2D> getRadios2D() {
        return radios2D;
    }
    
    public Map<Integer, Obstaculo2D> getObstaculos2D() {
        return obstaculos2D;
    }
    
    public void limparObjetos2D() {

        //objetos2D.clear();
        radios2D.clear();
        obstaculos2D.clear();
        firePropertyChange("objetos2D", this, null);
        //objetoSelecionado = null;

    }
    
    public void vincularControleDesenhoGrade(JCheckBoxMenuItem checkBox) {
        checkBox.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                for (Map.Entry<Integer, Map<Integer, Celula>> linhaCelulas : matrizCelulas.entrySet()) {
                    for (Map.Entry<Integer, Celula> colunaCelulas : linhaCelulas.getValue().entrySet()) {
                        Celula cel = colunaCelulas.getValue();
                        cel.setBorda(checkBox.getState());
                    }
                }
                
                pintarCelulas();
                pintarObjetos2D();
                repaint();
                
            }
        });
        
    }
    
    public void pintarCelulas() {
        
        if (DEBUG) {
            System.out.println("PainelDesign.pintarCelulas");
        }
        
        Graphics2D contextoGraficoImagem = imagem.createGraphics();
        
        for (Map.Entry<Integer, Map<Integer, Celula>> linhaCelulas : matrizCelulas.entrySet()) {
            
            for (Map.Entry<Integer, Celula> colunaCelulas : linhaCelulas.getValue().entrySet()) {
                
                Celula cel = colunaCelulas.getValue();
                
                contextoGraficoImagem.setColor(new Color(cel.getCorFundoRGB()));
                contextoGraficoImagem.fillRect(cel.x, cel.y, cel.width, cel.height);
                
                contextoGraficoImagem.setColor(new Color(cel.getCorBordaRGB()));
                contextoGraficoImagem.draw(cel);
                
            }
            
        }
        
        contextoGraficoImagem.dispose();
        
    }

    //TESTES    
//    public void depurarPinturaCelulas() {
//    
//        
//
//        for (Map.Entry<Integer, Map<Integer, Celula>> linhaCelulas : matrizCelulas.entrySet()) {
//
//            for (Map.Entry<Integer, Celula> colunaCelulas : linhaCelulas.getValue().entrySet()) {
//
//                Graphics2D contextoGraficoImagem = imagem.createGraphics();
//                Celula cel = colunaCelulas.getValue();
//
//                contextoGraficoImagem.setColor(new Color(cel.getCorFundoRGB()));
//                contextoGraficoImagem.fillRect(cel.x, cel.y, cel.width, cel.height);
//
//                contextoGraficoImagem.setColor(new Color(cel.getCorBordaRGB()));
//                contextoGraficoImagem.draw(cel);
//                
//                contextoGraficoImagem.dispose();
//                
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(PainelDesign.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                
//                //paintComponent(contextoGraficoImagem);
//                repaint();
//
//            }
//
//        }
//
//        
//    
//    }
    public int[] buscarCelula(Point p) {
        
        if (DEBUG) {
            System.out.println("PainelDesign.buscarCelula");
        }
        
        int larguraCelula, alturaCelula;
        BigDecimal lin, col;
        
        larguraCelula = matrizCelulas.get(0).get(0).width;
        alturaCelula = matrizCelulas.get(0).get(0).height;
        
        try {
            
            lin = new BigDecimal(String.valueOf(p.y)).divide(
                    new BigDecimal(String.valueOf(alturaCelula)), 0, RoundingMode.FLOOR);
            
            col = new BigDecimal(String.valueOf(p.x)).divide(
                    new BigDecimal(String.valueOf(larguraCelula)), 0, RoundingMode.FLOOR);
            
            return new int[]{lin.intValue(), col.intValue()};
            
        } catch (ArithmeticException ex) {
            JOptionPane.showMessageDialog(null, "Erro em operação com as células");
            ex.printStackTrace();
        }
        //System.out.println("cel("+lin+", "+col+")");
        return new int[]{0, 0};
        
    }
    
    public void adicionarRadio2D(Radio2D r2D) {
        
        if (DEBUG) {
            System.out.println("PainelDesign.adicionarObjeto2D");
        }

        //objetos2D.add(obj);
        radios2D.put(r2D.id, r2D);
        firePropertyChange("objetos2D", this, r2D);
        
    }
    
    public void adicionarObstaculo2D(Obstaculo2D o2D) {
        
        if (DEBUG) {
            System.out.println("PainelDesign.adicionarObjeto2D");
        }

        //objetos2D.add(obj);
        obstaculos2D.put(o2D.id, o2D);
        firePropertyChange("objetos2D", this, o2D);
        
    }
    
    public void removerRadio2D(Radio2D r2D) {
        
        if (true) {
            System.out.println("PainelDesign.adicionarObjeto2D");
        }

        //objetos2D.add(obj);
        radios2D.remove(r2D.id);
        firePropertyChange("objetos2D", r2D, null);
        
    }
    
    public void removerObstaculo2D(Obstaculo2D o2D) {
        
        if (DEBUG) {
            System.out.println("PainelDesign.adicionarObjeto2D");
        }

        //objetos2D.add(obj);
        obstaculos2D.remove(o2D.id);
        firePropertyChange("objetos2D", o2D, null);
        
    }
    
    public void pintarObjetos2D() {
        
        if (DEBUG) {
            System.out.println("PainelDesign.pintarObjetos2D");
        }
        
        Graphics contextoGrafico = imagem.createGraphics();
        
        for (Map.Entry<Integer, Radio2D> parRadio2D : radios2D.entrySet()) {
            parRadio2D.getValue().pintarObjeto(contextoGrafico);
        }
        
        for (Map.Entry<Integer, Obstaculo2D> parObstaculo2D : obstaculos2D.entrySet()) {
            parObstaculo2D.getValue().pintarObjeto(contextoGrafico);
        }
        
        contextoGrafico.dispose();
        
    }
    
    public void selecionarObjeto2D(ObjetoAmbiente2D obj2D) {
        
        try {
            
            for (Map.Entry<Integer, Radio2D> parRadio2D : radios2D.entrySet()) {
                parRadio2D.getValue().setFoco(false);
            }
            
            for (Map.Entry<Integer, Obstaculo2D> parObstaculo2D : obstaculos2D.entrySet()) {
                parObstaculo2D.getValue().setFoco(false);
            }
            
            obj2D.setFoco(true);
            firePropertyChange("objetoSelecionado", this, obj2D);
            
        } catch (NullPointerException ex) {
            firePropertyChange("objetoSelecionado", this, null);
        }
        
    }
    
    public void desenharLinhas(List<Line2D> linhas) {
        
        BufferedImage copiaFrameInicial = criarCopiaImagem(frameInicialAnimacao);
        Graphics2D contextoGraficoFrameInicial = (Graphics2D) copiaFrameInicial.createGraphics();
        
        contextoGraficoFrameInicial.setColor(new Color(Obstaculo2D.COR_SEM_FOCO));
        contextoGraficoFrameInicial.setStroke(new BasicStroke(Obstaculo2D.ESPESSURA));
        
        for (Line2D linha : linhas) {
            contextoGraficoFrameInicial.draw(linha);
        }
        
        contextoGraficoFrameInicial.dispose();
        
        imagem = copiaFrameInicial;
        repaint();
        
    }
    
    public void atualizarFrameInicial() {
        //System.out.println("atualizarFrameInicial");
        frameInicialAnimacao = criarCopiaImagem(imagem);
    }
    
    private BufferedImage criarCopiaImagem(BufferedImage bi) {
        
        if (DEBUG) {
            System.out.println("GerenciadorPainelDesign.criarCopiaImagem");
        }
        
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.

        if (DEBUG) {
            System.out.println("PainelDesign.paintComponent");
        }
        
        Graphics2D contextoGrafico = (Graphics2D) g.create();
        
        contextoGrafico.drawImage(imagem, 0, 0, this);
        contextoGrafico.dispose();
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (DEBUG) {
            System.out.println("PainelDesign.actionPerformed");
        }
        
        opcaoDesign = e.getActionCommand();
        
    }
    
    class PainelDesignMouseListener extends MouseAdapter {
        
        protected Point pontoMouseMoved = null;
        protected Point pontoMousePressed = null;
        protected Point pontoMouseDragged = null;
        protected Point pontoMouseReleased = null;
        protected Point pontoMouseClicked = null;
        
        @Override
        public void mouseMoved(MouseEvent e) {
            
            if (DEBUG) {
                System.out.println("GerenciadorPainelDesign.mouseMoved");
            }
            
            pontoMouseMoved = e.getPoint();
            
            pintarCelulas();
            
            int[] coordCelula = buscarCelula(pontoMouseMoved);
            Celula celula = matrizCelulas.get(coordCelula[0]).get(coordCelula[1]);
            Graphics2D contextoGrafico = imagem.createGraphics();

            //System.out.println("cel["+coordCelula[0]+"]["+coordCelula[1]+"]");
            contextoGrafico.setColor(new Color(PainelDesign.Celula.getCorSelecao()));
            contextoGrafico.draw(celula);
            contextoGrafico.dispose();
            
            pintarObjetos2D();
            repaint();
            
            firePropertyChange("moved", this, pontoMouseMoved);
            
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
            
            if (DEBUG) {
                System.out.println("GerenciadorPainelDesign.mousePressed");
            }
            
            pontoMousePressed = e.getPoint();
            
            if (opcaoDesign.equals(BarraFerramentas.BT_OBSTACULO)) {
                atualizarFrameInicial();
            }

            //firePropertyChange("pressed", this, pontoMousePressed);
        }
        
        @Override
        public void mouseDragged(MouseEvent e) {
            
            if (DEBUG) {
                System.out.println("GerenciadorPainelDesign.mouseDragged");
            }
            
            pontoMouseDragged = e.getPoint();
            
            if (opcaoDesign.equals(BarraFerramentas.BT_OBSTACULO)) {
                
                List linhas = new ArrayList();
                linhas.add(new Line2D.Float(pontoMousePressed, pontoMouseDragged));
                
                desenharLinhas(linhas);
                
            }
            
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            
            if (DEBUG) {
                System.out.println("GerenciadorPainelDesign.mouseReleased");
            }
            
            pontoMouseReleased = e.getPoint();
            
            try {
                
                if (opcaoDesign.equals(BarraFerramentas.BT_OBSTACULO)) {
                    imagem = criarCopiaImagem(frameInicialAnimacao);
                    firePropertyChange(BarraFerramentas.BT_OBSTACULO, pontoMousePressed, pontoMouseReleased);
                }
                
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "Opção Design não definida!");
                ex.printStackTrace();
            }
            
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            
            if (DEBUG) {
                System.out.println("GerenciadorPainelDesign.mouseClicked");
            }
            
            pontoMouseClicked = e.getPoint();
            
            if (opcaoDesign.equals(BarraFerramentas.BT_SELECAO)) {
                
                int tamanhoSeletor = 6;
                int gapCentralizador = tamanhoSeletor / 2;
                Rectangle seletor = new Rectangle(
                        pontoMouseClicked.x - gapCentralizador,
                        pontoMouseClicked.y - gapCentralizador,
                        tamanhoSeletor,
                        tamanhoSeletor
                );
                
                boolean resultadoIntersecao = false;
                ObjetoAmbiente2D objeto2D = null;
                
                for (Map.Entry<Integer, Radio2D> parRadio2D : radios2D.entrySet()) {
                    objeto2D = parRadio2D.getValue();
                    Rectangle formaObj = (Rectangle) objeto2D.getForma();
                    resultadoIntersecao = seletor.intersects(formaObj);
                    
                    if (resultadoIntersecao) {
                        break;
                    }

                    //System.out.println("radio intersecao: "+resultadoIntersecao);
                }
                
                if (!resultadoIntersecao) {
                    
                    for (Map.Entry<Integer, Obstaculo2D> parObstaculo2D : obstaculos2D.entrySet()) {
                        objeto2D = parObstaculo2D.getValue();
                        Line2D formaObj = (Line2D) objeto2D.getForma();
                        resultadoIntersecao = seletor.intersectsLine(formaObj);
                        //System.out.println("obstaculo intersecao: "+resultadoIntersecao);

                        if (resultadoIntersecao) {
                            break;
                        }
                        
                    }
                    
                }
                
                if (resultadoIntersecao) {
                    selecionarObjeto2D(objeto2D);
                    pintarCelulas();
                    pintarObjetos2D();
                    repaint();
                    //atualizarFrameInicial();
                } else {
                    firePropertyChange(BarraFerramentas.BT_SELECAO, this, pontoMouseClicked);
                }
                
            } else if (opcaoDesign.equals(BarraFerramentas.BT_RADIO)) {
                firePropertyChange(BarraFerramentas.BT_RADIO, this, pontoMouseClicked);
            }
            
            //int[] coord = buscarCelula(pontoMouseClicked);
            //JOptionPane.showMessageDialog(null, coord[0]+", "+coord[1]);
            
        }
        
    }

    /**
     *
     * @author Fernando
     */
    public static class Celula extends Rectangle {
        
        public static final int COR_BORDA_PADRAO = Color.WHITE.getRGB();
        public static final int COR_FUNDO_PADRAO = 0xFFFFD2;
        private int corFundoRGB = COR_FUNDO_PADRAO;
        private int corBordaRGB = COR_FUNDO_PADRAO;
        private final Map<String, java.lang.Float> valores = new HashMap();
        
        public int getCorFundoRGB() {
            return corFundoRGB;
        }
        
        public void setCorFundoRGB(int corFundoRGB) {
            this.corFundoRGB = corFundoRGB;
        }
        
        public int getCorBordaRGB() {
            return corBordaRGB;
        }
        
        public void setCorBordaRGB(int corBordaRGB) {
            this.corBordaRGB = corBordaRGB;
        }
        
        public void setBorda(boolean borda) {
            if (borda) {
                corBordaRGB = COR_BORDA_PADRAO;
            } else {
                corBordaRGB = corFundoRGB;
            }
        }
        
        public Map<String, java.lang.Float> getValores() {
            return valores;
        }
        
        public void adicionarValor(String nome, float valor) {
            this.valores.put(nome, valor);
        }
        
        public java.lang.Float obterValor(String nome) {
            //System.out.println("Celula.obterValor(" + nome + "): " + valores.get(nome));
            return nome == null ? null : valores.get(nome);
        }
        
        public static int getCorSelecao() {
            return Color.BLACK.getRGB();
        }
        
    }
    
}
