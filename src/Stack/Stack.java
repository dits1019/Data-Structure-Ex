package Stack;

import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack<E> implements StackInterface<E> {
    // 최소 크기(배열이 생성될 때의 최초 할당 크기)
    private static final int DEFAULT_CAPACITY = 10;
    // 빈 배열
    private static final Object[] EMPTY_ARRAY = {};

    // 요소를 담을 배열
    private Object[] array;
    // 요소 개수
    private int size;

    // 생성자1(초기 공간 할당 X)
    public Stack() {
        this.array = EMPTY_ARRAY;
        this.size = 0;
    }

    // 생성자2(초기 공간 할당 O)
    public Stack(int capacity) {
        this.array = new Object[capacity];
        this.size = 0;
    }

    // 데이터 개수에 따라 최적화된 크기를 갖게함
    private void resize() {
        // 빈 배열일 경우
        if(Arrays.equals(array, EMPTY_ARRAY)) {
            array = new Object[DEFAULT_CAPACITY];
            return;
        }

        // 현재 배열 크기
        int arrayCapacity = array.length;

        // 가득 찰 경우
        if(size == arrayCapacity) {
            int newSize = arrayCapacity * 2;

            // 배열 복사
            array = Arrays.copyOf(array, newSize);
            return;
        }

        // 용적의 절반 미만으로 요소가 차지하고 있을 경우
        if(size < (arrayCapacity / 2)) {
            int newCapacity = (arrayCapacity / 2);

            // 배열 복사
            array = Arrays.copyOf(array, Math.max(DEFAULT_CAPACITY, newCapacity));
            return;
        }
    }


    // 가장 마지막 부분에 추가(항상 최상단에만 추가해야함)
    @Override
    public E push(E item) {
        // 꽉 차있다면 크기를 재할당
        if(size == array.length) resize();

        // 마지막 위치에 요소 추가
        array[size] = item;
        // 사이즈 1 증가
        size++;

        return item;
    }


    // 가장 마지막 부분에 데이터 삭제(항상 최상단에만 삭제해야함)
    @Override
    public E pop() {
        // 만약 삭제할 요소가 없다면 Stack이 비어있다는 의미이므로 예외 발생시키기
        if(size == 0) throw new EmptyStackException();

        // 삭제될 요소를 반환하기 위한 임시 변수
        @SuppressWarnings("unchecked")
        E obj = (E) array[size - 1];

        // 요소 삭제
        array[size - 1] = null;

        // 사이즈 1 감소
        size--;
        // 크기 재할당
        resize();

        return obj;
    }


    // 가장 상단에 있는 데이터를 확인(pop 메소드에서 삭제과정만 없는 것)
    @SuppressWarnings("unchecked")
    @Override
    public E peek() {
        // 만약 삭제할 요소가 없다면 Stack이 비어있다는 의미이므로 예외 발생시키기
        if(size == 0) throw new EmptyStackException();

        return (E) array[size - 1];
    }


    // 찾으려는 데이터가 상단의 데이터로부터 얼마만큼 떨어져 있는지에 대한 상대적 위치 값

    @Override
    public int search(Object value) {
        for(int idx = size - 1; idx >= 0; idx--) {
            // 같은 객체를 찾았을 경우 size - idx 값을 반환
            if(array[idx].equals(value))
                return size - idx;

        }
        return -1;
    }


    // Stack에 있는 요소의 개수
    @Override
    public int size() {
        return size;
    }


    // 모든 요소들을 비워버리기
    @Override
    public void clear() {
        // 저장되어있던 모든 요소들 null 처리해줌
        for(int i = 0; i < size; i++)
            array[i] = null;

        size = 0;
        // 사용한 크기가 현재 크기의 기대치에 근접할 가능성이 높기 때문에 일단 크기를 줄임
        resize();
    }


    // Stack이 비어있는지 확인

    @Override
    public boolean empty() {
        return size == 0;
    }
}
