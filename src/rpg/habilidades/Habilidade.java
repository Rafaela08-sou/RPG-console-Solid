package rpg.habilidades;

import rpg.combate.Alvo;
import rpg.combate.Atacante;

/**
 * Interface Habilidade
 * --------------------
 * Representa qualquer ação especial que um combatente pode executar contra um
 * alvo (um golpe, um feitiço, um tiro...).
 *
 * Cada habilidade do jogo é um OBJETO que implementa esta interface. Assim, o
 * combate não precisa saber "qual" habilidade está sendo usada: ele apenas
 * chama usar() e cada habilidade faz a sua parte (POLIMORFISMO).
 *
 * SOLID aplicado aqui:
 *  - O (Aberto/Fechado): para criar uma habilidade nova, basta criar uma nova
 *    classe que implemente esta interface. Nada que já existe precisa mudar.
 *  - I (Segregação de Interfaces): a habilidade só enxerga um Atacante e um
 *    Alvo (o mínimo necessário), e não o personagem inteiro com inventário,
 *    energia etc.
 *  - D (Inversão de Dependência): quem usa habilidades depende desta abstração.
 */
public interface Habilidade {

    /** @return o nome da habilidade (mostrado no menu). */
    String getNome();

    /** @return o custo de energia para usar a habilidade (0 = grátis). */
    int getCusto();

    /**
     * Executa a habilidade.
     *
     * @param usuario quem está usando (fornece o valor de ataque)
     * @param alvo    quem vai sofrer o efeito
     * @return a mensagem que descreve o resultado, para ser exibida na tela
     */
    String usar(Atacante usuario, Alvo alvo);
}
