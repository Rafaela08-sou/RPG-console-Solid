package rpg.ui;

/**
 * Interface InterfaceJogo
 * -----------------------
 * Define COMO o jogo conversa com o jogador: mostrar texto, ler um texto e ler
 * uma opção numérica. Ela diz O QUE precisa ser feito, mas não COMO.
 *
 * Hoje a única implementação é ConsoleUI (terminal). Amanhã poderia existir uma
 * interface gráfica ou uma interface "falsa" para testes automáticos, e o
 * resto do jogo (Combate, Jogo) não precisaria mudar nada.
 *
 * SOLID aplicado aqui:
 *  - D (Inversão de Dependência): Combate e Jogo dependem DESTA abstração, e
 *    não de Scanner/System.out. É o exemplo mais claro do princípio no projeto.
 *  - I (Segregação de Interfaces): apenas 3 métodos, todos de entrada/saída.
 */
public interface InterfaceJogo {

    /** Exibe uma mensagem ao jogador. */
    void mostrar(String mensagem);

    /**
     * Pergunta um texto livre (ex.: o nome do herói). Não aceita texto vazio.
     */
    String lerTexto(String pergunta);

    /**
     * Pergunta uma opção numérica e só devolve quando o jogador digitar um
     * número válido dentro do intervalo.
     *
     * @param pergunta texto exibido antes do campo de resposta
     * @param min      menor valor aceito
     * @param max      maior valor aceito
     */
    int lerOpcao(String pergunta, int min, int max);
}
