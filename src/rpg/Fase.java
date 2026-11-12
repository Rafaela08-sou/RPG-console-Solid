package rpg;

import java.util.List;

import rpg.combate.Oponente;
import rpg.itens.Item;

/**
 * Record Fase
 * -----------
 * Descreve UMA etapa da aventura: contra quem o herói luta e o que ganha ao
 * vencer.
 *
 * "record" é um recurso do Java para criar classes simples que só carregam
 * dados (como uma ficha). O Java gera sozinho o construtor, os getters
 * (inimigo() e recompensas()), equals, hashCode e toString.
 *
 * Por que existe? Para a classe Jogo ficar GENÉRICA: ela só percorre uma lista
 * de fases, sem saber quais são os inimigos nem as recompensas. Para criar uma
 * fase nova não é preciso mexer em Jogo (princípio Aberto/Fechado).
 *
 * @param inimigo     o adversário da fase
 * @param recompensas itens que o herói ganha ao vencer a fase
 */
public record Fase(Oponente inimigo, List<Item> recompensas) {
}
