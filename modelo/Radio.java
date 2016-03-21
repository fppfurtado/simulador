/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.modelo;

import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Resource;

/**
 *
 * @author Fernando
 */
// Classe responsável por representar um rádio no ambiente.
// É um objeto físico no ambiente. Implementa funções de Transmissor e Receptor.
public class Radio extends ObjetoAmbiente implements
        SistemaWireless.Transmissor, PropertyChangeListener {

    public static boolean DEBUG = false;                                        // chave para ativar a depuração
    public static final float FREQUENCIA_PADRAO = 2.4f;                         // referencia de frequencia em GHz
    private static final AtomicInteger contadorId = new AtomicInteger(0);       // Mecanismo estático gerador de numeros identificadores
    @Resource
    private final int id = contadorId.getAndIncrement();                        // numero identificador do objeto
    private Point2D localizacao;                                                // localização do objeto: um ponto.
    @Resource
    private float frequencia;                                                   // frequencia do radio em GHz
    @Resource
    private float potencia;                                                     // potencia do radio em dBm
    @Resource
    private boolean flagTransmissao;                                            // flag para informar se o radio está transmitindo sinal ou não
    private final Antena antena;                                                // Antena do Radio, responsável por executar subprocessos (Delegação).

    // Construtor reduzido
    public Radio(float xLocalizacao, float yLocalizacao) throws IllegalArgumentException{
        this(xLocalizacao,
                yLocalizacao,
                FREQUENCIA_PADRAO,
                Float.NEGATIVE_INFINITY,
                0f
        );
    }

    // Construtor geral
    public Radio(float xLocalizacao, float yLocalizacao, float frequencia,
            float potencia, float ganho) throws IllegalArgumentException{

        if (DEBUG) {
            System.out.println("new Radio(" + potencia + "," + ganho + "," + xLocalizacao + "," + yLocalizacao + ")");
        }
        
        antena = new Antena(ganho);

        setLocalizacao(xLocalizacao, yLocalizacao);
        setFrequencia(frequencia);
        setPotencia(potencia);
        
    }

    // Gets & Sets
    public int getId() {

        if (DEBUG) {
            System.out.println("Radio.getId: " + id);
        }

        return id;

    }
    
    public float getFrequencia() {

        if (DEBUG) {
            System.out.println("Radio.getFrequencia: " + frequencia);
        }

        return frequencia;

    }

    public final void setFrequencia(float frequencia) throws IllegalArgumentException {

        if (DEBUG) {
            System.out.println("Radio.setFrequencia: " + frequencia);
        }

        if (frequencia <= 0) {
            throw new IllegalArgumentException("Valor negativo em Radio.setFrequencia");
        }

        this.frequencia = frequencia;

    }

    public float getPotencia() {

        if (DEBUG) {
            System.out.println("Radio.getPotencia: " + potencia);
        }

        return potencia;

    }

    public final void setPotencia(float potencia) {

        if (DEBUG) {
            System.out.println("Radio.setPotencia: " + potencia);
        }

        this.potencia = potencia;

    }

    public float getGanho() {

        if (DEBUG) {
            System.out.println("Radio.getGanho: " + antena.getGanho());
        }

        return antena.getGanho();

    }

    public final void setGanho(float ganho) {

        if (DEBUG) {
            System.out.println("Radio.setGanho: " + ganho);
        }

        antena.setGanho(ganho);

    }

    public boolean isFlagTransmissao() {

        if (DEBUG) {
            System.out.println("Radio.isFlagSimulacao: " + flagTransmissao);
        }

        return flagTransmissao;

    }

    public void setFlagTransmissao(boolean flagTransmissao) {

        if (DEBUG) {
            System.out.println("Radio.setFlagSimulacao: " + flagTransmissao);
        }

        this.flagTransmissao = flagTransmissao;

    }

    // implementação deste método especifica que a localização deste objeto é
    // descrita por um pontos apenas
    @Override
    public Point2D getLocalizacao() {

        if (DEBUG) {
            System.out.println("Radio.getLocalizacao: " + localizacao);
        }

        return localizacao;

    }

    // Proibido valores negativos
    public final void setLocalizacao(float x, float y) throws IllegalArgumentException {

        if (DEBUG) {
            System.out.println("Radio.setLocalizacao: (" + x + ", " + y + ")");
        }

        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Valor negativo em Radio.setLocalizacao");
        }

        Point2D p = new Point2D.Float(x, y);
        this.localizacao = p;

    }

    // Método da interface 'Transmissor' implementado
    @Override
    public void transmitirSinal() {

        if (DEBUG) {
            System.out.println("Radio.transmitir: "+flagTransmissao);
        }

        // sinaliza a transmissão
        //flagTransmissao = true;
        // execução da tarefa delegada para antena
        if(flagTransmissao){
            float ERP = antena.propagarSinal();
            notificadorListeners.firePropertyChange("transmissao", this, new SinalRadioFrequencia(frequencia, ERP) // novo sinal criado e transmitido
                            ); 
          //  antena.propagarSinal();
        } 
        //else {
         //   notificadorListeners.firePropertyChange("transmissao", this, null);
        //}

    }

    @Override
    public void adicionarListener(String nomePropridade, PropertyChangeListener observer) {

        if (DEBUG) {
            System.out.println("Radio.addPropertyChangeListener: " + observer.getClass().getSimpleName());
        }

        super.adicionarListener(nomePropridade, observer); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        float antigoValor, novoValor;

        antigoValor = (float) evt.getOldValue();
        novoValor = (float) evt.getNewValue();

        switch (evt.getPropertyName()) {
            case "comprimento":
                
                setLocalizacao(
                        (float) localizacao.getX(),
                        (float) localizacao.getY() * (novoValor / antigoValor)
                );
                break;
                
            case "largura":
                
                setLocalizacao(
                        (float) localizacao.getX() * (novoValor / antigoValor),
                        (float) localizacao.getY()
                );
                break;
                
        }

    }

    // Classe interna delegada responsável por representar a antena do objeto Radio
    // Antena pode ser isotrópico (ganho = 0) ou não
    class Antena {

        // ganho de diretividade
        // Caso ganho > 0, considera-se a antena do tipo omidirecional.
        @Resource
        protected float ganho;

        protected Antena(float ganho) {

            if (DEBUG) {
                System.out.println("new Antena(" + ganho + ")");
            }

            setGanho(ganho);

        }

        // Gets & Sets
        public float getGanho() {

            if (DEBUG) {
                System.out.println("Antena.getGanho: " + ganho);
            }

            return ganho;

        }

        public final void setGanho(float ganho) throws IllegalArgumentException {

            if (DEBUG) {
                System.out.println("Antena.setGanho: " + ganho);
            }

            if (ganho < 0) {
                throw new IllegalArgumentException("Valor negativo em Antena.setGanho");
            }

            this.ganho = ganho;

        }

        // Método responsável por notificar os interessados de que o sinal está
        // sendo transmitido
        private float propagarSinal() {

            if (DEBUG) {
                System.out.println("Antena.propagarSinal");
            }

            // transmissoa é modelada como uma Thread
            // Processo de transmissão precisa ser executado de forma concorrente
            //Thread transmissao;

            // definido a transmissão
            //transmissao = new Thread(new Runnable() {

              //  @Override
              //  public void run() {

                    // enquanto o radio estiver transmitindo...
//                    if (flagTransmissao) {
                      //  try {

                            if (DEBUG) {
                                System.out.println("Radio " + getId() + " transmitindo...");
                            }

                            // Potencia Efetivamente Irradiada
                            float ERP = potencia + ganho;
                            // notificar interessados da transmissao
                                                       return ERP;
                            // intervalo entre as notificações, em milissegundos
                        //    Thread.sleep(1000);

//                        } catch (InterruptedException ex) {
//                            System.out.println("Exceção " + ex.toString() + ": " + ex.getMessage());
//                            ex.printStackTrace();
//                        }
  //                  } 

                //}

            //});

            // iniciar transmissão
            //transmissao.start();


        }

    }

}
