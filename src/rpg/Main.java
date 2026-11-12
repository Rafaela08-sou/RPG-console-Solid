package rpg;

import rpg.ui.ConsoleUI;
import rpg.ui.InterfaceJogo;

/**
 * Classe Main
 * -----------
 * PONTO DE ENTRADA do programa (o método main é onde o Java começa a executar).
 *
 * Esta classe é a "RAIZ DE COMPOSIÇÃO": é o único lugar onde escolhemos as
 * implementações concretas e as ligamos umas às outras:
 *     ConsoleUI (a interface do terminal) + fases criadas pela fábrica -> Jogo
 *
 * Todo o resto do sistema só conhece abstrações (InterfaceJogo, Oponente,
 * Item, Habilidade). Para trocar o console por outra interface, só esta linha
 * mudaria.
 *
 * SOLID aplicado aqui:
 *  - D (Inversão de Dependência): é aqui que as dependências são "injetadas".
 *  - S: só monta e dispara o jogo; não tem regra de jogo nenhuma.
 */
public class Main {

    public static void main(String[] args) {
        // 1) Escolhe como o jogo conversa com o jogador (terminal).
        InterfaceJogo ui = new ConsoleUI();

        // 2) Monta o jogo entregando a interface e a campanha.
        Jogo jogo = new Jogo(ui, FabricaDeFases.criarFases());

        // 3) Executa. Se a entrada do teclado acabar (ex.: Ctrl+D), encerra
        //    com uma mensagem amigável em vez de mostrar um erro feio.
        try {
            jogo.iniciar();
        } catch (IllegalStateException e) {
            ui.mostrar("\nJogo encerrado: " + e.getMessage());
        }
    }
}
