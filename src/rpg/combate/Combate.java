package rpg.combate;

import java.util.List;

import rpg.habilidades.Habilidade;
import rpg.itens.Item;
import rpg.personagens.Personagem;
import rpg.ui.InterfaceJogo;

/**
 * Classe Combate
 * --------------
 * Controla UMA batalha por turnos entre o herói e um oponente.
 *
 * Ciclo de cada rodada:
 *   1) mostra o status dos dois lados;
 *   2) o jogador escolhe uma ação (habilidade ou item);
 *   3) se o oponente ainda estiver vivo, ele ataca;
 *   4) o herói recupera um pouco de energia;
 *   5) repete até alguém ficar sem vida.
 *
 * SOLID aplicado aqui:
 *  - S (Responsabilidade Única): só controla o FLUXO dos turnos. Não calcula
 *    dano (isso é do Combatente), não decide o que o inimigo faz (isso é do
 *    Oponente), não lê teclado nem usa System.out (isso é da InterfaceJogo).
 *  - D (Inversão de Dependência): depende das abstrações InterfaceJogo e
 *    Oponente, recebidas pelo CONSTRUTOR (injeção de dependência). Do herói
 *    usa a classe abstrata Personagem, nunca Guerreiro/Mago/Arqueiro.
 *  - L (Liskov): funciona com qualquer subclasse de Personagem e com qualquer
 *    implementação de Oponente.
 *  - O (Aberto/Fechado): habilidades e itens novos funcionam aqui sem editar
 *    esta classe, pois ela só chama habilidade.usar() e item.usar().
 */
public class Combate {

    /** Energia que o herói recupera ao final de cada rodada. */
    private static final int ENERGIA_POR_TURNO = 4;

    private final Personagem jogador;
    private final Oponente oponente;
    private final InterfaceJogo ui;

    /**
     * @param jogador  o herói
     * @param oponente o adversário desta batalha
     * @param ui       como conversar com o jogador (console ou outra)
     */
    public Combate(Personagem jogador, Oponente oponente, InterfaceJogo ui) {
        this.jogador = jogador;
        this.oponente = oponente;
        this.ui = ui;
    }

    /**
     * Roda a batalha inteira.
     *
     * @return true se o herói venceu; false se foi derrotado
     */
    public boolean executar() {
        ui.mostrar("\n==================================================");
        ui.mostrar("  BATALHA: " + jogador.getNome() + " vs " + oponente.getNome());
        ui.mostrar("==================================================");

        // Repete enquanto os DOIS lados estiverem vivos.
        while (jogador.estaVivo() && oponente.estaVivo()) {
            mostrarStatus();
            turnoDoJogador();

            // Se o oponente morreu no golpe do jogador, ele não ataca.
            if (!oponente.estaVivo()) {
                break;
            }

            // Turno do oponente (POLIMORFISMO: Combate não sabe qual golpe é).
            ui.mostrar(oponente.agir(jogador));
            jogador.recuperarEnergia(ENERGIA_POR_TURNO);
        }

        boolean venceu = jogador.estaVivo();
        if (venceu) {
            ui.mostrar("\n" + oponente.getNome() + " foi derrotado!");
        }
        return venceu;
    }

    /** Mostra vida e energia do herói e vida do oponente. */
    private void mostrarStatus() {
        ui.mostrar("\n--------------------------------------------------");
        ui.mostrar(String.format("%s [%s]  HP %s %d/%d  |  Energia %d/%d",
                jogador.getNome(), jogador.getClasse(),
                barra(jogador.getVida(), jogador.getVidaMaxima()),
                jogador.getVida(), jogador.getVidaMaxima(),
                jogador.getEnergia(), jogador.getEnergiaMaxima()));
        ui.mostrar(String.format("%s  HP %s %d/%d",
                oponente.getNome(),
                barra(oponente.getVida(), oponente.getVidaMaxima()),
                oponente.getVida(), oponente.getVidaMaxima()));
        ui.mostrar("--------------------------------------------------");
    }

    /** Desenha uma barra de vida de 10 posições, ex.: [#####-----]. */
    private String barra(int atual, int maximo) {
        int cheias = (int) Math.ceil(10.0 * atual / maximo);
        return "[" + "#".repeat(cheias) + "-".repeat(10 - cheias) + "]";
    }

    /**
     * Pede a ação do jogador e repete o menu até ele realizar uma ação válida.
     * Voltar atrás ("0) Voltar"), faltar energia ou abrir o inventário vazio
     * NÃO gastam o turno.
     */
    private void turnoDoJogador() {
        boolean agiu = false;
        while (!agiu) {
            int opcao = ui.lerOpcao("1) Habilidade   2) Item", 1, 2);
            agiu = (opcao == 1) ? menuHabilidades() : menuItens();
        }
    }

    /**
     * Menu de habilidades.
     *
     * @return true se uma habilidade foi usada (turno gasto); false se o
     *         jogador voltou ou não tinha energia
     */
    private boolean menuHabilidades() {
        List<Habilidade> lista = jogador.getHabilidades();
        for (int i = 0; i < lista.size(); i++) {
            Habilidade h = lista.get(i);
            ui.mostrar("  " + (i + 1) + ") " + h.getNome() + " (custo " + h.getCusto() + ")");
        }
        ui.mostrar("  0) Voltar");

        int opcao = ui.lerOpcao("Habilidade", 0, lista.size());
        if (opcao == 0) {
            return false;
        }

        Habilidade escolhida = lista.get(opcao - 1);
        if (!jogador.gastarEnergia(escolhida.getCusto())) {
            ui.mostrar("Energia insuficiente!");
            return false;
        }
        // POLIMORFISMO: cada habilidade sabe o que fazer.
        ui.mostrar(escolhida.usar(jogador, oponente));
        return true;
    }

    /**
     * Menu de itens.
     *
     * @return true se um item foi usado (turno gasto); false se o jogador
     *         voltou ou o inventário estava vazio
     */
    private boolean menuItens() {
        if (jogador.getInventario().estaVazio()) {
            ui.mostrar("Inventário vazio!");
            return false;
        }

        List<Item> itens = jogador.getInventario().listar();
        for (int i = 0; i < itens.size(); i++) {
            Item item = itens.get(i);
            ui.mostrar("  " + (i + 1) + ") " + item.getNome() + " - " + item.getDescricao());
        }
        ui.mostrar("  0) Voltar");

        int opcao = ui.lerOpcao("Item", 0, itens.size());
        if (opcao == 0) {
            return false;
        }

        // O item sai da mochila e é consumido. POLIMORFISMO: cada item sabe o
        // que fazer ao ser usado.
        Item item = jogador.getInventario().remover(opcao - 1);
        ui.mostrar(item.usar(jogador));
        return true;
    }
}
