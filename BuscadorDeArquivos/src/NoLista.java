public class NoLista <T> implements java.io.Serializable {

    private T info;
    private NoLista<T> proximo;

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public NoLista<T> getProximo() {
        return proximo;
    }

    public void setProximo(NoLista<T> proximo) {
        this.proximo = proximo;
    }

    public NoLista(T info) {
        this.info = info;
        this.proximo = null;
    }
}