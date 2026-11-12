package rpg.combate;

/**
 * Interface Alvo
 * --------------
 * Representa tudo aquilo que PODE RECEBER DANO (heróis e inimigos).
 *
 * É o outro "lado" do ataque: o Atacante entrega um dano bruto e o Alvo decide
 * quanto desse dano realmente é aplicado (por exemplo, descontando a defesa).
 *
 * SOLID aplicado aqui:
 *  - I (Segregação de Interfaces): só tem métodos relacionados a receber dano.
 *  - D (Inversão de Dependência): quem causa dano (Habilidade) conhece apenas
 *    esta interface, nunca uma classe concreta.
 */
public interface Alvo {

    /** @return o nome do alvo. */
    String getNome();

    /** @return a vida atual (0 significa derrotado). */
    int getVida();

    /** @return a vida máxima (usada para mostrar "HP 30/50" na tela). */
    int getVidaMaxima();

    /** @return true se ainda tem vida, false se foi derrotado. */
    boolean estaVivo();

    /**
     * Recebe um ataque.
     *
     * @param danoBruto dano antes de descontar a defesa
     * @return o dano que realmente foi aplicado
     */
    int receberDano(int danoBruto);
}
