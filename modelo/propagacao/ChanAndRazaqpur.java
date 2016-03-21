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
public class ChanAndRazaqpur extends ModeloEmpiricoPropagacao {

    private static final float PERDA_PAREDE_PADRAO = 10;
    private final FreeSpacePathLoss modeloFSPL;
    @Resource
    private float Lp = PERDA_PAREDE_PADRAO;

    public ChanAndRazaqpur(FreeSpacePathLoss modeloFSPL) {

        
        super(true, false, false);
        
        this.modeloFSPL = modeloFSPL;

//        this.sombreamento = false;
//        this.multiPercurso = false;

    }

    protected ChanAndRazaqpur(ChanAndRazaqpur modelo) {

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
        
        //float perdaParedes = 0;
        float perdaParedes = paredes.size()*Lp;
        
//        for(Obstaculo parede : paredes) {
//            perdaParedes = perdaParedes + Lp; 
//        }
        
        return perdaParedes;
        
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
        return "CHAN & RAZAQPUR";
    }

    @Override
    public String getFormula() {
        return "32.45 + 20*log(d) + 20*log(f) + Lp";
    }

    @Override
    public ModeloEmpiricoPropagacao clonar() {
        return new ChanAndRazaqpur(this);
    }

}
