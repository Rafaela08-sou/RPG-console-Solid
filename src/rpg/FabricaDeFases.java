package rpg;

import java.util.List;

import rpg.habilidades.HabilidadeDeDano;
import rpg.itens.Equipamento;
import rpg.itens.PocaoDeCura;
import rpg.itens.PocaoDeEnergia;
import rpg.personagens.Inimigo;

/**
 * Classe FabricaDeFases
 * ---------------------
 * Monta a campanha: a lista de fases (inimigo + recompensas) em ordem de
 * dificuldade. É aqui que ficam TODOS os números de balanceamento dos
 * inimigos. Se o jogo ficar fácil ou difícil demais, é neste arquivo que você
 * mexe.
 *
 * Cada chamada a criarFases() cria inimigos NOVOS (com vida cheia). Isso é
 * importante porque um Inimigo derrotado fica com vida 0 e não pode ser
 * reaproveitado em uma nova partida.
 *
 * SOLID aplicado aqui:
 *  - S: só sabe montar as fases.
 *  - D: entrega as habilidades ao Inimigo pelo construtor (injeção de
 *    dependência); o Inimigo não as cria.
 *  - O: para criar uma fase nova, acrescenta-se um item na lista; nenhuma
 *    outra classe precisa mudar.
 */
public final class FabricaDeFases {

    // Classe utilitária: não deve ser instanciada.
    private FabricaDeFases() {
    }

    /** @return a campanha completa: Goblin -> Orc -> Dragão (chefe). */
    public static List<Fase> criarFases() {
        // Fase 1: inimigo fraco, para o jogador aprender os controles.
        Fase goblin = new Fase(
                new Inimigo("Goblin", 55, 10, 2,
                        new HabilidadeDeDano("Facada", 0, 1.0, 1)),
                List.of(new Equipamento("Espada Afiada", 4, 0),
                        new PocaoDeCura(40)));

        // Fase 2: inimigo médio, bate mais forte.
        Fase orc = new Fase(
                new Inimigo("Orc", 85, 15, 4,
                        new HabilidadeDeDano("Machadada", 0, 1.2, 1)),
                List.of(new Equipamento("Armadura de Couro", 0, 3),
                        new PocaoDeCura(40),
                        new PocaoDeEnergia(10)));

        // Fase 3: o CHEFE. Muita vida e golpe forte. Última fase, sem recompensas.
        Fase dragao = new Fase(
                new Inimigo("Dragão Ancião (CHEFE)", 170, 21, 6,
                        new HabilidadeDeDano("Sopro de Fogo", 0, 1.6, 1)),
                List.of());

        return List.of(goblin, orc, dragao);
    }
}
