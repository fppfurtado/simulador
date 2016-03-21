/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.modelo.propagacao;

import java.util.List;
import simulador.modelo.Obstaculo;

/**
 *
 * @author Fernando
 */
public class FreeSpacePathLoss extends ModeloEmpiricoPropagacao{
    
    public static boolean DEBUG = false;                                         // chave para ativar a depuração
    // Fórmula da parcela Perda de Percurso
   // public static final String FORMULA = "32.45+20*log("+STR_DISTANCIA+")+20*log("+STR_FREQUENCIA+")";
    //private final String formulaPerdaPercurso = ;
    // Fórmula da parcela Desvanecimento Lento
    //public static final String PARCELA_DESVANECIMENTO_LENTO = "0";
    // Fórmula da parcela Desvanecimento Rápido
    //public static final String PARCELA_DESVANECIMENTO_RAPIDO = "0";
    // Mapa para controlar quais parcelas serão ou não consideradas no momento de calcular a atenuação
    
    // Construtor Geral
    public FreeSpacePathLoss() {
        
        super(true, false, false);
        
        if(DEBUG){
            System.out.println("new FreeSpacePathLoss");
        }
        
       // interpretadorGeral = new Parser(getFormula());
//        interpretadorPerdaPercurso = new Parser(formulaPerdaPercurso);
//        interpretadorDesvLento = new Parser("0");
//        interpretadorDesvRapido = new Parser("0");
        
        // Por default, todas as parcelas são consideradas
//        controleParcelas.put(STR_PERDA_PERCURSO, Boolean.TRUE);
//        controleParcelas.put(STR_DESVANECIMENTO_LENTO, Boolean.FALSE);
//        controleParcelas.put(STR_DESVANECIMENTO_RAPIDO, Boolean.FALSE);
        
        
    }
    
    // Construtor protegido. Usado pelo pattern Prototype para clonagem do objeto
    protected FreeSpacePathLoss(FreeSpacePathLoss modelo){
        
//        super(
//                "32.45+20*log("+STR_DISTANCIA+")+20*log("+STR_FREQUENCIA+")"
//       );
        super(modelo.percursoMedio, modelo.sombreamento, modelo.multiPercurso);
        super.dp = modelo.dp;
//        
        if(DEBUG){
            System.out.println("new FSPL Clone");
        }
        
        //interpretadorGeral = new Parser(getFormula());
//        interpretadorPerdaPercurso = new Parser(formulaPerdaPercurso);
//        interpretadorDesvLento = new Parser("0");
//        interpretadorDesvRapido = new Parser("0");
        
//        this.controleParcelas.put(STR_PERDA_PERCURSO, modelo.controleParcelas.get(STR_PERDA_PERCURSO)
//        );
//        
//        this.controleParcelas.put(STR_DESVANECIMENTO_LENTO, modelo.controleParcelas.get(STR_DESVANECIMENTO_LENTO)
//        );
//        
//        this.controleParcelas.put(STR_DESVANECIMENTO_RAPIDO, modelo.controleParcelas.get(STR_DESVANECIMENTO_RAPIDO)
//        );
//        this.percursoMedio = modelo.percursoMedio;
//        this.sombreamento = modelo.sombreamento;
//        this.multiPercurso = modelo.multiPercurso;
        
        
    }

    @Override
    public float obterPerdaPropagacao(float distancia, float frequencia, List<Obstaculo> obstaculos, List andares) {
        //System.out.println("FSPL. obterPerdaPropagacao");
        return super.obterPerdaPropagacao(distancia, frequencia, obstaculos, andares);
    }
    
    @Override
    public float calcularPerdaPercursoMedio(float distancia, float frequencia) {
        return (float) (32.45 + 20*Math.log10(distancia) + 20*Math.log10(frequencia));
    }

    @Override
    public float calcularPerdaAndares(List andares) {
        return 0;
    }

    @Override
    public float calcularPerdaParedes(List<Obstaculo> paredes) {
        return 0;
    }
    
    

//    @Override
//    public float calcularPerdaSombreamento(float distancia, float frequencia) {
//        return 0;
//    }
//
//    @Override
//    public float calcularPerdaMultiPercurso(float distancia, float frequencia) {
//        return 0;
//    }
    
    @Override
    public String getNome() {
        return "FREE SPACE PATH LOSS";
    }

    @Override
    public String getFormula() {
        
        if(DEBUG){
            System.out.println("FreeSpacePathLoss.getFormula");
        }
        
       // return interpretadorPerdaPercurso.getInputString();
        return "32.45+20*log(d)+20*log(f)";
        
    }

    // Método usado pelo pattern Prototype
    @Override
    public ModeloEmpiricoPropagacao clonar() {
        
        if(DEBUG){
            System.out.println("FreeSpacePathLoss.clone");
        }
        
        // chama construtor protegido e passa referencia 'this' para ser clonado
        return new FreeSpacePathLoss(this);
        
    }

}

