package solutions.day16

import org.apache.commons.lang3.StringUtils
import solutions.GenericSolution
import util.readFileAsString
import java.io.File

data class Rule(
    val field: String,
    val ranges: List<IntRange>,
    var validPositions: MutableSet<Int> = mutableSetOf(),
    var position: Int = -1
)

data class Ticket(
    val values: List<Int>
)

data class Notes(
    val rules: List<Rule>,
    val myTicket: Ticket,
    val nearbyTickets: List<Ticket>
)

/**
 * Part 1:
 * I rely on the observation that the values are always < 1000.
 * I therefore create a sieve of values that are valid based on the rules
 * and then just check each value in the sieve.
 *
 * Part 2:
 * I first determine all the valid positions for each field based on the rule
 * and then look for a combination of positions that satisfies all rules.
 * You can find a naive and an optimal solution for finding the combination below.
 */
class Solution : GenericSolution {
    private fun parseRule(line: String): Rule {
        val components = line.split(":")
        val rule = components[0]
        val ranges = components[1]
            .split("or")
            .map { it.trim() }
            .map { it.split("-")[0].toInt()..it.split("-")[1].toInt() }

        return Rule(rule, ranges)
    }

    private fun parseTicket(line: String): Ticket {
        return Ticket(
            line
                .split(",")
                .map { it.toInt() }
        )
    }

    private fun parseInput(inputFile: File): Notes {
        val inputText = readFileAsString(inputFile)
        val components = inputText.split("\n\n")

        val rules = components[0]
            .split("\n")
            .map { parseRule(it) }

        val myTicket = parseTicket(components[1].split("\n")[1])

        val nearbyTickets = components[2]
            .split("\n")
            .drop(1)
            .filterNot { StringUtils.isBlank(it) }
            .map { parseTicket(it) }

        return Notes(rules, myTicket, nearbyTickets)
    }

    @Suppress("SameParameterValue") // Would rather have it as a parameter.
    private fun buildValidNumberSieve(maxValue: Int, rules: List<Rule>): BooleanArray {
        val validNumberSieve = BooleanArray(maxValue) { false }

        for (rule in rules) {
            for (range in rule.ranges) {
                for (i in range) {
                    validNumberSieve[i] = true
                }
            }
        }

        return validNumberSieve
    }

    override fun runPart1(inputFile: File): String {
        val notes = parseInput(inputFile)

        val validNumberSieve = buildValidNumberSieve(1000, notes.rules)

        var scanningErrorRate = 0L

        for (ticket in notes.nearbyTickets) {
            for (value in ticket.values) {
                if (!validNumberSieve[value]) {
                    scanningErrorRate += value
                }
            }
        }

        return scanningErrorRate.toString()
    }

    private fun getValidTickets(notes: Notes): List<Ticket> {
        val validNumberSieve = buildValidNumberSieve(1000, notes.rules)

        val validTickets = mutableListOf<Ticket>()
        validTickets.add(notes.myTicket)
        for (ticket in notes.nearbyTickets) {
            var isValid = true
            for (value in ticket.values) {
                if (!validNumberSieve[value]) {
                    isValid = false
                    break
                }
            }

            if (isValid) {
                validTickets.add(ticket)
            }
        }

        return validTickets
    }

    private fun getValidFieldPositionsOnTicket(rule: Rule, validTickets: List<Ticket>): MutableSet<Int> {
        val fields = validTickets[0].values.size

        val validPositions = mutableSetOf<Int>()
        for (position in 0 until fields) {
            val isValidPosition = validTickets.all { ticket ->
                rule.ranges.filter { range -> range.contains(ticket.values[position]) }.any()
            }

            if (isValidPosition) {
                validPositions.add(position)
            }
        }

        return validPositions
    }

    private fun determineValidFieldPositions(rules: List<Rule>, tickets: List<Ticket>) {
        for (rule in rules) {
            val validFieldPositions = getValidFieldPositionsOnTicket(rule, tickets)
            rule.validPositions = validFieldPositions
        }
    }

    private var iterations = 0L

    /**
     * This uses backtracking to find a combination of field positions that satisfies all rules.
     * Runs in about 6s for part 2.
     */
    @Suppress("unused") // Keeping as alternate solution.
    private fun determineFieldPositionsNaive(rule: Int, rules: List<Rule>, usedPositions: MutableSet<Int>) {
        if (rule == rules.size) {
            return
        }

        for (candidatePosition in rules[rule].validPositions) {
            if (usedPositions.contains(candidatePosition)) {
                continue
            }

            rules[rule].position = candidatePosition
            iterations++

            usedPositions.add(candidatePosition)
            determineFieldPositionsNaive(rule + 1, rules, usedPositions)
            usedPositions.remove(candidatePosition)

            if (rules.last().position != -1) {
                return
            }
        }
    }

    /**
     * This is based on the observation that since there's only 1 accepted solution,
     * then there must always be an entry that's only valid in one position.
     * Once that's found, there must always be another that's only valid in one position.
     * Runs in <1s for part 2.
     */
    private fun determineFieldPositionsOptimal(rules: List<Rule>) {
        for (i in rules.indices) {
            val singlePossiblePositionRule = rules.find { it.validPositions.size == 1 }
            val position = singlePossiblePositionRule!!.validPositions.first()
            singlePossiblePositionRule.position = position
            for (rule in rules) {
                rule.validPositions.remove(position)
            }
        }
    }

    override fun runPart2(inputFile: File): String {
        val notes = parseInput(inputFile)

        val validTickets = getValidTickets(notes)

        determineValidFieldPositions(notes.rules, validTickets)
        determineFieldPositionsOptimal(notes.rules)

        var result = 1L
        notes.rules
            .filter { it.field.startsWith("departure") }
            .map { it.position }
            .map { notes.myTicket.values[it] }
            .forEach { result *= it }

        return result.toString()
    }
}
