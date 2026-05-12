package skiplist;

public class IntSkipListNode {
    public int value;
    public IntSkipListNode[] next;

    public IntSkipListNode(int value, int level) {
        this.value = value;
        this.next = new IntSkipListNode[level];

        for (int i = 0; i < level; i++) {
            this.next[i] = null;
        }
    }
}
