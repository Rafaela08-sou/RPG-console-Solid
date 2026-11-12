package rpg.itens;

import rpg.personagens.Personagem;

/**
 * Interface Item
 * --------------
 * Representa qualquer objeto que o herói pode guardar no inventário e USAR
 * (poções, equipamentos, e o que mais você quiser criar).
 *
 * O inventário e o combate só conhecem esta interface. Quando o jogador escolhe
 * um item, o jogo chama item.usar(...) sem saber se é uma poção ou uma espada:
 * cada item sabe o que fazer (POLIMORFISMO).
 *
 * SOLID aplicado aqui:
 *  - O (Aberto/Fechado): criar um item novo = criar uma nova classe que
 *    implementa Item. Inventario e Combate não mudam.
 *  - I (Segregação de Interfaces): interface enxuta, com apenas 3 métodos.
 *  - D (Inversão de Dependência): Inventario guarda "Item" (abstração), nunca
 *    PocaoDeCura ou Equipamento diretamente.
 */
public interface Item {

    /** @return o nome do item (mostrado no menu do inventário). */
    String getNome();

    /** @return uma descrição curta do efeito (mostrada no menu). */
    String getDescricao();

    /**
     * Usa o item em um personagem.
     *
     * @param dono o personagem que vai receber o efeito
     * @return mensagem que descreve o que aconteceu
     */
    String usar(Personagem dono);
}
