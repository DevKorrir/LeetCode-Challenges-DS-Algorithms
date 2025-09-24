

// This class is like a smart phonebook for food! It helps us quickly find the highest-rated dish for any type of cuisine.
class FoodRatings(foods: Array<String>, cuisines: Array<String>, ratings: IntArray) {

    // This is a map (like a dictionary) that stores a food and its cuisine.
    // e.g., "pizza" -> "italian"
    private val foodToCuisine = HashMap<String, String>()

    // This map stores a food and its rating.
    // e.g., "pizza" -> 9
    private val foodToRating = HashMap<String, Int>()

    // This is a special rule for sorting our food. It's called a 'Comparator'.
    // It tells our PriorityQueue how to order things.
    private val cmp = Comparator<Pair<Int, String>> { a, b ->
    
        // First, we compare the ratings (the 'first' item in our Pair).
        // 'b.first.compareTo(a.first)' means we want to sort from highest rating to lowest (descending).
        val rCmp = b.first.compareTo(a.first)

        // If the ratings are different, we use the rating comparison result.
        // If the ratings are the same (rCmp is 0), we compare the food names alphabetically (ascending).
        if (rCmp != 0) rCmp else a.second.compareTo(b.second)
    }

    // This is the core of our system. It's a map where each cuisine has its own PriorityQueue.
    // A PriorityQueue automatically keeps the "best" item at the top, based on our 'cmp' rule.
    private val cuisineHeaps = HashMap<String, PriorityQueue<Pair<Int, String>>>()

    // This is the 'init' block. It runs automatically when a FoodRatings object is created.
    // It's like the setup phase, where we fill all our maps and queues with the initial data.
    init {
        // We loop through all the food items provided.
        for (i in foods.indices) {
            val f = foods[i] // Get the food name.
            val c = cuisines[i] // Get the cuisine.
            val r = ratings[i] // Get the rating.
            
            // We fill our maps with this information.
            foodToCuisine[f] = c
            foodToRating[f] = r
            
            // We get or create a PriorityQueue for this food's cuisine.
            // 'getOrPut' is a great function: it gets the queue if it exists, or creates a new one (using our 'cmp' rule) if it doesn't.
            val pq = cuisineHeaps.getOrPut(c) { PriorityQueue(cmp) }
            
            // We add the food and its rating to the queue as a pair.
            pq.add(Pair(r, f))
        }
    }


    // This function changes the rating for a given food.
    fun changeRating(food: String, newRating: Int) {
        // We get the food's cuisine from our map. '?: return' means "if we can't find the food, just stop here."
        val cuisine = foodToCuisine[food] ?: return

        // We update the food's rating in our main rating map.
        foodToRating[food] = newRating

        // We add the food with its *new* rating to the correct PriorityQueue.
        // We don't remove the old rating from the queue. We'll deal with that later.
        cuisineHeaps[cuisine]!!.add(Pair(newRating, food))
        
    }

    // This function finds the highest-rated food for a specific cuisine.
    fun highestRated(cuisine: String): String {
        // We get the PriorityQueue for the requested cuisine.
        // '?: throw ...' means "if we can't find the cuisine, something is wrong, so we throw an error."
        val pq = cuisineHeaps[cuisine] ?: throw IllegalArgumentException("Unknown cuisine")

        // We enter an infinite loop. We'll exit this loop with a 'return' statement when we find our answer.
        while (true) {
            // We look at the top item in the queue with 'peek()'. It's the highest-rated item according to the queue.
            // '?: throw ...' handles the case where the queue is empty.
            val top = pq.peek() ?: throw IllegalStateException("No food for cuisine")

            // We get the *current* rating for this food from our 'foodToRating' map.
            val currentRating = foodToRating[top.second]!!

            // We compare the rating from the queue ('top.first') to the food's current rating.
            // If they match, it means this is a valid, up-to-date entry.
            if (top.first == currentRating) {
                // We've found the highest-rated food! We return its name.
                return top.second
            } else {
                // If the ratings don't match, it means this entry is 'stale' (it's an old rating from before a 'changeRating' call).
                // We remove it from the queue with 'poll()'. The loop will then check the next item.
                pq.poll()
            }
        }
    }

}


// ---------------------- Example usage ----------------------
fun main() {
    val foods = arrayOf("kimchi", "miso", "sushi", "moussaka", "ramen", "bulgogi")
    val cuisines = arrayOf("korean", "japanese", "japanese", "greek", "japanese", "korean")
    val ratings = intArrayOf(9, 12, 8, 15, 14, 7)

    val fr = FoodRatings(foods, cuisines, ratings)

    println(fr.highestRated("korean"))   // -> "kimchi" (9 vs bulgogi 7)
    println(fr.highestRated("japanese")) // -> "ramen" (14 is highest)

    fr.changeRating("sushi", 16)         // sushi becomes 16
    println(fr.highestRated("japanese")) // -> "sushi" (16 now highest)

    fr.changeRating("kimchi", 3)
    println(fr.highestRated("korean"))   // -> "bulgogi" (7 now higher than kimchi 3)
}