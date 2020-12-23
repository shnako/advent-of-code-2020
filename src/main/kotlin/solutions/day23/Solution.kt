package solutions.day23

import solutions.GenericSolution
import util.readFileAsString
import java.io.File

data class Game(
    val cups: ArrayDeque<Int>,
    val min: Int,
    val max: Int,
)

class Solution : GenericSolution {
    private fun readCups(inputFile: File): Game {
        val cups = readFileAsString(inputFile)
            .toCharArray()
            .dropLast(1)
            .map { it.toInt() - 48 }

        return Game(
            ArrayDeque(cups),
            cups.minOrNull()!!,
            cups.maxOrNull()!!,
        )
    }

    private fun findValidLabel(label: Int, game: Game): Int {
        for (i in label - 1 downTo game.min) {
            if (game.cups.contains(i)) {
                return i
            }
        }

        return -1
    }

    private fun playMove(game: Game) {
        val current = game.cups.removeFirst()
        val nextCup1 = game.cups.removeFirst()
        val nextCup2 = game.cups.removeFirst()
        val nextCup3 = game.cups.removeFirst()


        var destinationCupLabel = findValidLabel(current, game)

        if (destinationCupLabel == -1) {
            destinationCupLabel = findValidLabel(game.max + 1, game)
        }

        val insertIndex = game.cups.indexOf(destinationCupLabel) + 1
        game.cups.add(insertIndex, nextCup3)
        game.cups.add(insertIndex, nextCup2)
        game.cups.add(insertIndex, nextCup1)

        game.cups.addLast(current)
    }

    private fun getResult(game: Game): Long {
        while (game.cups.first() != 1) {
            game.cups.addLast(game.cups.removeFirst())
        }

        game.cups.removeFirst()

        var multiplier = 1L
        var result = 0L
        while (game.cups.isNotEmpty()) {
            result += multiplier * game.cups.removeLast()
            multiplier *= 10
        }

        return result
    }

    override fun runPart1(inputFile: File): String {
        val game = readCups(inputFile)
        for (i in 1..100) {
            playMove(game)
        }
        return getResult(game).toString()
    }

    override fun runPart2(inputFile: File): String {
        val game = readCups(inputFile)
        for (i in game.max + 1..1000000) {
            game.cups.addLast(i)
        }

        for (i in 1..10000000) {
            playMove(game)
            if (i % 100 == 0) {
                println(i)
            }
        }

        val index1 = game.cups.indexOf(1)
        return (game.cups[index1 + 1] * game.cups[index1 + 2]).toString()
    }
}
