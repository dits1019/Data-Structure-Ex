package Singly_LinkedList;

import java.util.NoSuchElementException;

public class SLinkedList<E> implements List<E> {

    // 노드의 첫 부분(리스트의 가장 첫 노드)
    private Node<E> head;
    // 노드의 마지막 부분(리스트의 가장 마지막 노드)
    private Node<E> tail;
    // 요소 개수
    private int size;

    // 생성자
    public SLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }


    // 특정 위치의 노드를 반환
    private Node<E> search(int index) {

        // 범위 밖(잘못된 위치)일 경우 예외 던지기
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        // head가 기리키는 노드부터 시작
        Node<E> x = head;

        for (int i = 0; i < index; i++) {
            // x노드의 다음 노드를 x에 저장한다
            x = x.next;
        }
        return x;
    }


    // 가장 앞부분에 추가
    public void addFirst(E value) {
        // 새 노드 생성
        Node<E> newNode = new Node<E>(value);
        // 새 노드의 다음 노드로 head 노드를 연결
        newNode.next = head;
        // head가 가리키는 노드를 새 노드로 변경
        head = newNode;
        size++;

        // 다음에 가리킬 노드가 없는 경우(데이터가 새 노드밖에 없는 경우)
        // 데이터가 한 개(새 노드)밖에 없으므로 새 노드는 처음 시작노드이자 마지막 노드
        // 즉 tail = head

        if (head.next == null) {
            tail = head;
        }
    }

    // 가장 마지막 부분에 추가

    @Override
    public boolean add(E value) {
        addLast(value);
        return true;
    }

    public void addLast(E value) {
        // 새 노드 생성
        Node<E> newNode = new Node<E>(value);

        // 처음 넣는 노드일 경우 addFirst로 추가
        if(size == 0) {
            addFirst(value);
            return;
        }

        // 마지막 노드(tail)의 다음 노드(next)가 새 노드를 가리키도록 하고
        // tail이 가리키는 노드를 새 노드로 바꿔줌
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    // 특정 위치에 추가
    @Override
    public void add(int index, E value) {
        // 잘못된 인덱스를 참조할 경우 예외 발생
        if(index > size || index < 0)
            throw new IndexOutOfBoundsException();

        // 추가하려는 index가 가장 앞에 추가하려는 경우 addFirst 호출
        if(index == 0) {
            addFirst(value);
            return;
        }

        // 추가하려는 index가 마지막 위치일 경우 addLast 호출
        if(index == size) {
            addLast(value);
            return;
        }

        // 추가하려는 위치의 이전 노드
        Node<E> prev_Node = search(index - 1);
        // 추가하려는 위치의 노드
        Node<E> next_node = prev_Node.next;
        // 추가하려는 노드
        Node<E> newNode = new Node<E>(value);


        // 이전 노드가 가리키는 노드를 끊은 뒤
        prev_Node.next = null;
        // 새 노드로 변경
        prev_Node.next = newNode;
        // 또한 새 노드가 가리키는 노드는 next_Node로 설정
        newNode.next = next_node;
        size++;
    }


    // 가장 앞의 요소(head)를 삭제
    public E remove() {

        Node<E> headNode = head;

        if (headNode == null)
            throw new NoSuchElementException();

        // 삭제된 노드를 반환하기 위한 임시 변수
        E element = headNode.data;

        // head의 다음 노드
        Node<E> nextNode = head.next;

        // head 노드의 데이터들을 모두 삭제
        head.data = null;
        head.next = null;

        // head 가 다음 노드를 가리키도록 업데이트
        head = nextNode;
        size--;

        // 삭제된 요소가 리스트의 유일한 요소였을 경우
        // 그요소는 head이자 tail이었으므로
        // 삭제되면서 tail도 가리킬 요소가 없기 때문에
        // size가 0일경우 tail도 null로 변환
        if(size == 0)
            tail = null;

        return element;
    }

    // 특정 index의 요소를 삭제
    @Override
    public E remove(int index) {

        // 삭제하려는 노드가 첫 번째 원소일 경우
        if (index == 0)
            return remove();


        // 잘못된 범위에 대한 예외
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();


        // 삭제할 노드의 이전 노드
        Node<E> prevNode = search(index - 1);
        // 삭제할 노드
        Node<E> removedNode = prevNode.next;
        // 삭제할 노드의 다음 노드
        Node<E> nextNode = removedNode.next;

        // 삭제되는 노드의 데이터를 반환하기 위한 임시변수
        E element = removedNode.data;

        // 이전 노드가 가리키는 노드를 삭제하려는 노드의 다음노드로 변경
        prevNode.next = nextNode;

        // 데이터 삭제
        removedNode.next = null;
        removedNode.data = null;
        size--;

        return element;
    }

    // 특정 요소를 삭제
    @Override
    public boolean remove(Object value) {

        Node<E> prevNode = head;
        boolean hasValue = false;
        // 삭제할 노드
        Node<E> x = head;

        // value 와 일치하는 노드를 찾는다.
        for (; x != null; x = x.next) {
            if (value.equals(x.data)) {
                hasValue = true;
                break;
            }
            prevNode = x;
        }

        // 일치하는 요소가 없을 경우 false 반환
        if(x == null)
            return false;

        // 만약 삭제하려는 노드가 head라면 기존 remove()를 사용
        if (x.equals(head)) {
            remove();
            return true;
        } else {
            // 이전 노드의 링크를 삭제하려는 노드의 다음 노드로 연결
            prevNode.next = x.next;

            x.data = null;
            x.next = null;
            size--;
            return true;
        }
    }


    // 해당 위치에 있는 요소를 반환
    @Override
    public E get(int index) {
        return search(index).data;
    }


    // 해당 위체에 데이터를 새로운 데이터로 교체
    @Override
    public void set(int index, E value) {
        Node<E> replaceNode = search(index);
        replaceNode.data = null;
        replaceNode.data = value;
    }


    // 찾고자 하는 요소의 위치를 반환
    @Override
    public int indexOf(Object value) {
        int index = 0;

        for (Node<E> x = head; x != null; x = x.next) {
            if(value.equals(x.data))
                return index;

            index++;
        }
        // 찾고자 하는 요소를 찾지 못했을 경우 -1 반환
        return -1;
    }


    // 사용자가 찾고자 하는 요소가 존재하는지 안하는지 반환
    @Override
    public boolean contains(Object value) {
        return indexOf(value) >= 0;
    }


    // 크기 반환
    @Override
    public int size() {
        return size;
    }


    // 리스트의 요소가 단 하나도 존재하지 않고 비어있는지 알려줌
    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    // 모든 요소를 비움
    @Override
    public void clear() {
        for (Node<E> x = head; x != null;) {
            Node<E> nextNode = x.next;
            x.data = null;
            x.next = null;
            x = nextNode;
        }
        head = tail = null;
        size = 0;
    }
}
