import java.util.LinkedList;

public class MapaDispersao<T> {

    private LinkedList<NoMapa<T>>[] tabela;
    private int tamanho;
    private int quantidade;

    public MapaDispersao(int tamanho) {
        this.tamanho = tamanho;
        this.quantidade = 0;

        tabela = new LinkedList[tamanho];

        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new LinkedList<>();
        }
    }

    private int calcularHash(int chave) {
        return chave % tamanho;
    }

    public void inserir(int chave, T valor) {
        int indice = calcularHash(chave);
        LinkedList<NoMapa<T>> lista = tabela[indice];

        for (NoMapa<T> no : lista) {
            if (no.getChave() == chave) {
                no.setValor(valor);
                return;
            }
        }

        lista.add(new NoMapa<>(chave, valor));
        quantidade++;
    }

    public void remover(int chave) {
        int indice = calcularHash(chave);
        LinkedList<NoMapa<T>> lista = tabela[indice];

        NoMapa<T> temp = new NoMapa<>(chave, null);

        if (lista.remove(temp)) {
            quantidade--;
        }
    }

    public T buscar(int chave) {
        int indice = calcularHash(chave);
        LinkedList<NoMapa<T>> lista = tabela[indice];

        for (NoMapa<T> no : lista) {
            if (no.getChave() == chave) {
                return no.getValor();
            }
        }

        return null;
    }

    public double calcularFatorCarga() {
        return (double) quantidade / tamanho;
    }
}