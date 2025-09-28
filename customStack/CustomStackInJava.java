// CustomStack.java
public class CustomStack {
    private int[] stack; // holds values
    private int[] inc;   // lazy increments
    private int size;    // current number of elements
    private int maxSize; // capacity

    // Constructor to initialize arrays
    public CustomStack(int maxSize) {
        this.maxSize = maxSize;
        this.stack = new int[maxSize];
        this.inc = new int[maxSize];
        this.size = 0;
    }

    // Push x if there's room
    public void push(int x) {
        if (size < maxSize) {
            stack[size] = x; // put at top index
            // inc[size] remains as-is (should be 0 when unused)
            size++;
        }
    }

    // Pop top element with pending increments applied, or -1 if empty
    public int pop() {
        if (size == 0) return -1;
        int idx = size - 1;
        int res = stack[idx] + inc[idx]; // apply increment for this index

        // pass pending increment downwards so lower elements also get it
        if (idx > 0) {
            inc[idx - 1] += inc[idx];
        }
        inc[idx] = 0; // clear increment at popped position
        size--;
        return res;
    }

    // Increment bottom k elements by val (lazy)
    public void increment(int k, int val) {
        if (size == 0) return;
        int upto = Math.min(k, size);
        inc[upto - 1] += val; // record pending addition for indices 0..upto-1
    }

    // Small driver to run the provided example
    public static void main(String[] args) {
        CustomStack stk = new CustomStack(3); // capacity 3
        stk.push(1);                       // [1]
        stk.push(2);                       // [1,2]
        System.out.println(stk.pop());     // -> 2
        stk.push(2);                       // [1,2]
        stk.push(3);                       // [1,2,3]
        stk.push(4);                       // ignored (full)
        stk.increment(5, 100);             // -> [101,102,103]
        stk.increment(2, 100);             // -> [201,202,103]
        System.out.println(stk.pop());     // -> 103
        System.out.println(stk.pop());     // -> 202
        System.out.println(stk.pop());     // -> 201
        System.out.println(stk.pop());     // -> -1
    }
}
