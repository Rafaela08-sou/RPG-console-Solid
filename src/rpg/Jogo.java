package rpg;

import java.util.List;

import rpg.combate.Combate;
import rpg.itens.Item;
import rpg.itens.PocaoDeCura;
import rpg.itens.PocaoDeEnergia;
import rpg.personagens.FabricaDePersonagens;
import rpg.personagens.Personagem;
import rpg.ui.InterfaceJogo;

/**
 * Classe Jogo
 * -----------
 * Conduz a PARTIDA inteira, do começo ao fim:
 *   1) pergunta o nome e a classe do herói e o cria;
 *   2) entrega os itens iniciais;
 *   3) percorre as fases, uma batalha de cada vez;
 *   4) após cada vitória: dá as recompensas e deixa o herói descansar um pouco;
 *   5) termina com vitória (todas as fases vencidas) ou game over.
 *
 * SOLID aplicado aqui:
 *  - S: só conduz a sequência da partida. Os turnos são do Combate, o texto é
 *    da InterfaceJogo, a criação dos heróis é da FabricaDePersonagens.
 *  - D: depende de InterfaceJogo (abstração) e recebe a ui e a lista de fases
 *    pelo CONSTRUTOR (injeção de dependência).
 *  - O: não conhece nenhum inimigo específico; percorre qualquer lista de
 *    fases recebida.
 */
public class Jogo {

    // Regras de descanso entre batalhas (facilitam ajustar o equilíbrio).
    private static final double PERCENTUAL_CURA_DESCANSO = 0.20; // 20% da vida máxima
    private static final int ENERGIA_DESCANSO = 6;

    // Itens que todo herói recebe no início.
    private static final int CURA_INICIAL = 40;
    private static final int ENERGIA_INICIAL = 10;

    private final InterfaceJogo ui;
    private final List<Fase> fases;

    /**
     * @param ui    como conversar com o jogador
     * @param fases a campanha (lista de fases)
     */
    public Jogo(InterfaceJogo ui, List<Fase> fases) {
        this.ui = ui;
        this.fases = fases;
    }

    /** Roda uma partida completa. */
    public void iniciar() {
        ui.mostrar("==================================================");
        ui.mostrar("               RPG EM CONSOLE");
        ui.mostrar("==================================================");

        Personagem jogador = criarPersonagem();
        entregarItensIniciais(jogador);

        for (int i = 0; i < fases.size(); i++) {
            Fase fase = fases.get(i);
            ui.mostrar("\n>>> FASE " + (i + 1) + " de " + fases.size() + " <<<");

            boolean venceu = new Combate(jogador, fase.inimigo(), ui).executar();
            if (!venceu) {
                ui.mostrar("\nVocê foi derrotado... GAME OVER.");
                return;
            }

            boolean ultimaFase = (i == fases.size() - 1);
            if (!ultimaFase) {
                receberRecompensas(jogador, fase);
                descansar(jogador);
            }
        }

        ui.mostrar("\n==================================================");
        ui.mostrar("PARABÉNS! Você derrotou o chefe e zerou o jogo!");
        ui.mostrar("==================================================");
    }

    /** Pergunta nome e classe e pede à fábrica que crie o herói. */
    private Personagem criarPersonagem() {
        String nome = ui.lerTexto("Nome do herói");

        // O menu é montado a partir da fábrica: classes novas aparecem sozinhas.
        List<String> classes = FabricaDePersonagens.classesDisponiveis();
        StringBuilder menu = new StringBuilder();
        for (int i = 0; i < classes.size(); i++) {
            menu.append(i + 1).append(") ").append(classes.get(i)).append("   ");
        }
        ui.mostrar(menu.toString().trim());

        int opcao = ui.lerOpcao("Classe", 1, classes.size());
        return FabricaDePersonagens.criar(classes.get(opcao - 1), nome);
    }

    /** Todo herói começa com 2 poções de cura e 1 de energia. */
    private void entregarItensIniciais(Personagem jogador) {
        jogador.getInventario().adicionar(new PocaoDeCura(CURA_INICIAL));
        jogador.getInventario().adicionar(new PocaoDeCura(CURA_INICIAL));
        jogador.getInventario().adicionar(new PocaoDeEnergia(ENERGIA_INICIAL));
    }

    /** Coloca na mochila os itens ganhos ao vencer a fase. */
    private void receberRecompensas(Personagem jogador, Fase fase) {
        ui.mostrar("\nVitória! Você encontrou:");
        for (Item item : fase.recompensas()) {
            jogador.getInventario().adicionar(item);
            ui.mostrar("  + " + item.getNome() + " (" + item.getDescricao() + ")");
        }
    }

    /** Entre batalhas o herói recupera parte da vida e um pouco de energia. */
    private void descansar(Personagem jogador) {
        int cura = (int) (jogador.getVidaMaxima() * PERCENTUAL_CURA_DESCANSO);
        jogador.curar(cura);
        jogador.recuperarEnergia(ENERGIA_DESCANSO);
        ui.mostrar(String.format("Você descansa um pouco: HP %d/%d, Energia %d/%d.",
                jogador.getVida(), jogador.getVidaMaxima(),
                jogador.getEnergia(), jogador.getEnergiaMaxima()));
    }
}
