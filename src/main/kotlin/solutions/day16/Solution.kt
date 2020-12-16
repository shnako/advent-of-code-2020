package solutions.day16

import org.apache.commons.lang3.StringUtils
import solutions.GenericSolution
import util.readFileAsString
import java.io.File
import kotlin.system.measureTimeMillis

data class Rule(
    val field: String,
    val ranges: List<IntRange>,
    var validPositions: List<Int> = listOf(),
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

    private fun getValidFieldPositionsOnTicket(rule: Rule, validTickets: List<Ticket>): List<Int> {
        val fields = validTickets[0].values.size

        val validPositions = mutableListOf<Int>()
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

    private fun determineFieldPositions(rule: Int, rules: List<Rule>, usedPositions: MutableSet<Int>) {
        if (rule == rules.size) {
            return
        }

        for (candidatePosition in rules[rule].validPositions) {
            if (usedPositions.contains(candidatePosition)) {
                continue
            }

            rules[rule].position = candidatePosition

            usedPositions.add(candidatePosition)
            determineFieldPositions(rule + 1, rules, usedPositions)
            usedPositions.remove(candidatePosition)

            if (rules.last().position != -1) {
                return
            }
        }
    }

    override fun runPart2(inputFile: File): String {
        val notes = parseInput(inputFile)

        val validTickets = getValidTickets(notes)

        val t1 = measureTimeMillis { determineValidFieldPositions(notes.rules, validTickets) }
        println("t1 took $t1 ms")
        val t2 = measureTimeMillis { determineFieldPositions(0, notes.rules, mutableSetOf()) }
        println("t2 took $t2 ms")

        val relevantTicketValues = notes.rules
            .filter { it.field.startsWith("departure") }
            .map { it.position }
            .map { notes.myTicket.values[it] }

        var result = 1L
        relevantTicketValues.forEach { result *= it }

        return result.toString()
    }
}
