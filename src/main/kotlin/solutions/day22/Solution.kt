package solutions.day22

import org.apache.commons.lang3.StringUtils
import solutions.GenericSolution
import util.readFileAsString
import java.io.File
import java.util.*

data class Game(
    val number: Int,
    val p1Deck: LinkedList<Int>,
    val p2Deck: LinkedList<Int>,
    val p1PreviousDecks: MutableSet<Int> = mutableSetOf(),
    val p2PreviousDecks: MutableSet<Int> = mutableSetOf(),
)

@SuppressWarnings("kotlin:S125") // I want to keep the commented print statement.
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
            1,
            LinkedList(decks[0]),
            LinkedList(decks[1]),
        )
    }

    private fun playCombatRound(game: Game) {
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

    private fun playCombatGame(game: Game) {
        while (game.p1Deck.isNotEmpty() && game.p2Deck.isNotEmpty()) {
            playCombatRound(game)
        }
    }

    private fun calculateScore(winningDeck: LinkedList<Int>): Long {
        var score = 0L
        for (i in winningDeck.size downTo 1) {
            score += winningDeck.pop() * i
        }

        return score
    }

    override fun runPart1(inputFile: File): String {
        val game = parseGame(inputFile)
        playCombatGame(game)
        val winningDeck = if (game.p1Deck.isNotEmpty()) game.p1Deck else game.p2Deck
        return calculateScore(winningDeck).toString()
    }

    private fun playRecursiveCombatRound(game: Game, p1Card: Int, p2Card: Int): Int {
        return if (game.p1Deck.size >= p1Card && game.p2Deck.size >= p2Card) {
            val subGame = Game(
                game.number + 1,
                LinkedList(game.p1Deck.subList(0, p1Card)),
                LinkedList(game.p2Deck.subList(0, p2Card)),
            )
            playRecursiveCombatGame(subGame)
        } else {
            if (p1Card > p2Card) 1 else 2
        }
    }

    private fun playRecursiveCombatGame(game: Game): Int {
        while (game.p1Deck.isNotEmpty() && game.p2Deck.isNotEmpty()) {
            if (game.p1PreviousDecks.contains(game.p1Deck.hashCode())
                || game.p2PreviousDecks.contains(game.p2Deck.hashCode())
            ) {
                return 1
            }

            game.p1PreviousDecks.add(game.p1Deck.hashCode())
            game.p2PreviousDecks.add(game.p2Deck.hashCode())

            val p1Card = game.p1Deck.pop()
            val p2Card = game.p2Deck.pop()
            val winner = playRecursiveCombatRound(game, p1Card, p2Card)
//            println("Player $winner wins game ${game.number}'s round ${game.p1PreviousDecks.size}.")

            if (winner == 1) {
                game.p1Deck.addLast(p1Card)
                game.p1Deck.addLast(p2Card)
            } else {
                game.p2Deck.addLast(p2Card)
                game.p2Deck.addLast(p1Card)
            }
        }

        return if (game.p2Deck.isEmpty()) 1 else 2
    }

    override fun runPart2(inputFile: File): String {
        val game = parseGame(inputFile)
        val winner = playRecursiveCombatGame(game)
        val winningDeck = if (winner == 1) game.p1Deck else game.p2Deck
        return calculateScore(winningDeck).toString()
    }
}
