/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.modelo.propagacao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Resource;
import org.mbertoli.jfep.Parser;
import simulador.modelo.Obstaculo;
import simulador.modelo.Prototype;

/**
 *
 * @author Fernando
 */
// Classe abstrata que representa a estrutura geral de um modelo de propagação.
// Implementa o pattern 'Prototype' por meio da interface de mesmo nome.
public abstract class ModeloEmpiricoPropagacao implements Prototype {

    @Resource
    protected Boolean percursoMedio;
    @Resource
    protected Boolean sombreamento;
    @Resource
    protected Boolean multiPercurso;
    @Resource
    protected float dp;

    public ModeloEmpiricoPropagacao(Boolean perdaPercurso, Boolean sombreamento, Boolean multiPercurso) {
          //  String parcPerdaPercurso) {

        //this.interpretadorPerdaPercurso = new Parser(parcPerdaPercurso);
        //this.interpretadorDesvLento = new Parser(parcDesvLento);
        //this.interpretadorDesvRapido = new Parser(parcDesvRapido);

        this.percursoMedio = perdaPercurso;
        this.sombreamento = sombreamento;
        this.multiPercurso = multiPercurso;
        
    }
    
    //public abstract float obterPerdaPropagacao(float distancia, float frequencia);

    public float obterPerdaPropagacao(float distancia, float frequencia, List<Obstaculo> obstaculos, List andares) {

        // perda total
        // inicialmente nula
        float perda = 0;

        // verificação através do mapa 'controleParcelas'
        if (percursoMedio) {
            perda = perda +
                    calcularPerdaPercursoMedio(distancia, frequencia) +
                    calcularPerdaParedes(obstaculos) +
                    calcularPerdaAndares(andares);
        }
        if (sombreamento) {
            perda = perda + calcularPerdaSombreamento(0, dp);
        }
        if (multiPercurso) {
            perda = perda + calcularPerdaMultiPercurso(distancia, frequencia);
        }
        
        return perda;

    }

    public abstract float calcularPerdaPercursoMedio(float distancia, float frequencia);
    
    public abstract float calcularPerdaParedes(List<Obstaculo> paredes);
    
    public abstract float calcularPerdaAndares(List andares);
    
    public float calcularPerdaSombreamento(float media, float dp) {            
        return (float)new Random().nextGaussian()*dp+media;
    }
    
    public float calcularPerdaMultiPercurso(float distancia, float frequencia) {
        return 0;
    }

    public abstract String getNome();

    public abstract String getFormula();

    @Override
    public abstract ModeloEmpiricoPropagacao clonar();
    
}
