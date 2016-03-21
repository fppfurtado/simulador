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
 * @author fernandofurtado
 */
public class SeidelAndRappaport extends ModeloEmpiricoPropagacao{

    private final FreeSpacePathLoss modeloFSPL;
    private static final float PERDA_AFS_PADRAO = 1.39f;
    private static final float PERDA_AFC_PADRAO = 2.38f;
    @Resource
    private float AFS = PERDA_AFS_PADRAO;
    @Resource
    private float AFC = PERDA_AFC_PADRAO;
    
    public SeidelAndRappaport(FreeSpacePathLoss modeloFSPL) {
        
        super(true, false, false);
        this.modeloFSPL = modeloFSPL;
        
        this.sombreamento = false;
        this.multiPercurso = false;
      
    }

    protected SeidelAndRappaport(SeidelAndRappaport modelo) {
        
        super(modelo.percursoMedio, modelo.sombreamento, modelo.multiPercurso);
        super.dp = modelo.dp;
        this.modeloFSPL = modelo.modeloFSPL;
//        this.percursoMedio = modelo.percursoMedio;
//        this.sombreamento = modelo.sombreamento;
//        this.multiPercurso = modelo.multiPercurso;
    }

    @Override
    public float calcularPerdaPercursoMedio(float distancia, float frequencia) {     
        return modeloFSPL.calcularPerdaPercursoMedio(distancia, frequencia);
    }   
    
@Override
    public float calcularPerdaParedes(List<Obstaculo> paredes) {
        
        float perdaObstaculos = 0;
        
        for(Obstaculo parede : paredes) {
        
            if(parede.getTipoMaterial().equals(Obstaculo.TipoMaterial.CONCRETO)) {
                perdaObstaculos = perdaObstaculos + AFC;
            } else {
                perdaObstaculos = perdaObstaculos + AFS;
            }
            
        }       
       
        return perdaObstaculos;
        
    }
    
    @Override
    public float calcularPerdaAndares(List andares) {
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
        return "SEIDEL & RAPPAPORT";
    }

    @Override
    public String getFormula() {
        return "32.45 + 20*log(d) + 20*log(f) + ASF + AFC";
    }

    @Override
    public ModeloEmpiricoPropagacao clonar() {
        return new SeidelAndRappaport(this);
    }
    
}
