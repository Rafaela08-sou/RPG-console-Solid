package rpg.personagens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rpg.habilidades.Habilidade;
import rpg.habilidades.HabilidadeDeDano;
import rpg.itens.Inventario;

/**
 * Classe abstrata Personagem
 * --------------------------
 * Representa o HERÓI controlado pelo jogador. Herda de Combatente (nome, vida,
 * ataque, defesa) e acrescenta o que só o herói tem:
 *   - energia (gasta para usar habilidades especiais);
 *   - uma lista de habilidades;
 *   - um inventário de itens.
 *
 * É ABSTRATA porque não existe "um personagem genérico": o jogador sempre é um
 * Guerreiro, um Mago ou um Arqueiro. Cada subclasse define seus atributos e sua
 * habilidade especial, e implementa getClasse().
 *
 * CONCEITOS DE POO:
 *  - HERANÇA: Personagem herda de Combatente; Guerreiro/Mago/Arqueiro herdam daqui.
 *  - COMPOSIÇÃO: o Personagem TEM um Inventario e TEM Habilidades.
 *  - ENCAPSULAMENTO: atributos private, acesso controlado por métodos.
 *  - POLIMORFISMO: getClasse() é abstrato; cada subclasse responde do seu jeito.
 *
 * SOLID aplicado aqui:
 *  - L (Liskov): o Combate recebe um "Personagem" e funciona com qualquer
 *    subclasse, sem precisar de "if" para saber qual é.
 *  - O: para criar uma nova classe de herói (ex.: Ladino) basta criar uma
 *    subclasse; nada aqui muda.
 */
public abstract class Personagem extends Combatente {

    /** Energia máxima de qualquer herói. */
    private static final int ENERGIA_MAXIMA = 20;

    /** Todo herói tem este ataque básico, que não gasta energia. */
    private static final Habilidade ATAQUE_BASICO =
            new HabilidadeDeDano("Ataque básico", 0, 1.0, 1);

    private int energia = ENERGIA_MAXIMA;
    private final List<Habilidade> habilidades = new ArrayList<>();
    private final Inventario inventario = new Inventario();

    /**
     * Construtor protegido: chamado pelas subclasses (Guerreiro, Mago...).
     * Já entrega ao herói o ataque básico e a habilidade especial da classe.
     *
     * A habilidade especial é recebida PRONTA por parâmetro (injeção de
     * dependência). Assim o construtor não precisa chamar métodos que as
     * subclasses poderiam sobrescrever, o que é uma prática mais segura.
     *
     * @param habilidadeEspecial golpe exclusivo da classe do herói
     */
    protected Personagem(String nome, int vida, int ataque, int defesa,
                         Habilidade habilidadeEspecial) {
        super(nome, vida, ataque, defesa);
        habilidades.add(ATAQUE_BASICO);
        habilidades.add(habilidadeEspecial);
    }

    /**
     * Método ABSTRATO: cada subclasse é obrigada a informar o nome da sua classe
     * (ex.: "Guerreiro"). Não há implementação aqui de propósito.
     */
    public abstract String getClasse();

    // ---------- Energia ----------

    /**
     * Tenta gastar energia.
     *
     * @return true se tinha energia suficiente (e gastou); false se não tinha
     *         (e nada é gasto)
     */
    public boolean gastarEnergia(int custo) {
        if (energia < custo) {
            return false;
        }
        energia -= custo;
        return true;
    }

    /** Recupera energia, sem passar do máximo. */
    public void recuperarEnergia(int valor) {
        energia = Math.min(ENERGIA_MAXIMA, energia + valor);
    }

    public int getEnergia() {
        return energia;
    }

    public int getEnergiaMaxima() {
        return ENERGIA_MAXIMA;
    }

    // ---------- Habilidades e inventário ----------

    /** @return lista SOMENTE LEITURA das habilidades do herói. */
    public List<Habilidade> getHabilidades() {
        return Collections.unmodifiableList(habilidades);
    }

    public Inventario getInventario() {
        return inventario;
    }
}
