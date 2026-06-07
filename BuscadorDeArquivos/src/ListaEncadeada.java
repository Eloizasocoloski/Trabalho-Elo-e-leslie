public class ListaEncadeada<T> implements java.io.Serializable {

    private NoLista<T> primeiro;

    public ListaEncadeada() {
        this.primeiro = null;
    }

    public NoLista<T> getPrimeiro() {
        return primeiro;
    }

    public void inserir(T valor) {
        NoLista<T> novo = new NoLista<>(valor);
        novo.setProximo(primeiro);
        primeiro = novo;
    }

    public boolean estaVazia() {
        return primeiro == null;
    }

    public NoLista<T> buscar(T valor) {
        NoLista<T> atual = primeiro;
        while (atual != null) {
            if (atual.getInfo().equals(valor)) {
                return atual;
            }
            atual = atual.getProximo();
        }
        return null;
    }

    public void retirar(T valor) {
        NoLista<T> atual = primeiro;
        NoLista<T> anterior = null;

        while (atual != null) {
            if (atual.getInfo().equals(valor)) {
                if (anterior == null) {
                    primeiro = atual.getProximo();
                } else {
                    anterior.setProximo(atual.getProximo());
                }
                return;
            }
            anterior = atual;
            atual = atual.getProximo();
        }
    }

    public int obterComprimento() {
        int contador = 0;
        NoLista<T> atual = primeiro;
        while (atual != null) {
            contador++;
            atual = atual.getProximo();
        }
        return contador;
    }

    public NoLista<T> obterNo(int idx) {
        if (idx < 0 || idx >= obterComprimento()) {
            throw new IndexOutOfBoundsException("Posição inválida: " + idx);
        }
        NoLista<T> atual = primeiro;
        for (int i = 0; i < idx; i++) {
            atual = atual.getProximo();
        }
        return atual;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        NoLista<T> atual = primeiro;
        while (atual != null) {
            sb.append(atual.getInfo());
            if (atual.getProximo() != null) {
                sb.append(", ");
            }
            atual = atual.getProximo();
        }
        return sb.toString();
    }
}