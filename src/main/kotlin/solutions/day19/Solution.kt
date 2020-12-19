package solutions.day19

import org.apache.commons.lang3.StringUtils
import solutions.GenericSolution
import util.readFileAsString
import java.io.File

data class Rule(
    val number: Int,
    val subRules: List<Int>?,
    val orSubRules: List<Int>?,
    val character: Char?
)

/**
 * This solution recursively builds a Regex string based on the rules
 * and then counts how many messages match it.
 *
 * For part 2 I simply stop recursing while building the Regex string after a number of steps.
 */
class Solution : GenericSolution {
    private fun parseRule(ruleText: String): Rule {
        val numberAndRuleTextComponents = ruleText.split(":")
        val number = numberAndRuleTextComponents[0].toInt()

        val ruleComponents = numberAndRuleTextComponents[1].split("|")

        var character: Char? = null
        var subRules: List<Int>? = null
        if (ruleComponents[0].contains('"')) {
            character = ruleComponents[0].trim()[1]
        } else {
            subRules = ruleComponents[0]
                .split(" ")
                .filter { StringUtils.isNotBlank(it) }
                .map { it.toInt() }
        }

        var orSubRules: List<Int>? = null
        if (ruleComponents.size == 2) {
            orSubRules = ruleComponents[1]
                .split(" ")
                .filter { StringUtils.isNotBlank(it) }
                .map { it.toInt() }
        }

        return Rule(
            number,
            subRules,
            orSubRules,
            character
        )
    }

    private fun parseRules(inputText: String): MutableMap<Int, Rule> {
        return inputText
            .split("\n\n")[0]
            .split("\n")
            .filter { StringUtils.isNotBlank(it) }
            .map { parseRule(it) }
            .map { it.number to it }
            .toMap()
            .toMutableMap()
    }

    private fun parseMessages(inputText: String): List<String> {
        return inputText
            .split("\n\n")[1]
            .split("\n")
            .filter { StringUtils.isNotBlank(it) }
    }

    private fun buildRegexBasedOnRules(depth: Int, rule: Rule, rules: Map<Int, Rule>): String {
        if (rule.character != null) {
            return rule.character.toString()
        }

        val firstPart = rule.subRules!!
            .joinToString(separator = "") { buildRegexBasedOnRules(depth + 1, rules[it]!!, rules) }

        // Only go down 16 levels to avoid recursing infinitely.
        // This is sufficient for my input.
        if (depth > 16) {
            return firstPart
        }

        return if (rule.orSubRules == null) {
            firstPart
        } else {
            val secondPart = rule.orSubRules
                .joinToString(separator = "") { buildRegexBasedOnRules(depth + 1, rules[it]!!, rules) }
            "($firstPart|$secondPart)"
        }
    }

    private fun countMessagesMatchingRules(messages: List<String>, rules: Map<Int, Rule>): Int {
        val regexStr = buildRegexBasedOnRules(0, rules[0]!!, rules)
        println("Regex string based on rules: $regexStr")
        val regex = Regex(regexStr)

        return messages
            .filter { regex.matches(it) }
            .count()
    }

    override fun runPart1(inputFile: File): String {
        val inputText = readFileAsString(inputFile)
        val messages = parseMessages(inputText)
        val rules = parseRules(inputText)

        return countMessagesMatchingRules(messages, rules).toString()
    }

    private fun replaceRules(rules: MutableMap<Int, Rule>) {
        val newRule8 = "8: 42 | 42 8"
        val newRule11 = "11: 42 31 | 42 11 31"

        rules[8] = parseRule(newRule8)
        rules[11] = parseRule(newRule11)
    }

    override fun runPart2(inputFile: File): String {
        val inputText = readFileAsString(inputFile)
        val messages = parseMessages(inputText)
        val rules = parseRules(inputText)
        replaceRules(rules)

        return countMessagesMatchingRules(messages, rules).toString()
    }
}
