fun reverse(x: Int): Int {
    var n = x
    var rev = 0

    while (n != 0) {
        val pop = n % 10
        n /= 10


        if (rev > Int.MAX_VALUE / 10 || (rev == Int.MAX_VALUE / 10 && pop > 7)) return 0
        if (rev < Int.MIN_VALUE / 10 || (rev == Int.MIN_VALUE / 10 && pop < -8)) return 0

        rev = rev * 10 + pop
    }

    return rev
}

// Simple tests
fun main() {
    val tests = listOf(123, -123, 120, 0, 1534236469)
    for (t in tests) {
        println("$t -> ${reverse(t)}")
    }
}
