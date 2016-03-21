/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.modelo.propagacao;

import java.util.List;
import javax.annotation.Resource;
import simulador.modelo.Obstaculo;

/**
 *
 * @author Fernando
 */
public class LogNormal extends ModeloEmpiricoPropagacao{
    
    //private static final String formula = "PL(d0) + 10*n*log(d/d0)";
    private final FreeSpacePathLoss modeloFSPL;
    @Resource
    private float d0 = 1;
    @Resource
    private float n = 2;
    //private final String formulaDesvLento = "10*n*log(d/d0)";
    
    public LogNormal(FreeSpacePathLoss modelo) {
        
        super(true, true, false);
        
        this.modeloFSPL = modelo;
        
//        controleParcelas.put(STR_PERDA_PERCURSO, Boolean.TRUE);
//        controleParcelas.put(STR_DESVANECIMENTO_LENTO, Boolean.TRUE);
//        controleParcelas.put(STR_DESVANECIMENTO_RAPIDO, Boolean.FALSE);
//        this.sombreamento = false;
//        this.multiPercurso = false;
        
    }
    
    protected LogNormal(LogNormal modelo) {
    
        super(modelo.percursoMedio, modelo.sombreamento, modelo.multiPercurso);
        super.dp = modelo.dp;
        this.d0 = modelo.d0;
        this.n = modelo.n;        
        this.modeloFSPL = modelo.modeloFSPL;
        
//        this.controleParcelas.put(
//                STR_PERDA_PERCURSO, modelo.controleParcelas.get(STR_PERDA_PERCURSO)
//        );
//        
//        this.controleParcelas.put(
//                STR_DESVANECIMENTO_LENTO, modelo.controleParcelas.get(STR_DESVANECIMENTO_LENTO)
//        );
//        
//        this.controleParcelas.put(
//                STR_DESVANECIMENTO_RAPIDO, modelo.controleParcelas.get(STR_DESVANECIMENTO_RAPIDO)
//        );
        
//        this.percursoMedio = modelo.percursoMedio;
//        this.sombreamento = modelo.sombreamento;
//        this.multiPercurso = modelo.multiPercurso;
    
    }

//    @Override
//    public float obterPerdaPropagacao(float distancia, float frequencia, int numParedes, int numAndares) {
//        System.out.println("LogNormal.obterPerda");
//        //return modeloFSPL.obterPerdaPropagacao(d0, frequencia) + 10*n*(float)Math.log10(distancia/d0);
//        return super.obterPerdaPropagacao(distancia, frequencia, 0);
//    }  

    @Override
    public String getNome() {
        return "LOG DISTANCE";
    }

    @Override
    public String getFormula() {
        return "PL(d0) + 10*n*log(d/d0) + X(0,dp)";
    }

    @Override
    public float calcularPerdaPercursoMedio(float distancia, float frequencia) {
        //System.out.println("LogNormal.getPerdaPercurso");
        return this.modeloFSPL.calcularPerdaPercursoMedio(d0, frequencia) +
                10*n*(float)Math.log10(distancia/d0);
    }
    
    @Override
    public float calcularPerdaParedes(List<Obstaculo> paredes) {
        return 0;
    }
    
    @Override
    public float calcularPerdaAndares(List andares) {
        return 0;
    }

    

    
    
//    @Override
//    public float calcularPerdaSombreamento(float distancia, float frequencia) {
//        System.out.println("LogNormal.getDesvanecimentoLento");
//        return 0;
//    }
//
//    @Override
//    public float calcularPerdaMultiPercurso(float distancia, float frequencia) {
//        System.out.println("LogNormal.getDesvanecimentoRapido");
//        return 0;
//    }

    @Override
    public ModeloEmpiricoPropagacao clonar() {
        return new LogNormal(this);
    }
    
}
