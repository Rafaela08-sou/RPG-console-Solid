package rpg.personagens;

import rpg.habilidades.HabilidadeDeDano;

/**
 * Classe Mago
 * -----------
 * Herói frágil, porém com o maior ataque do jogo ("canhão de vidro").
 * Habilidade especial: "Bola de Fogo" (dano 2,2x, custa 8 de energia).
 */
public class Mago extends Personagem {

    public Mago(String nome) {
        // vida 80, ataque 24, defesa 3 e a Bola de Fogo
        super(nome, 80, 24, 3, new HabilidadeDeDano("Bola de Fogo", 8, 2.2, 1));
    }

    @Override
    public String getClasse() {
        return "Mago";
    }
}
