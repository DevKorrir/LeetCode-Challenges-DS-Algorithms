// This is our function. It's named 'isPalindrome', takes an integer 'x' as input, and returns a 'Boolean' (true or false).
fun isPalindrome(x: Int): Boolean {

    // A negative number can't be a palindrome because the '-' sign changes the order.
    // For example, -121 is not the same as 121-.
    if (x < 0) return false

    // This condition handles numbers that end in zero, like 120. 
    // The only number that ends in zero and is a palindrome is 0 itself.
    // 120 reversed is 021, which is 21, so it's not a palindrome.
    // The check 'x != 0' is important so that the number 0 is not excluded.
    if (x % 10 == 0 && x != 0) return false

    // We create a new variable 'num' and set it to the value of 'x'.
    // We do this so we don't change the original number 'x' as we work.
    var num = x

    // We create a variable to build the reversed half of the number. It starts at 0.
    var reversedHalf = 0

    // This is a loop that will keep running as long as the first half of the number ('num')
    // is greater than the reversed second half ('reversedHalf').
    // We stop when we have reversed roughly half the digits.
    while (num > reversedHalf) {
    
        // We build the reversed number by taking the last digit of 'num' and adding it to 'reversedHalf'.
        // 'num % 10' gives us the last digit (e.g., 121 % 10 = 1).
        // Multiplying 'reversedHalf' by 10 shifts its digits to the left, making room for the new digit.
        reversedHalf = reversedHalf * 10 + num % 10

        // We remove the last digit from 'num' by dividing it by 10.
        // In integer division, 121 / 10 becomes 12.
        num /= 10
    }

    // Now, we have two conditions to check for a palindrome:
    // 1. If the number has an **even** number of digits (like 1221), then 'num' and 'reversedHalf' will be equal at the end of the loop.
    //    For example, with 1221, 'num' becomes 12 and 'reversedHalf' becomes 12.
    // 2. If the number has an **odd** number of digits (like 121), 'num' will be the single middle digit (1) and 'reversedHalf' will be the reversed first half (12).
    //    We can remove the last digit of 'reversedHalf' by dividing it by 10 to get the correct comparison.
    //    12 / 10 becomes 1, which matches 'num'.
    return num == reversedHalf || num == reversedHalf / 10
}

fun main() {
    println(isPalindrome(121))    // true
    println(isPalindrome(-121))   // false
    println(isPalindrome(10))     // false
    println(isPalindrome(12321))  // true
    println(isPalindrome(0))      // true
}


// second code while converting to a string
fun isPalindromeString(x: Int): Boolean {
    // Negative numbers are not palindromes because of the leading '-' sign.
    if (x < 0) return false

    val s = x.toString()
    val rev = s.reversed()
    return s == rev
}

// Quick test
fun main() {
    println(isPalindromeString(121))   // true
    println(isPalindromeString(-121))  // false
    println(isPalindromeString(10))    // false
    println(isPalindromeString(0))     // true
}
