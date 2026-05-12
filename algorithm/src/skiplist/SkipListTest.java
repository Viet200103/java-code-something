package skiplist;

public class SkipListTest {

    public static void run() {
        IntSkipList skipList = new IntSkipList(4);
        skipList.insert(20);
        skipList.insert(10);

        skipList.print();

        System.out.println("Contains 30: " + skipList.contains(30));
        System.out.println("Contains 20: " + skipList.contains(20));
    }
}
