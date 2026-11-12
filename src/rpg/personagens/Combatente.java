package rpg.personagens;

import rpg.combate.Alvo;
import rpg.combate.Atacante;

/**
 * Classe abstrata Combatente
 * --------------------------
 * É a "base comum" de TUDO que luta no jogo: o herói (Personagem) e os
 * inimigos (Inimigo). Ela guarda o que os dois têm em comum:
 * nome, vida, ataque e defesa, e implementa as regras de receber dano e curar.
 *
 * Por que existe? Para NÃO REPETIR código. Antes, Personagem e Inimigo tinham o
 * mesmo cálculo de dano copiado em dois lugares. Agora está só aqui (princípio
 * DRY: "Don't Repeat Yourself").
 *
 * Ela é ABSTRATA porque não existe um "combatente genérico" no jogo: só existem
 * tipos concretos (Guerreiro, Mago, Goblin...). Por isso não podemos fazer
 * "new Combatente(...)".
 *
 * CONCEITOS DE POO:
 *  - ABSTRAÇÃO: modela o conceito geral de "quem luta".
 *  - ENCAPSULAMENTO: todos os atributos são private. O único jeito de mudar a
 *    vida é por receberDano() ou curar(), que impedem valores absurdos (vida
 *    negativa ou acima do máximo).
 *  - HERANÇA: Personagem e Inimigo herdam tudo daqui.
 *
 * SOLID aplicado aqui:
 *  - S: cuida só dos atributos de combate e das regras de dano/cura.
 *  - L: qualquer subclasse pode ser usada como Combatente sem quebrar nada.
 */
public abstract class Combatente implements Atacante, Alvo {

    private final String nome;
    private final int vidaMaxima;
    private int vida;
    private int ataque;
    private int defesa;

    /**
     * Construtor protegido: só as subclasses podem chamá-lo (via super).
     *
     * @param nome   nome do combatente
     * @param vida   vida inicial (e também a vida máxima)
     * @param ataque valor de ataque
     * @param defesa valor de defesa
     */
    protected Combatente(String nome, int vida, int ataque, int defesa) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O combatente precisa de um nome.");
        }
        if (vida <= 0 || ataque < 0 || defesa < 0) {
            throw new IllegalArgumentException("Atributos inválidos para " + nome);
        }
        this.nome = nome;
        this.vidaMaxima = vida;
        this.vida = vida;
        this.ataque = ataque;
        this.defesa = defesa;
    }

    // ---------- Getters (leitura dos atributos privados) ----------

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int getVida() {
        return vida;
    }

    @Override
    public int getVidaMaxima() {
        return vidaMaxima;
    }

    @Override
    public int getAtaque() {
        return ataque;
    }

    public int getDefesa() {
        return defesa;
    }

    @Override
    public boolean estaVivo() {
        return vida > 0;
    }

    // ---------- Regras de combate ----------

    /**
     * Regra do dano: dano real = dano bruto - defesa, mas NUNCA menos que 1
     * (assim todo golpe sempre faz pelo menos um arranhão).
     * A vida nunca fica abaixo de 0.
     */
    @Override
    public int receberDano(int danoBruto) {
        int danoReal = Math.max(1, danoBruto - defesa);
        vida = Math.max(0, vida - danoReal);
        return danoReal;
    }

    /** Recupera vida, sem passar da vida máxima. */
    public void curar(int quantidade) {
        vida = Math.min(vidaMaxima, vida + quantidade);
    }

    /** Aumenta o ataque (usado por equipamentos). */
    public void aumentarAtaque(int valor) {
        ataque += valor;
    }

    /** Aumenta a defesa (usado por equipamentos). */
    public void aumentarDefesa(int valor) {
        defesa += valor;
    }
}
