package com.example.CRUD.ordenacao;

public class FilaObj<T> {

    private int tamanho;
    private T[] fila;

    // Construtor
    public FilaObj(int capaciade) {
        this.tamanho = 0;
        this.fila = (T[]) new Object[capaciade];
    }

    public int getTamanho() {
        return tamanho ;
    }

    // Métodos

    /* Método isEmpty() - retorna true se a fila está vazia e false caso contrário */
    public boolean isEmpty() {
        return tamanho == 0;
    }

    /* Método isFull() - retorna true se a fila está cheia e false caso contrário */
    public boolean isFull() {
        return tamanho == fila.length;
    }

    /* Método insert - recebe um elemento e insere esse elemento na fila
                       no índice tamanho, e incrementa tamanho
                       Lançar IllegalStateException caso a fila esteja cheia
     */
    public void insert(T info) {
        if(!isFull()) {
            fila[tamanho++] = info;
        } else {
            throw new IllegalStateException("Fila cheia!");
        }
    }

    /* Método peek - retorna o primeiro elemento da fila, sem removê-lo */
    public T peek() {
        if(!isEmpty()) {
            return fila[0];
        }
        return null;
    }

    /* Método poll - remove e retorna o primeiro elemento da fila, se a fila não estiver
       vazia. Quando um elemento é removido, a fila "anda", e tamanho é decrementado
       Depois que a fila andar, "limpar" o ex-último elemento da fila, atribuindo null
     */
    public T poll() {
        T aux = fila[0];
        if (!isEmpty()) {
            for(int i = 0; i < tamanho - 1; i++) {
                fila[i] = fila[i + 1];
            }
            fila[--tamanho] = null;
            return aux;
        }
        return null;
    }

    /* Método exibe() - exibe o conteúdo da fila */
    public void exibe() {
        if(!isEmpty()) {
            for(int i = tamanho; i >= 0; i--) {
                System.out.print(fila[i]);
            }
        } else {
            System.out.printf("Pilha vazia");
        }
    }

}
