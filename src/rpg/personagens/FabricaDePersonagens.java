package rpg.personagens;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Classe FabricaDePersonagens
 * ---------------------------
 * Centraliza a CRIAÇÃO dos heróis (padrão de projeto "Factory").
 *
 * Ela mantém um REGISTRO (mapa) que liga o nome de cada classe ao jeito de
 * criá-la. Quem usa a fábrica (a classe Jogo) apenas pede "as classes
 * disponíveis" e "crie a classe X", sem nunca escrever "new Guerreiro(...)".
 *
 * COMO ADICIONAR UM NOVO HERÓI (ex.: Ladino):
 *   1) criar a classe Ladino (extends Personagem);
 *   2) acrescentar UMA linha no bloco static abaixo:
 *          CLASSES.put("Ladino", Ladino::new);
 *   O menu de escolha, o Jogo e o Combate se ajustam sozinhos.
 *
 * SOLID aplicado aqui:
 *  - S: só sabe criar personagens.
 *  - O: o código que MONTA o menu e que CRIA o personagem não muda quando
 *    surge uma classe nova; só o registro cresce.
 *
 * Detalhe de Java: "Guerreiro::new" é uma referência ao construtor (equivale a
 * "nome -> new Guerreiro(nome)"). Function<String, Personagem> significa
 * "função que recebe um nome (String) e devolve um Personagem".
 */
public final class FabricaDePersonagens {

    // LinkedHashMap mantém a ordem em que as classes foram registradas,
    // então o menu sempre aparece na mesma ordem.
    private static final Map<String, Function<String, Personagem>> CLASSES =
            new LinkedHashMap<>();

    // Bloco static: executado uma única vez, quando a classe é carregada.
    static {
        CLASSES.put("Guerreiro", Guerreiro::new);
        CLASSES.put("Mago", Mago::new);
        CLASSES.put("Arqueiro", Arqueiro::new);
    }

    // Construtor privado: esta classe só tem métodos estáticos e não deve ser
    // instanciada.
    private FabricaDePersonagens() {
    }

    /** @return os nomes das classes disponíveis, na ordem do registro. */
    public static List<String> classesDisponiveis() {
        return new ArrayList<>(CLASSES.keySet());
    }

    /**
     * Cria um herói.
     *
     * @param classe nome da classe (um dos valores de classesDisponiveis())
     * @param nome   nome do herói
     */
    public static Personagem criar(String classe, String nome) {
        Function<String, Personagem> criador = CLASSES.get(classe);
        if (criador == null) {
            throw new IllegalArgumentException("Classe desconhecida: " + classe);
        }
        return criador.apply(nome);
    }
}
