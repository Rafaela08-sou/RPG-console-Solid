package rpg.personagens;

import rpg.habilidades.HabilidadeDeDano;

/**
 * Classe Arqueiro
 * ---------------
 * Herói equilibrado, com habilidade de golpes múltiplos.
 * Habilidade especial: "Tiro Duplo" (2 golpes de 1,2x, custa 5 de energia).
 * Como cada golpe sofre o desconto da defesa separadamente, o Tiro Duplo é
 * ótimo contra inimigos de defesa baixa.
 */
public class Arqueiro extends Personagem {

    public Arqueiro(String nome) {
        // vida 95, ataque 17, defesa 5 e o Tiro Duplo
        super(nome, 95, 17, 5, new HabilidadeDeDano("Tiro Duplo", 5, 1.2, 2));
    }

    @Override
    public String getClasse() {
        return "Arqueiro";
    }
}
