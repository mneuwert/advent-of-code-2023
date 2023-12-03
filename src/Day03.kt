data class PartNumber(val value: Int, val range: IntRange, val lineIndex: Int)

data class Symbol(val value:Char, val range: IntRange, val lineIndex: Int)

data class Gear(val firstPart: PartNumber, val secondPart: PartNumber) {
    fun ratio(): Int {
        return firstPart.value * secondPart.value
    }
}

fun main() {

    fun processInput(input: List<String>): Pair<List<PartNumber>, List<Symbol>> {
        val parts = mutableListOf<PartNumber>()
        val symbols = mutableListOf<Symbol>()
        val numberRegex = Regex("\\d+")
        val symbolRegex = Regex("[^\\d.]")

        for (lineIndex in 0..<input.size) {
            val line = input[lineIndex]
            val numbers = numberRegex.findAll(line).map { PartNumber(it.value.toInt(), range = it.range, lineIndex = lineIndex) }.toList()
            val symbolsInLine = symbolRegex.findAll(line).map { Symbol(it.value.first(), range = it.range, lineIndex = lineIndex) }.toList()

            parts.addAll(numbers)
            symbols.addAll(symbolsInLine)
        }
        return Pair(parts, symbols)
    }

    fun part1(input: List<String>): Int {

        val processedInput = processInput(input)
        val parts = processedInput.first
        val symbols = processedInput.second

        val validParts = mutableListOf<PartNumber>()

        for (part in parts) {

            // Get a position of number substring in the line
            var leftIndex = part.range.first
            var rightIndex = part.range.last

            // Get adjacent chars in the same line
            val lineRange = IntRange(part.lineIndex-1, part.lineIndex+1)
            val inLineRange = IntRange(leftIndex-1, rightIndex+1)

            symbols.count { it.lineIndex in lineRange && it.range.first in inLineRange }.takeIf { it == 1}?.let {
                validParts.add(part)
            }
        }

        return validParts.sumOf { it.value }
    }

    fun part2(input: List<String>): Int {
        val processedInput = processInput(input)
        val parts = processedInput.first
        val gearSymbols = processedInput.second.filter { it.value == '*' }

        val gears = mutableListOf<Gear>()

        for (gearSymbol in gearSymbols) {
            val lineRange = IntRange(gearSymbol.lineIndex-1, gearSymbol.lineIndex+1)
            val inLineRange = IntRange(gearSymbol.range.first-1, gearSymbol.range.first+1)

            val adjacentParts = parts.filter { it.lineIndex in lineRange && inLineRange.intersect(it.range).isNotEmpty() }
            if (adjacentParts.size == 2) {
                gears.add(Gear(adjacentParts[0], adjacentParts[1]))
            }
        }

        return gears.sumOf { it.ratio() }
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}