// MyCircularQueue.java
// Java implementation with friendly inline comments

public class MyCircularQueue {
    private final int[] data; // array to store elements
    private final int capacity; // maximum number of elements
    private int head; // index of front element
    private int tail; // index where next element will be placed
    private int count; // current number of elements

    // Initialize the circular queue with size k
    public MyCircularQueue(int k) {
        this.capacity = k;
        this.data = new int[k];
        this.head = 0;
        this.tail = 0;
        this.count = 0;
    }

    // Insert element into the circular queue. Return true if successful.
    public boolean enQueue(int value) {
        if (isFull()) {
            return false; // no space to insert
        }
        data[tail] = value;          // store value at tail index
        tail = (tail + 1) % capacity; // move tail to next slot (wrap around)
        count++;                     // increase number of elements
        return true;
    }

    // Delete an element from the circular queue. Return true if successful.
    public boolean deQueue() {
        if (isEmpty()) {
            return false; // nothing to remove
        }
        // we don't need to "clear" the value; just move head forward and decrement count
        head = (head + 1) % capacity; // move head to the next element (wrap around)
        count--;                      // one less element
        return true;
    }

    // Get the front item. Return -1 if the queue is empty.
    public int Front() {
        if (isEmpty()) return -1;
        return data[head]; // head points to the front element
    }

    // Get the last item (rear). Return -1 if the queue is empty.
    public int Rear() {
        if (isEmpty()) return -1;
        // tail points to the next insertion slot, so the last element is at tail-1 (wrap around)
        int lastIndex = (tail - 1 + capacity) % capacity;
        return data[lastIndex];
    }

    // Check whether the circular queue is empty.
    public boolean isEmpty() {
        return count == 0;
    }

    // Check whether the circular queue is full.
    public boolean isFull() {
        return count == capacity;
    }

    // Simple test driver using the example given
    public static void main(String[] args) {
        MyCircularQueue q = new MyCircularQueue(3);
        System.out.println(q.enQueue(1)); // true
        System.out.println(q.enQueue(2)); // true
        System.out.println(q.enQueue(3)); // true
        System.out.println(q.enQueue(4)); // false (queue full)
        System.out.println(q.Rear());     // 3
        System.out.println(q.isFull());   // true
        System.out.println(q.deQueue());  // true
        System.out.println(q.enQueue(4)); // true
        System.out.println(q.Rear());     // 4
    }
}
