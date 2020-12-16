package solutions.day16

import org.apache.commons.lang3.StringUtils
import solutions.GenericSolution
import util.readFileAsString
import java.io.File

data class Rule(
    val rule: String,
    val ranges: List<IntRange>
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

    override fun runPart2(inputFile: File): String {
        val notes = parseInput(inputFile)
        TODO("Not yet implemented")
    }
}
