package rpg.combate;

/**
 * Interface Atacante
 * ------------------
 * Representa tudo aquilo que PODE ATACAR no jogo (heróis e inimigos).
 *
 * Ela é propositalmente pequena: só tem o que é necessário para atacar
 * (um nome para mostrar nas mensagens e o valor de ataque para calcular o dano).
 *
 * SOLID aplicado aqui:
 *  - I (Segregação de Interfaces): em vez de uma interface gigante, separamos o
 *    papel de "atacar" (Atacante) do papel de "receber dano" (Alvo).
 *  - D (Inversão de Dependência): as habilidades dependem desta abstração, e não
 *    das classes concretas Guerreiro, Mago, Inimigo etc.
 */
public interface Atacante {

    /** @return o nome de quem ataca (usado nas mensagens de combate). */
    String getNome();

    /** @return o valor de ataque atual (base para o cálculo do dano). */
    int getAtaque();
}
