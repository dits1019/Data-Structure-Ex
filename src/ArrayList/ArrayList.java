package ArrayList;

import java.util.Arrays;

public class ArrayList<E> implements List<E> {
    // 최소(기본) 크기(배열이 생성될 때의 최초 할당 크기)
    private static final int DEFAULT_CAPACITY = 10;
    // 빈 배열
    private static final Object[] EMPTY_ARRAY = {};

    // 요소 개수
    private int size;

    // 요소를 담을 배열
    Object[] array;

    // 생성자1(초기 공간 할당 X)
    public ArrayList() {
        this.array = EMPTY_ARRAY;
        this.size = 0;
    }

    // 생성자2(초기 공간 할당 O)
    public ArrayList(int capacity) {
        this.array = new Object[capacity];
        this.size = 0;
    }

    // 현재 array의 길이와 데이터의 개수(size)를 비교교
    private void resize() {
        int array_capacity = array.length;

        // 배열의 크기가 0일 경우
        if (Arrays.equals(array, EMPTY_ARRAY)) {
            // DEFAULT_CAPACITY만큼 배열을 생성
            array = new Object[DEFAULT_CAPACITY];
            return;
        }

        // 용량이 꽉 찰 경우
        if (size == array_capacity) {
            // 늘릴 필요가 있으므로 크기를 2배로 설정하고 생성
            int new_capacity = array_capacity * 2;

            // copy
            // copyOf(x, y) = x를 복사해서 y만큼 크기를 늘림(늘어난 곳은 null)
            array = Arrays.copyOf(array, new_capacity);
            return;
        }

        // 배열의 크기의 절반 미만으로 요소가 차지하고 있을 경우
        if (size < (array_capacity / 2)) {
            // 불필요한 공간을 줄이기 위해 절반으로 줄임
            int new_capacity = array_capacity / 2;

            // copy
            // 만약 복사할 배열보다 용적의 크기가 작을경우 새로운 용적까지만 복사하고 반환
            array = Arrays.copyOf(array, Math.max(new_capacity, DEFAULT_CAPACITY));
            return;
        }
    }

    // 가장 마지막 부분에 추가
    @Override
    public boolean add(E value) {
        addLast(value);
        return true;
    }

    public void addLast(E value) {
        // 꽉차있는 상태라면 크기 재할당
        if (size == array.length)
            resize();
        // 마지막 위치에 요소 추가
        array[size] = value;
        // 사이즈 1 증가
        size++;
    }

    // 중간 부분에 추가
    @Override
    public void add(int index, E value) {
        // 범위를 벗어나면 예외 발생
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException();

        // index가 마지막 위치라면 addLast 메소드로 요소추가
        if (index == size) {
            addLast(value);
        } else {
            // 꽉차있다면 크기 재할당
            if (size == array.length)
                resize();

            // index 기준 후자에 있는 모든 요소들 한 칸씩 뒤로 밀기
            for (int i = size; i > index; i--)
                array[i] = array[i - 1];

            // index 위치에 요소 할당
            array[index] = value;
            size++;
        }
    }


    // 가장 처음 부분에 추가
    public void addFirst(E value) {
        add(0, value);
    }


    // 해당 위치에 있는 요소를 반환
    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        // 범위를 벗어나면 예외 발생
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();

        // Object 타입에서 E타입으로 캐스팅 후 반환
        return (E) array[index];
    }


    // 기존에 index에 위치한 데이터를 새로운 데이터로 교체체
    @Override
    public void set(int index, E value) {
        // 범위를 벗어나면 예외 발생
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else {
            // 해당 위치의 요소를 교체
            array[index] = value;
        }
    }


    // 찾고자 하는 요소의 위치를 반환
    @Override
    public int indexOf(Object value) {
        int i = 0;

        // value와 같은 객체(요소 값)일 경우 i(위치) 반환
        for (i = 0; i < size; i++) {
            if (array[i].equals(value))
                return i;
        }
        // 일치하는 것이 없을 경우 -1을 반환
        return -1;
    }


    // 거꾸로 탐색
    public int lastIndexOf(Object value) {
        for (int i = size - 1; i >= 0; i--) {
            if (array[i].equals(value))
                return i;
        }
        return -1;
    }


    // 찾고자 하는 요소가 존재하는지 안하는지 반환
    @Override
    public boolean contains(Object value) {
        // 0 이상이면 요소가 존재한다는 뜻
        if (indexOf(value) >= 0) return true;
        else return false;
    }


    // 특정 위치에 있는 요소 제거
    @SuppressWarnings("unchecked")
    @Override
    public E remove(int index) {
        // 범위를 벗어나면 예외 발생
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();

        // 삭제될 요소를 반환하기 위해 임시로 담아둠
        E element = (E) array[index];
        array[index] = null;

        // 삭제한 요소의 뒤에 있는 모든 요소들을 한 칸씩 당겨옴
        for (int i = index; i < size; i++) {
            array[i] = array[i + 1];
            array[i + 1] = null;
        }
        size--;
        resize();
        return element;
    }


    // 사용자가 원하는 특정 요소를 리스트에서 찾아서 삭제
    @Override
    public boolean remove(Object value) {
        // 삭제하고자 하는 요소의 인덱스 찾기
        int index = indexOf(value);

        // -1이라면 array에 요소가 없다는 의미이므로 false 반환
        if (index == -1)
            return false;

        // index 위치에 있는 요소를 삭제
        remove(index);
        return true;
    }


    // 현재 크기 반환
    @Override
    public int size() {
        return size;
    }


    // ArrayList가 비어있는지 알려줌
    @Override
    public boolean isEmpty() {
        // 요소가 0개일 경우 비어있다는 의미이므로 true 반환
        return size == 0;
    }


    // 모든 요소들 제거
    @Override
    public void clear() {
        // 모든 공간을 null 처리
        for (int i = 0; i < size; i++)
            array[i] = null;

        size = 0;
        resize();
    }


    // ArrayList를 새로 복사
    @Override
    public Object clone() throws CloneNotSupportedException {

        // 새로운 객체 생성
        ArrayList<?> cloneList = (ArrayList<?>)super.clone();

        // 새로운 객체의 배열도 생성해주어야 함 (객체는 얕은복사가 되기 때문에)
        cloneList.array = new Object[size];

        // 배열의 값을 복사함
        System.arraycopy(array, 0, cloneList.array, 0, size);

        return cloneList;
    }


    // 현재있는 ArrayList의 리스트를 객체배열로 반환
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    // ArrayList를 이미 생성된 다른 배열에 복사
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            // copyOf(원본 배열, 복사할 길이, Class<? extends T[]> 타입)
            return (T[]) Arrays.copyOf(array, size, a.getClass());
        }
        // 원본배열, 원본배열 시작위치, 복사할 배열, 복사할배열 시작위치, 복사할 요소 수
        System.arraycopy(array, 0, a, 0, size);
        return a;
    }

}