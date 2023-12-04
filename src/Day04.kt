data class Card(val index: Int, val winningNumbers:Set<Int>, val actualNumbers:Set<Int>) {
    fun matches(): Int {
        return winningNumbers.intersect(actualNumbers).size
    }

    fun points(): Int {
        var points = 0
        for (i in 1..matches()) {
            if (i == 1) {
                points += i
            } else {
                points *= 2
            }
        }
        return points
    }
}
fun main() {

    fun part1(input: List<String>): Int {
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

        for (card in cards) {
            println("${card} matches: ${card.matches()}, points: ${card.points()}")
        }

        return cards.sumOf { it.points() }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}