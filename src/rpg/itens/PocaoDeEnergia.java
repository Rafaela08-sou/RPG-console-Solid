package rpg.itens;

import rpg.personagens.Personagem;

/**
 * Classe PocaoDeEnergia
 * ---------------------
 * Segundo tipo de item consumível: devolve energia ao personagem, permitindo
 * usar mais habilidades especiais.
 *
 * Ela existe também para DEMONSTRAR o princípio Aberto/Fechado: este item foi
 * adicionado ao jogo sem que fosse preciso mexer em Inventario, Combate ou
 * Personagem. Bastou criar a classe e implementar a interface Item.
 */
public class PocaoDeEnergia implements Item {

    private final int energia;

    /** @param energia quantidade de energia que a poção recupera. */
    public PocaoDeEnergia(int energia) {
        if (energia <= 0) {
            throw new IllegalArgumentException("A energia precisa ser positiva.");
        }
        this.energia = energia;
    }

    @Override
    public String getNome() {
        return "Poção de Energia";
    }

    @Override
    public String getDescricao() {
        return "Recupera " + energia + " de energia";
    }

    @Override
    public String usar(Personagem dono) {
        int antes = dono.getEnergia();
        dono.recuperarEnergia(energia);
        int recuperado = dono.getEnergia() - antes;
        return dono.getNome() + " bebe a poção e recupera " + recuperado + " de energia.";
    }
}
