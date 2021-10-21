package Singly_LinkedList;

public class Node<E> {
    E data;
    // 다음 노드객체를 가리키는 래퍼런스 변수
    Node<E> next;

    Node(E data) {
        this.data = data;
        this.next = null;
    }

}
