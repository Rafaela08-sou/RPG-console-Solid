package rpg.ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Classe ConsoleUI
 * ----------------
 * Implementação de InterfaceJogo para o TERMINAL: escreve com System.out e lê
 * com Scanner. É a ÚNICA classe do projeto que conhece Scanner e System.out.
 *
 * Também faz a VALIDAÇÃO das entradas: se o jogador digitar uma letra onde se
 * espera um número, ou um número fora do intervalo, a pergunta é repetida em
 * vez de o programa quebrar.
 *
 * SOLID aplicado aqui:
 *  - S (Responsabilidade Única): só cuida de entrada e saída de texto. Não
 *    sabe nada de regras do jogo.
 *  - L: pode substituir qualquer InterfaceJogo sem quebrar o resto do jogo.
 */
public class ConsoleUI implements InterfaceJogo {

    // Scanner lê o que o jogador digita no teclado (UTF-8 para aceitar acentos).
    private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

    // Saída em UTF-8 para os acentos e símbolos aparecerem corretamente.
    private final PrintStream saida = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    @Override
    public void mostrar(String mensagem) {
        saida.println(mensagem);
    }

    @Override
    public String lerTexto(String pergunta) {
        while (true) {
            String linha = perguntar(pergunta).trim();
            if (!linha.isEmpty()) {
                return linha;
            }
            saida.println("O texto não pode ficar vazio.");
        }
    }

    @Override
    public int lerOpcao(String pergunta, int min, int max) {
        while (true) {
            String linha = perguntar(pergunta).trim();
            try {
                int valor = Integer.parseInt(linha);
                if (valor >= min && valor <= max) {
                    return valor;
                }
            } catch (NumberFormatException ignorada) {
                // Não era um número: cai na mensagem de erro abaixo.
            }
            saida.println("Opção inválida. Digite um número de " + min + " a " + max + ".");
        }
    }

    /**
     * Mostra a pergunta, espera o jogador digitar e devolve a linha digitada.
     * Se a entrada acabar (ex.: Ctrl+D), encerra com uma exceção clara.
     */
    private String perguntar(String pergunta) {
        saida.print(pergunta + " > ");
        saida.flush(); // garante que o texto apareça antes de esperar a digitação
        if (!scanner.hasNextLine()) {
            throw new IllegalStateException("Entrada encerrada.");
        }
        return scanner.nextLine();
    }
}
