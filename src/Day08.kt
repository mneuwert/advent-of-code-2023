import Node as Node

data class Node(val left: String, val right: String) {}
data class Network(val path: String, val nodes: Map<String, Node>) {
    companion object {
        fun parse(input: List<String>): Network {
            val nodes = mutableMapOf<String, Node>()
            val path = mutableListOf<String>()
            val pathRegEx = Regex("([LR]+)")

            input.forEach {
                if (it.matches(pathRegEx)) {
                    path.add(it)
                } else {
                    val name = it.substringBefore("=").trim()
                    if (name.length == 3) {
                        it.substringAfter("=").replace(")", "").replace("(", "").split(",").let {
                            nodes[name] = Node(it[0].trim(), it[1].trim())
                        }
                    }
                }
            }
            return Network(path.distinct().joinToString(""), nodes)
        }
    }

    fun stepsToFind(nodeToFind: String): Long {
        var steps: Long = 0
        var current = nodes.keys.first()
        val pathIt = CircularIterator(path.toList())

        println("Starting at $current")

        do {
            val next = pathIt.next()
            when(next) {
                'R' -> current = nodes[current]!!.right
                'L'-> current = nodes[current]!!.left
            }
            steps++
//            println("Step $steps: $next -> $current")
        } while (current != nodeToFind)

        return steps
    }
}

fun main() {

    fun part1(input: List<String>): Long {
        val network = Network.parse(input)
        return network.stepsToFind("ZZZ")
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}