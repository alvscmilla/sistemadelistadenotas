public interface TAD_Pilha<T> {
    boolean push(T elemento);
    T pop();
    
    T top();
    
    boolean isEmpty();
    
    boolean isFull();
    
    int size();
}
