package ArrayList;

public class ArrayListTest {
    public static void main(String[] args) {
        ArrayList<String> arrayListEx = new ArrayList<String>(5);
        arrayListEx.add("가");
        arrayListEx.add("나");

        for (int i = 0; i < arrayListEx.size(); i++) {
            System.out.println(arrayListEx.get(i));
        }
    }
}
