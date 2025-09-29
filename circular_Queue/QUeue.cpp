// my_circular_queue.cpp
// C++ implementation with friendly inline comments

#include <iostream>
#include <vector>
using namespace std;

class MyCircularQueue {
private:
    vector<int> data; // underlying array storage
    int capacity;     // maximum size
    int head;         // index of current front
    int tail;         // index where next element will be inserted
    int count;        // number of elements

public:
    // Constructor: allocate vector of size k
    MyCircularQueue(int k) : data(k), capacity(k), head(0), tail(0), count(0) {}

    // Insert element into the circular queue. Return true if successful.
    bool enQueue(int value) {
        if (isFull()) return false;    // cannot insert if full
        data[tail] = value;            // store value at tail
        tail = (tail + 1) % capacity;  // advance tail with wrap-around
        count++;                       // increase stored elements
        return true;
    }

    // Delete the front element. Return true if successful.
    bool deQueue() {
        if (isEmpty()) return false;   // nothing to remove
        head = (head + 1) % capacity;  // advance head to next element
        count--;                       // decrease stored elements
        return true;
    }

    // Get the front item. Return -1 if empty.
    int Front() {
        if (isEmpty()) return -1;
        return data[head];             // head points to front
    }

    // Get the last item. Return -1 if empty.
    int Rear() {
        if (isEmpty()) return -1;
        int lastIndex = (tail - 1 + capacity) % capacity; // tail is next insert slot
        return data[lastIndex];
    }

    // Check if empty.
    bool isEmpty() {
        return count == 0;
    }

    // Check if full.
    bool isFull() {
        return count == capacity;
    }
};

// Example usage to match the prompt's example
int main() {
    MyCircularQueue q(3);
    cout << boolalpha;
    cout << q.enQueue(1) << endl; // true
    cout << q.enQueue(2) << endl; // true
    cout << q.enQueue(3) << endl; // true
    cout << q.enQueue(4) << endl; // false
    cout << q.Rear() << endl;     // 3
    cout << q.isFull() << endl;   // true
    cout << q.deQueue() << endl;  // true
    cout << q.enQueue(4) << endl; // true
    cout << q.Rear() << endl;     // 4
    return 0;
}
