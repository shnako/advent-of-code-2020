package solutions.day22

import org.apache.commons.lang3.StringUtils
import solutions.GenericSolution
import util.readFileAsString
import java.io.File
import java.util.*

data class Game(
    val p1Deck: LinkedList<Int>,
    val p2Deck: LinkedList<Int>,
)

class Solution : GenericSolution {
    private fun parseGame(inputFile: File): Game {
        val decks = readFileAsString(inputFile)
            .split("\n\n")
            .map { it.split("\n") }
            .map {
                it.subList(1, it.size)
                    .filter { card -> StringUtils.isNotBlank(card) }
                    .map { card -> card.toInt() }
            }

        return Game(
            LinkedList(decks[0]),
            LinkedList(decks[1]),
        )
    }

    private fun playRound(game: Game) {
        val p1Card = game.p1Deck.pop()
        val p2Card = game.p2Deck.pop()

        if (p1Card > p2Card) {
            game.p1Deck.addLast(p1Card)
            game.p1Deck.addLast(p2Card)
        } else {
            game.p2Deck.addLast(p2Card)
            game.p2Deck.addLast(p1Card)
        }
    }

    private fun playGame(game: Game) {
        while (game.p1Deck.isNotEmpty() && game.p2Deck.isNotEmpty()) {
            playRound(game)
        }
    }


    override fun runPart1(inputFile: File): String {
        val game = parseGame(inputFile)
        playGame(game)

        val winningDeck = if (game.p1Deck.isNotEmpty()) game.p1Deck else game.p2Deck
        var score = 0L
        for (i in winningDeck.size downTo 1) {
            score += winningDeck.pop() * i
        }

        return score.toString()
    }

    override fun runPart2(inputFile: File): String {
        TODO("Not yet implemented")
    }
}
