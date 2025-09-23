/**
 * LeetCode-style signature: sorts the input array in ascending order and returns it.
 */
fun sortArray(nums: IntArray): IntArray {
    heapSort(nums)
    return nums
}

/**
 * In-place heapsort: build max-heap then repeatedly extract max to the end.
 */
private fun heapSort(a: IntArray) {
    val n = a.size
    if (n <= 1) return

    // Build max-heap (heapify whole array)
    // Start from the last parent node and sift down each node.
    for (i in n / 2 - 1 downTo 0) {
        siftDown(a, i, n)
    }

    // Repeatedly swap the root(max) with the last element of the heap,
    // reduce heap size by 1, and restore heap property.
    var end = n - 1
    while (end > 0) {
        // move current max to position `end`
        val tmp = a[0]; a[0] = a[end]; a[end] = tmp

        // restore max-heap property for heap size `end`
        siftDown(a, 0, end)
        end--
    }
}

/**
 * Sift-down (heapify) procedure:
 * starting from index `start`, push element down until heap property holds
 * for a heap of size `heapSize` (elements in indices [0, heapSize-1]).
 */
private fun siftDown(a: IntArray, start: Int, heapSize: Int) {
    var root = start
    while (true) {
        val left = 2 * root + 1                 // index of left child
        if (left >= heapSize) break            // no children -> done

        // assume root is largest so far
        var swap = root

        // if left child greater than current, mark left as candidate
        if (a[swap] < a[left]) swap = left

        val right = left + 1                   // index of right child
        // if right child exists and is greater than current candidate, choose right
        if (right < heapSize && a[swap] < a[right]) swap = right

        // if root already larger than both children, heap property satisfied
        if (swap == root) break

        // otherwise swap root with the larger child and continue
        val tmp = a[root]; a[root] = a[swap]; a[swap] = tmp
        root = swap
    }
}

// ---------- Optional test harness ----------
fun main() {
    val examples = arrayOf(
        intArrayOf(5, 2, 3, 1),
        intArrayOf(5, 1, 1, 2, 0, 0),
        intArrayOf(),
        intArrayOf(1),
        intArrayOf(-2, 3, 0, -1, 2, 3),
        intArrayOf(3, 2, 1, 0, -1, -2)
    )

    for (arr in examples) {
        println("before: ${arr.joinToString(", ")}")
        val sorted = sortArray(arr)
        println(" after: ${sorted.joinToString(", ")}")
        println()
    }
}
