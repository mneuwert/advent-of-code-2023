data class Card(val index: Int, val winningNumbers:Set<Int>, val actualNumbers:Set<Int>) {
    fun matches(): Int {
        return winningNumbers.intersect(actualNumbers).size
    }

    fun points(): Int {
        return if(matches() > 1) 1 shl (matches() - 1) else matches()
    }

    override fun toString(): String {
        return "Card(index=$index, matches=${matches()}, points=${points()})"
    }
}
fun main() {

    fun parseCards(input: List<String>): List<Card> {
        val cards = mutableListOf<Card>()

        val numberRegex = Regex("\\d+")
        for (line in input) {
            val parts = line.split(":")

            // Parse card number from the first part using regex
            val cardNumber = numberRegex.find(parts[0])!!.value.toInt()

            val numberParts = parts[1].split("|")
            val winningNumbers = numberRegex.findAll(numberParts[0]).map { it.value.toInt() }.toSet()
            val actualNumbers = numberRegex.findAll(numberParts[1]).map { it.value.toInt() }.toSet()

            cards.add(Card(cardNumber, winningNumbers, actualNumbers))
        }
        return cards
    }

    fun part1(input: List<String>): Int {
        val cards = parseCards(input)
        return cards.sumOf { it.points() }
    }

    fun part2(input: List<String>): Int {
        val cards = parseCards(input)
        val wonCards = IntArray(cards.size)
        wonCards.fill(1)

        // Find first winning card which has one or more matches
        // Means if first card with index 1 has 2 matches, then it won cards with index 2 and 3
        // If card 2 wins 3 cards, then it won cards with index 3, 4, 5
        // Repeat until no more cards are won
        // Count how many cards are won
        // Return the count
        for (card in cards.withIndex()) {
            for (i in card.value.index..card.index + card.value.matches() ) {
                wonCards[i] += wonCards[card.index]
            }
        }

        return wonCards.sum()
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}