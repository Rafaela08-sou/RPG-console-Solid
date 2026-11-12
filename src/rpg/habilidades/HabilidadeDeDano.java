package rpg.habilidades;

import rpg.combate.Alvo;
import rpg.combate.Atacante;

/**
 * Classe HabilidadeDeDano
 * -----------------------
 * Implementação de Habilidade que causa dano ao alvo. Ela é CONFIGURÁVEL:
 * com os mesmos parâmetros do construtor criamos habilidades bem diferentes.
 *
 *   new HabilidadeDeDano("Ataque básico",  0, 1.0, 1)  -> golpe simples, grátis
 *   new HabilidadeDeDano("Golpe Poderoso", 6, 2.0, 1)  -> dano dobrado, custa 6
 *   new HabilidadeDeDano("Tiro Duplo",     5, 1.2, 2)  -> dois golpes de 1,2x
 *
 * Fórmula do dano bruto de cada golpe:  ataque do usuário x multiplicador
 * (o Alvo ainda desconta a própria defesa dentro de receberDano()).
 *
 * SOLID aplicado aqui:
 *  - S: a única responsabilidade desta classe é calcular e aplicar dano.
 *  - O: ela é uma EXTENSÃO da interface Habilidade; nenhum código existente foi
 *    alterado para ela existir.
 *  - L: pode ser usada em qualquer lugar que espere uma Habilidade.
 */
public class HabilidadeDeDano implements Habilidade {

    // Atributos "final": são definidos uma vez no construtor e nunca mudam
    // (imutabilidade = menos bugs). "private" = ENCAPSULAMENTO.
    private final String nome;
    private final int custo;
    private final double multiplicador;
    private final int golpes;

    /**
     * @param nome          nome exibido no menu
     * @param custo         energia gasta ao usar (0 = grátis)
     * @param multiplicador quanto do ataque do usuário vira dano (1.0 = 100%)
     * @param golpes        quantas vezes o golpe acerta por uso
     */
    public HabilidadeDeDano(String nome, int custo, double multiplicador, int golpes) {
        // Validação: impede criar uma habilidade com valores sem sentido.
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("A habilidade precisa de um nome.");
        }
        if (custo < 0 || multiplicador <= 0 || golpes < 1) {
            throw new IllegalArgumentException("Valores inválidos para a habilidade " + nome);
        }
        this.nome = nome;
        this.custo = custo;
        this.multiplicador = multiplicador;
        this.golpes = golpes;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int getCusto() {
        return custo;
    }

    @Override
    public String usar(Atacante usuario, Alvo alvo) {
        // Passo 1: calcula o dano bruto de UM golpe (ataque x multiplicador).
        int danoBruto = (int) (usuario.getAtaque() * multiplicador);

        // Passo 2: repete o golpe "golpes" vezes e soma o dano realmente aplicado.
        int danoTotal = 0;
        for (int i = 0; i < golpes; i++) {
            danoTotal += alvo.receberDano(danoBruto);
        }

        // Passo 3: devolve o texto para a interface exibir. Esta classe NÃO
        // imprime nada sozinha (quem imprime é a camada de interface).
        return String.format("%s usa %s em %s e causa %d de dano!",
                usuario.getNome(), nome, alvo.getNome(), danoTotal);
    }
}
