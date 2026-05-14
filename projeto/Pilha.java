public class Pilha<T> implements TAD_Pilha<T> {

    private int topo;
    private int capacidade;
    private Object[] elementos;

    public Pilha() {
        this(30);
    }

    public Pilha(int capacidade) {
        this.topo       = -1;
        this.capacidade = capacidade;
        this.elementos  = new Object[capacidade];
    }

    @Override
    public boolean isEmpty() {
        return topo == -1;
    }

    @Override
    public boolean isFull() {
        return topo == capacidade - 1;
    }

    @Override
    public int size() {
        return topo + 1;
    }

    @Override
    public boolean push(T elemento) {
        if (isFull()) return false;
        elementos[++topo] = elemento;
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T pop() {
        if (isEmpty()) return null;
        return (T) elementos[topo--];
    }

    @SuppressWarnings("unchecked")
    @Override
    public T top() {
        if (isEmpty()) return null;
        return (T) elementos[topo];
    }

    @Override
    public String toString() {
        if (isEmpty()) return "Pilha vazia";
        StringBuilder sb = new StringBuilder();
        sb.append("Pilha [").append(size()).append("/").append(capacidade).append("] = [");
        for (int i = 0; i <= topo; i++) {
            sb.append(elementos[i]);
            if (i < topo) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}