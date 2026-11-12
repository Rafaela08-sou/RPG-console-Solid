package rpg.combate;

/**
 * Interface Oponente
 * ------------------
 * Representa o adversário do herói em uma batalha. Um Oponente:
 *  - ataca (é um Atacante);
 *  - recebe dano (é um Alvo);
 *  - e sabe AGIR sozinho no seu turno (método agir), pois quem decide o que ele
 *    faz é ele mesmo, e não o jogador.
 *
 * Por que esta interface existe?
 *  A classe Combate precisa de "alguém para lutar contra o herói". Se ela
 *  dependesse da classe concreta Inimigo, ficaria amarrada a ela. Dependendo
 *  de Oponente, o Combate funciona com qualquer tipo de adversário que venha
 *  a ser criado no futuro (um chefe com comportamento especial, por exemplo).
 *
 * SOLID aplicado aqui:
 *  - D (Inversão de Dependência): Combate depende desta abstração.
 *  - O (Aberto/Fechado): novos tipos de oponentes podem ser criados sem alterar
 *    a classe Combate.
 */
public interface Oponente extends Atacante, Alvo {

    /**
     * Executa o turno do oponente contra o alvo informado.
     *
     * @param alvo quem será atacado (normalmente o herói)
     * @return a mensagem que descreve o que aconteceu
     */
    String agir(Alvo alvo);
}
