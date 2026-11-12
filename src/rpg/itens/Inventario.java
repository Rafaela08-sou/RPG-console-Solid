package rpg.itens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe Inventario
 * -----------------
 * A "mochila" do personagem: guarda uma lista de itens.
 *
 * Repare que a lista é de "Item" (a interface), e não de PocaoDeCura ou
 * Equipamento. Por isso o inventário aceita QUALQUER item, inclusive os que
 * ainda nem existem.
 *
 * SOLID aplicado aqui:
 *  - S (Responsabilidade Única): só gerencia a coleção de itens (adicionar,
 *    remover, listar). Não sabe usar itens e não imprime nada.
 *  - D (Inversão de Dependência): depende da abstração Item.
 *
 * ENCAPSULAMENTO: a lista é privada. Quem quiser ver os itens recebe uma
 * visão SOMENTE LEITURA, então ninguém consegue alterar a mochila "pelas
 * costas" (só pelos métodos adicionar e remover).
 */
public class Inventario {

    private final List<Item> itens = new ArrayList<>();

    /** Coloca um item na mochila. */
    public void adicionar(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Não é possível guardar um item nulo.");
        }
        itens.add(item);
    }

    /**
     * Tira um item da mochila e o devolve.
     *
     * @param indice posição do item na lista (começa em 0)
     */
    public Item remover(int indice) {
        return itens.remove(indice);
    }

    /** @return true se não há nenhum item. */
    public boolean estaVazio() {
        return itens.isEmpty();
    }

    /** @return uma visão somente leitura dos itens (não dá para alterar a lista). */
    public List<Item> listar() {
        return Collections.unmodifiableList(itens);
    }
}
