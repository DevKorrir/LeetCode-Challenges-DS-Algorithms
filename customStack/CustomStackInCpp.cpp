// custom_stack.cpp
#include <bits/stdc++.h>
using namespace std;

// Class implementing the CustomStack
class CustomStack {
private:
    int maxSize;         // maximum capacity
    int sz;              // current size (number of elements)
    vector<int> stack;   // store values
    vector<int> inc;     // lazy increments

public:
    // Constructor: prepare vectors of length maxSize
    CustomStack(int maxSize) : maxSize(maxSize), sz(0), stack(maxSize, 0), inc(maxSize, 0) {}

    // Push x onto the stack if there's room
    void push(int x) {
        if (sz < maxSize) {
            stack[sz] = x; // place at index sz (top)
            // inc[sz] is already 0 or leftover - it should be 0 because popping clears it,
            // but we leave as-is since logic handles pending increments correctly.
            sz++;
        }
    }

    // Pop and return top element (including pending increments). Return -1 if empty.
    int pop() {
        if (sz == 0) return -1;
        int idx = sz - 1;
        int res = stack[idx] + inc[idx]; // apply any pending increment for this top element

        // propagate pending increment down to previous element, if any
        if (idx > 0) {
            inc[idx - 1] += inc[idx];
        }
        inc[idx] = 0; // clear increment at this position
        sz--;
        return res;
    }

    // Increment bottom k elements by val (lazy)
    void increment(int k, int val) {
        if (sz == 0) return;
        int upto = min(k, sz);
        inc[upto - 1] += val; // mark that indices 0..upto-1 get +val
    }
};

// Example usage (main)
int main() {
    CustomStack stk(3); // capacity 3

    stk.push(1); // [1]
    stk.push(2); // [1,2]
    cout << stk.pop() << '\n'; // should print 2
    stk.push(2); // [1,2]
    stk.push(3); // [1,2,3]
    stk.push(4); // ignored (full)
    stk.increment(5, 100); // [101,102,103]
    stk.increment(2, 100); // [201,202,103]
    cout << stk.pop() << '\n'; // 103
    cout << stk.pop() << '\n'; // 202
    cout << stk.pop() << '\n'; // 201
    cout << stk.pop() << '\n'; // -1
    return 0;
}
