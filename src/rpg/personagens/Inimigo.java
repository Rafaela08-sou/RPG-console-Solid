package rpg.personagens;

import rpg.combate.Alvo;
import rpg.combate.Oponente;
import rpg.habilidades.Habilidade;

/**
 * Classe Inimigo
 * --------------
 * Adversário controlado pelo computador. Goblin, Orc e Dragão são todos
 * "Inimigo": o que muda entre eles são só os números (vida, ataque, defesa) e a
 * habilidade, definidos em FabricaDeFases. Não precisamos de uma classe por
 * monstro.
 *
 * Herda de Combatente (nome, vida, ataque, defesa, receberDano) e implementa
 * Oponente (sabe agir no próprio turno).
 *
 * Inimigo NÃO tem energia, inventário nem lista de habilidades. Isso é
 * Segregação de Interfaces em ação: ele só implementa o que precisa (Atacante,
 * Alvo e Oponente), sem ser forçado a ter coisas que só o herói usa.
 *
 * SOLID aplicado aqui:
 *  - D (Inversão de Dependência): a habilidade é recebida PRONTA pelo
 *    construtor (injeção de dependência). O Inimigo não cria uma
 *    HabilidadeDeDano por conta própria; ele só conhece a interface Habilidade.
 *  - S: só sabe ser um adversário (agir e receber dano).
 */
public class Inimigo extends Combatente implements Oponente {

    private final Habilidade habilidade;

    /**
     * @param nome       nome do inimigo
     * @param vida       vida inicial
     * @param ataque     ataque
     * @param defesa     defesa
     * @param habilidade golpe que ele usa em todo turno (injetado de fora)
     */
    public Inimigo(String nome, int vida, int ataque, int defesa, Habilidade habilidade) {
        super(nome, vida, ataque, defesa);
        if (habilidade == null) {
            throw new IllegalArgumentException("O inimigo precisa de uma habilidade.");
        }
        this.habilidade = habilidade;
    }

    /** No seu turno, o inimigo simplesmente usa a sua habilidade no alvo. */
    @Override
    public String agir(Alvo alvo) {
        return habilidade.usar(this, alvo);
    }
}
