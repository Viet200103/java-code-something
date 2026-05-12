package skiplist;

import java.util.Random;

public class IntSkipList {
    private final int maxLevel;
    private final IntSkipListNode[] root;

    /*
        Configure the exponent array to use for random level selection
        with exponential probability (the higher the level, the lower the probability)
     */
    private final int[] exponents;

    private Random rd = new Random();

    public IntSkipList(int maxLevel) {
        this.maxLevel = maxLevel;
        this.root = new IntSkipListNode[maxLevel];

        exponents = new int[maxLevel];
        for (int i = 0; i < maxLevel; i++) {
            this.root[i] = null;
        }

        chooseExponents();
    }


    public boolean isEmpty() {
        return this.root[0] == null;
    }

    public void chooseExponents() {
        assert exponents != null;
        exponents[maxLevel - 1] = (2 << (maxLevel - 1)) - 1;
        for (int i = maxLevel - 2, k = 0; i >= 0; i--, k++) {
            exponents[i] = exponents[i + 1] - (2 << k); // 2 ^ k
        }
    }

    public int chooseLevel() {
        // Lower levels appear more often than higher levels.
        int random = (Math.abs(rd.nextInt()) % exponents[maxLevel - 1]) + 1;

        // What range does random fall into?
        // Each range corresponds to a level.

        // exponents = [1, 2, 4, 8, 16] lv 0 -> lv 4
        // random ∈ [1..16]

        // r < 2, lv = 0
        // r < 4, lv = 1
        // r < 8, lv = 2
        // r < 16, lv = 3
        // else lv = 4
        for (int i = 1; i < maxLevel; i++) {
            if (random < exponents[i]) {
                return  i - 1;
            }
        }
        return maxLevel - 1;
    }
    
    public boolean contains(int value) {
        IntSkipListNode prev = null;
        
        for (int i = maxLevel - 1; i >= 0; i--) {
            IntSkipListNode curr = (prev == null) ? root[i] : prev.next[i];
            
            while (curr != null && curr.value < value) {
                prev = curr;
                curr = curr.next[i];
            }
            
            if (curr != null && curr.value == value) {
                return true;
            }
        }
        
        return false;
    } 
    
    public void insert(int value) {
        // Level 2:      10 -------- 50
        // Level 1:      10 -- 30 -- 50 -- 70
        // Level 0: 5 -- 10 -- 20 -- 30 -- 50 -- 70

        // Save the node preceding the insert position at each level.
        IntSkipListNode[] update = new IntSkipListNode[maxLevel];

        IntSkipListNode prev = null; // previous node

        // Find the from highest to lowest level
        for (int i = maxLevel - 1; i >= 0; i--) {
            IntSkipListNode curr = (prev == null) ? root[i] : prev.next[i];
            
            while (curr != null && curr.value < value) {
                prev = curr;
                curr = curr.next[i];
            }

            update[i] = prev;
        }
        
        IntSkipListNode nextNode = (update[0] == null) ? root[0] : update[0].next[0];
        if (nextNode != null && nextNode.value == value) {
            return; 
        }
        
        int newLevel = chooseLevel();
        IntSkipListNode newNode = new IntSkipListNode(value, newLevel + 1);
        
        for (int i = 0; i <= newLevel; i++) {
            if (update[i] == null) {
                newNode.next[i] = root[i];
                root[i] = newNode;
            } else {
                newNode.next[i] = update[i].next[i];
                update[i].next[i] = newNode;
            }
        }
    }

    public void print() {
        for (int i = maxLevel - 1; i >= 0; i--) {
            System.out.print("Level " + i + ": ");
            IntSkipListNode curr = root[i];
            while (curr != null) {
                System.out.print(curr.value + " -> ");
                curr = curr.next[i];
            }
            System.out.println("null");
        }
    }

    public Random getRd() {
        return rd;
    }

    public void setRd(Random rd) {
        this.rd = rd;
    }
}
