// custom_stack.c
#include <stdio.h>
#include <stdlib.h>

// Define the CustomStack structure
typedef struct {
    int maxSize; // maximum capacity
    int size;    // current number of elements
    int *stack;  // array to hold stack values
    int *inc;    // array to hold lazy increments
} CustomStack;

// Create a new CustomStack with given maxSize
CustomStack* customStackCreate(int maxSize) {
    CustomStack *cs = (CustomStack*) malloc(sizeof(CustomStack));
    if (!cs) return NULL;
    cs->maxSize = maxSize;
    cs->size = 0;
    cs->stack = (int*) calloc(maxSize, sizeof(int)); // allocate stack array
    cs->inc = (int*) calloc(maxSize, sizeof(int));   // allocate increment array
    return cs;
}

// Push x onto the stack if there's space
void customStackPush(CustomStack* obj, int x) {
    if (!obj) return;
    if (obj->size < obj->maxSize) {
        obj->stack[obj->size] = x; // put value at top index
        // inc[obj->size] should already be 0 (initialized), no need to change
        obj->size++; // increase size to represent new top
    }
}

// Pop and return the top element with any pending increments applied.
// If stack is empty, return -1.
int customStackPop(CustomStack* obj) {
    if (!obj) return -1;
    if (obj->size == 0) return -1; // empty stack
    int idx = obj->size - 1; // index of top element
    int res = obj->stack[idx] + obj->inc[idx]; // apply pending increment for this index

    // Propagate the pending increment downwards so lower elements still get it
    if (idx > 0) {
        obj->inc[idx - 1] += obj->inc[idx];
    }
    obj->inc[idx] = 0; // clear the increment for the popped position
    obj->size--; // remove top
    return res;
}

// Increment bottom k elements by val (lazy)
void customStackIncrement(CustomStack* obj, int k, int val) {
    if (!obj) return;
    if (obj->size == 0) return; // nothing to do
    int upto = k < obj->size ? k : obj->size; // how many to increment
    // We add val to inc[upto-1], meaning all elements 0..upto-1 will effectively get val
    obj->inc[upto - 1] += val;
}

// Free allocated memory
void customStackFree(CustomStack* obj) {
    if (!obj) return;
    free(obj->stack);
    free(obj->inc);
    free(obj);
}

// Example usage (mirrors the provided example)
int main(void) {
    CustomStack *stk = customStackCreate(3); // maxSize = 3

    customStackPush(stk, 1); // stack: [1]
    customStackPush(stk, 2); // stack: [1,2]
    printf("%d\n", customStackPop(stk)); // pop -> 2
    customStackPush(stk, 2); // stack: [1,2]
    customStackPush(stk, 3); // stack: [1,2,3]
    customStackPush(stk, 4); // ignored (stack full)
    customStackIncrement(stk, 5, 100); // increment bottom 3 by 100 -> [101,102,103]
    customStackIncrement(stk, 2, 100); // increment bottom 2 by 100 -> [201,202,103]

    printf("%d\n", customStackPop(stk)); // -> 103
    printf("%d\n", customStackPop(stk)); // -> 202
    printf("%d\n", customStackPop(stk)); // -> 201
    printf("%d\n", customStackPop(stk)); // -> -1 (empty)

    customStackFree(stk);
    return 0;
}
