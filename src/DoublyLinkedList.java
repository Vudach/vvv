/**
 * Реализация двусвязного списка
 * 
 * @param <T> тип данных, хранящихся в списке
 */
public class DoublyLinkedList<T> {
    private Node<T> head;  // Голова списка
    private Node<T> tail;  // Хвост списка
    private int size;      // Размер списка
    
    /**
     * Конструктор пустого списка
     */
    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    /**
     * Добавление элемента в начало списка
     * 
     * @param data данные для добавления
     */
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }
    
    /**
     * Добавление элемента в конец списка
     * 
     * @param data данные для добавления
     */
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }
    
    /**
     * Добавление элемента по индексу
     * 
     * @param index позиция для вставки
     * @param data данные для добавления
     * @throws IndexOutOfBoundsException если индекс вне диапазона
     */
    public void add(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", Размер: " + size);
        }
        
        if (index == 0) {
            addFirst(data);
            return;
        }
        
        if (index == size) {
            addLast(data);
            return;
        }
        
        Node<T> newNode = new Node<>(data);
        Node<T> current = getNodeAt(index);
        
        newNode.next = current;
        newNode.prev = current.prev;
        current.prev.next = newNode;
        current.prev = newNode;
        size++;
    }
    
    /**
     * Удаление первого элемента списка
     * 
     * @return удаленный элемент
     * @throws IllegalStateException если список пуст
     */
    public T removeFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("Список пуст");
        }
        
        T data = head.data;
        
        if (size == 1) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return data;
    }
    
    /**
     * Удаление последнего элемента списка
     * 
     * @return удаленный элемент
     * @throws IllegalStateException если список пуст
     */
    public T removeLast() {
        if (isEmpty()) {
            throw new IllegalStateException("Список пуст");
        }
        
        T data = tail.data;
        
        if (size == 1) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return data;
    }
    
    /**
     * Удаление элемента по индексу
     * 
     * @param index позиция элемента для удаления
     * @return удаленный элемент
     * @throws IndexOutOfBoundsException если индекс вне диапазона
     */
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", Размер: " + size);
        }
        
        if (index == 0) {
            return removeFirst();
        }
        
        if (index == size - 1) {
            return removeLast();
        }
        
        Node<T> current = getNodeAt(index);
        T data = current.data;
        
        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
        
        return data;
    }
    
    /**
     * Удаление первого вхождения элемента по значению
     * 
     * @param data значение для удаления
     * @return true если элемент был найден и удален, false иначе
     */
    public boolean removeByValue(T data) {
        Node<T> current = head;
        
        while (current != null) {
            if (current.data.equals(data)) {
                if (current == head) {
                    removeFirst();
                } else if (current == tail) {
                    removeLast();
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                    size--;
                }
                return true;
            }
            current = current.next;
        }
        return false;
    }
    
    /**
     * Получение элемента по индексу
     * 
     * @param index позиция элемента
     * @return данные элемента
     * @throws IndexOutOfBoundsException если индекс вне диапазона
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", Размер: " + size);
        }
        return getNodeAt(index).data;
    }
    
    /**
     * Проверка наличия элемента в списке
     * 
     * @param data значение для поиска
     * @return true если элемент найден, false иначе
     */
    public boolean contains(T data) {
        return indexOf(data) != -1;
    }
    
    /**
     * Поиск индекса первого вхождения элемента
     * 
     * @param data значение для поиска
     * @return индекс элемента или -1 если не найден
     */
    public int indexOf(T data) {
        Node<T> current = head;
        int index = 0;
        
        while (current != null) {
            if (current.data.equals(data)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }
    
    /**
     * Получение размера списка
     * 
     * @return количество элементов в списке
     */
    public int size() {
        return size;
    }
    
    /**
     * Проверка пустоты списка
     * 
     * @return true если список пуст, false иначе
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Очистка списка
     */
    public void clear() {
        head = tail = null;
        size = 0;
    }
    
    /**
     * Печать списка от начала к концу
     */
    public void printForward() {
        if (isEmpty()) {
            System.out.println("Список пуст");
            return;
        }
        
        System.out.print("Прямой обход: ");
        Node<T> current = head;
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(" <-> ");
            }
            current = current.next;
        }
        System.out.println();
    }
    
    /**
     * Печать списка от конца к началу
     */
    public void printBackward() {
        if (isEmpty()) {
            System.out.println("Список пуст");
            return;
        }
        
        System.out.print("Обратный обход: ");
        Node<T> current = tail;
        while (current != null) {
            System.out.print(current.data);
            if (current.prev != null) {
                System.out.print(" <-> ");
            }
            current = current.prev;
        }
        System.out.println();
    }
    
    /**
     * Вспомогательный метод для получения узла по индексу
     * 
     * @param index позиция узла
     * @return узел на указанной позиции
     */
    private Node<T> getNodeAt(int index) {
        Node<T> current;
        
        // Оптимизация: начинаем с ближайшего конца
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        
        return current;
    }
}
