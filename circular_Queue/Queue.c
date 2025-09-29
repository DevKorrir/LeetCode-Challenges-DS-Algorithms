/* my_circular_queue.c
   C implementation with friendly inline comments
*/

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

// Define struct to hold circular queue data
typedef struct {
    int *data;     // array of values
    int capacity;  // maximum elements
    int head;      // index of front element
    int tail;      // index where next element will be stored
    int count;     // current number of elements
} MyCircularQueue;

// Create and initialize the circular queue
MyCircularQueue* myCircularQueueCreate(int k) {
    MyCircularQueue *q = (MyCircularQueue*) malloc(sizeof(MyCircularQueue));
    if (!q) return NULL;
    q->capacity = k;
    q->data = (int*) malloc(sizeof(int) * k);
    q->head = 0;
    q->tail = 0;
    q->count = 0;
    return q;
}

// Insert into queue. Return true if successful.
bool myCircularQueueEnQueue(MyCircularQueue* obj, int value) {
    if (obj->count == obj->capacity) return false; // full
    obj->data[obj->tail] = value;                   // place value at tail
    obj->tail = (obj->tail + 1) % obj->capacity;   // advance tail (wrap)
    obj->count++;                                   // increment stored elements
    return true;
}

// Remove front element. Return true if successful.
bool myCircularQueueDeQueue(MyCircularQueue* obj) {
    if (obj->count == 0) return false;              // empty
    obj->head = (obj->head + 1) % obj->capacity;    // advance head (wrap)
    obj->count--;                                   // decrement stored elements
    return true;
}

// Get front value, or -1 if empty
int myCircularQueueFront(MyCircularQueue* obj) {
    if (obj->count == 0) return -1;
    return obj->data[obj->head];
}

// Get rear value, or -1 if empty
int myCircularQueueRear(MyCircularQueue* obj) {
    if (obj->count == 0) return -1;
    int lastIndex = (obj->tail - 1 + obj->capacity) % obj->capacity;
    return obj->data[lastIndex];
}

// Check if empty
bool myCircularQueueIsEmpty(MyCircularQueue* obj) {
    return obj->count == 0;
}

// Check if full
bool myCircularQueueIsFull(MyCircularQueue* obj) {
    return obj->count == obj->capacity;
}

// Free the queue
void myCircularQueueFree(MyCircularQueue* obj) {
    if (!obj) return;
    free(obj->data);
    free(obj);
}

// Example driver mirroring the prompt example
int main(void) {
    MyCircularQueue *q = myCircularQueueCreate(3);
    printf("%s\n", myCircularQueueEnQueue(q, 1) ? "true" : "false"); // true
    printf("%s\n", myCircularQueueEnQueue(q, 2) ? "true" : "false"); // true
    printf("%s\n", myCircularQueueEnQueue(q, 3) ? "true" : "false"); // true
    printf("%s\n", myCircularQueueEnQueue(q, 4) ? "true" : "false"); // false
    printf("%d\n", myCircularQueueRear(q));    // 3
    printf("%s\n", myCircularQueueIsFull(q) ? "true" : "false"); // true
    printf("%s\n", myCircularQueueDeQueue(q) ? "true" : "false"); // true
    printf("%s\n", myCircularQueueEnQueue(q, 4) ? "true" : "false"); // true
    printf("%d\n", myCircularQueueRear(q));    // 4

    myCircularQueueFree(q);
    return 0;
}
