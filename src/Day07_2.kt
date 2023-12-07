import java.lang.Math.max

data class Hand2(val cards: List<Char>, val bid:Long, val rank: Int): Comparable<Hand> {
    companion object {
        val validCards = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')
        fun strength(card: Char): Int {
            return validCards.size - validCards.indexOf(card)
        }
        fun parse(input: List<String>): List<Hand> {
            val hands = mutableListOf<Hand>()
            input.forEach {
                it.split(' ').let { parts ->
                    val cards = parts[0].toCharArray().toList()
                    val bid = parts[1].toLong()

                    // Strongest to weakest:
                    // Five of a kind AAAAA -> 7
                    // Four of a kind AA8AA -> 6
                    // Full house: 23332 (3+2) -> 5
                    // Three of a kind TTT98 -> 4
                    // Two pairs 23432 -> 3
                    // One pair A23A4 -> 2
                    // High card 23456 (all different) -> 1
                    var rank = 0
                    var pairs = 0
                    var triples = 0
                    var fours = 0
                    var fives = 0
                    var jokers = 0

                    // Find groups of 5.4.3 and 2
                    var cardsMap = cards.groupBy { it }.toMutableMap()
                    jokers = cardsMap['J']?.size ?: 0
                    cardsMap.remove('J')

                    cardsMap.forEach {
                        when( max(it.value.size, it.value.size + jokers) ) {
                            5 -> fives++
                            4 -> fours++
                            2 -> pairs++
                            3 -> triples++
                        }
                    }

                    if (fives > 0) {
                        rank = 7
                    } else if (fours > 0) {
                        rank = 6
                    }

                    if (rank == 0) {
                        // Now take a closer look at combinations of tuples and triples
                        if (triples > 0) {
                            if (pairs > 0) {
                                // full house
                                rank = 5
                            } else {
                                // three of a kind
                                rank = 4
                            }
                        } else {
                            when (pairs) {
                                2 -> rank = 3
                                1 -> rank = 2
                                else -> rank = 1
                            }
                        }
                    }

                    hands.add(Hand(cards, bid, rank))
                }
            }
            return hands
        }
    }

    override fun compareTo(other: Hand): Int {
        if (rank != other.rank) {
            return rank.compareTo(other.rank)
        } else {
            // compare cards (first non equal card in the hand is relevant)
            for (i in 0 until cards.size) {
                if (cards[i] != other.cards[i]) {
                    return strength(cards[i]).compareTo(strength(other.cards[i]))
                }
            }
        }
        return 0
    }
}

fun main() {
    val input = readInput("Day07")
    val hands = Hand2.parse(input).sorted()
    hands
        .mapIndexed { i, h -> h.bid * (i + 1) }
        .sum()
        .println()
}