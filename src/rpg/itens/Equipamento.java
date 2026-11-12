package rpg.itens;

import rpg.personagens.Personagem;

/**
 * Classe Equipamento
 * ------------------
 * Item que, ao ser equipado, aumenta PERMANENTEMENTE o ataque e/ou a defesa do
 * personagem (ex.: "Espada Afiada" +4 ataque, "Armadura de Couro" +3 defesa).
 *
 * Como cada equipamento é só uma combinação de bônus, uma única classe atende
 * a todos: o que muda é o que passamos no construtor.
 *
 * SOLID aplicado aqui:
 *  - S: só sabe aplicar bônus de atributos.
 *  - O: extensão de Item, sem alterar nada existente.
 */
public class Equipamento implements Item {

    private final String nome;
    private final int bonusAtaque;
    private final int bonusDefesa;

    /**
     * @param nome        nome do equipamento
     * @param bonusAtaque quanto de ataque ele concede (0 se não concede)
     * @param bonusDefesa quanto de defesa ele concede (0 se não concede)
     */
    public Equipamento(String nome, int bonusAtaque, int bonusDefesa) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O equipamento precisa de um nome.");
        }
        if (bonusAtaque < 0 || bonusDefesa < 0) {
            throw new IllegalArgumentException("Os bônus não podem ser negativos.");
        }
        this.nome = nome;
        this.bonusAtaque = bonusAtaque;
        this.bonusDefesa = bonusDefesa;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getDescricao() {
        // Mostra só os bônus que existem, para o texto ficar limpo.
        StringBuilder texto = new StringBuilder();
        if (bonusAtaque > 0) {
            texto.append("+").append(bonusAtaque).append(" ataque");
        }
        if (bonusDefesa > 0) {
            if (texto.length() > 0) {
                texto.append(", ");
            }
            texto.append("+").append(bonusDefesa).append(" defesa");
        }
        return texto.toString();
    }

    @Override
    public String usar(Personagem dono) {
        dono.aumentarAtaque(bonusAtaque);
        dono.aumentarDefesa(bonusDefesa);
        return dono.getNome() + " equipa " + nome + " (" + getDescricao() + ").";
    }
}
