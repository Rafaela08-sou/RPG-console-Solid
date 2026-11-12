package rpg.itens;

import rpg.personagens.Personagem;

/**
 * Classe PocaoDeCura
 * ------------------
 * Item consumível que devolve uma quantidade fixa de vida ao personagem.
 * Ela nunca ultrapassa a vida máxima (quem garante isso é o método curar()
 * do próprio personagem).
 *
 * SOLID aplicado aqui:
 *  - S: só sabe "curar vida".
 *  - O: extensão de Item, sem alterar nada existente.
 *  - L: pode ser usada em qualquer lugar que espere um Item.
 */
public class PocaoDeCura implements Item {

    private final int cura;

    /** @param cura quantidade de vida que a poção recupera. */
    public PocaoDeCura(int cura) {
        if (cura <= 0) {
            throw new IllegalArgumentException("A cura precisa ser positiva.");
        }
        this.cura = cura;
    }

    @Override
    public String getNome() {
        return "Poção de Cura";
    }

    @Override
    public String getDescricao() {
        return "Recupera " + cura + " de vida";
    }

    @Override
    public String usar(Personagem dono) {
        // Guarda a vida antes para informar quanto foi realmente recuperado
        // (se o herói estiver quase cheio, a cura real é menor que a da poção).
        int antes = dono.getVida();
        dono.curar(cura);
        int recuperado = dono.getVida() - antes;
        return dono.getNome() + " bebe a poção e recupera " + recuperado + " de vida.";
    }
}
