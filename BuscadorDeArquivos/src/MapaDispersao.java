public class MapaDispersao<T> implements java.io.Serializable {

    private ListaEncadeada<NoMapa<T>>[] tabela;
    private int tamanho;
    private int quantidade;

    public MapaDispersao(int tamanho) {
        this.tamanho = tamanho;
        this.quantidade = 0;
        this.tabela = new ListaEncadeada[tamanho];

        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new ListaEncadeada<>();
        }
    }

    private int calcularHash(String chave) {
        return Math.abs(chave.hashCode() % tamanho);
    }

    public void inserir(String chave, T valor) {
        int indice = calcularHash(chave);
        ListaEncadeada<NoMapa<T>> lista = tabela[indice];

        NoMapa<T> atual = lista.getPrimeiro();
        while (atual != null) {
            if (atual.getChave()) {
                atual.setValor(valor);
                return;
            }
            atual = atual.getProximo();
        }

        lista.inserir(new NoMapa<>(chave, valor));
        quantidade++;
    }

    public T buscar(String chave) {
        int indice = calcularHash(chave);
        ListaEncadeada<NoMapa<T>> lista = tabela[indice];

        NoMapa<T> atual = lista.getPrimeiro();
        while (atual != null) {
            if (atual.getChave().equals(chave)) {
                return atual.getValor();
            }
            atual = atual.getProximo();
        }

        return null;
    }

    public void remover(String chave) {
        int indice = calcularHash(chave);
        ListaEncadeada<NoMapa<T>> lista = tabela[indice];

        NoMapa<T> atual = lista.getPrimeiro();
        while (atual != null) {
            if (atual.getChave().equals(chave)) {
                lista.remover(atual);
                quantidade--;
                return;
            }
            atual = atual.getProximo();
        }
    }

    public double calcularFatorCarga() {
        return (double) quantidade / tamanho;
    }
}