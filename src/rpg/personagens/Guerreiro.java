package rpg.personagens;

import rpg.habilidades.HabilidadeDeDano;

/**
 * Classe Guerreiro
 * ----------------
 * Herói resistente: muita vida e defesa, ataque mediano.
 * Habilidade especial: "Golpe Poderoso" (dano dobrado, custa 6 de energia).
 *
 * Repare como a subclasse é pequena: tudo o que é comum já está em Personagem
 * e Combatente. Aqui só dizemos o que torna o Guerreiro diferente
 * (HERANÇA + reuso de código).
 */
public class Guerreiro extends Personagem {

    public Guerreiro(String nome) {
        // super(...) chama o construtor de Personagem: vida 120, ataque 18,
        // defesa 8 e a habilidade especial do Guerreiro.
        super(nome, 120, 18, 8, new HabilidadeDeDano("Golpe Poderoso", 6, 2.0, 1));
    }

    @Override
    public String getClasse() {
        return "Guerreiro";
    }
}
