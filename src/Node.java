/**
 * Класс узла двусвязного списка
 * 
 * @param <T> тип данных, хранящихся в узле
 */
public class Node<T> {
    T data;           // Данные узла
    Node<T> next;     // Ссылка на следующий узел
    Node<T> prev;     // Ссылка на предыдущий узел
    
    /**
     * Конструктор узла
     * 
     * @param data данные для хранения в узле
     */
    public Node(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
