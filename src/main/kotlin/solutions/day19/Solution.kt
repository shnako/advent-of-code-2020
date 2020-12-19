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

    private fun parseRules(inputText: String): Map<Int, Rule> {
        return inputText
            .split("\n\n")[0]
            .split("\n")
            .filter { StringUtils.isNotBlank(it) }
            .map { parseRule(it) }
            .map { it.number to it }
            .toMap()
    }

    private fun parseMessages(inputText: String): List<String> {
        return inputText
            .split("\n\n")[1]
            .split("\n")
            .filter { StringUtils.isNotBlank(it) }
    }

    private fun buildRegexBasedOnRules(rule: Rule, rules: Map<Int, Rule>): String {
        if (rule.character != null) {
            return rule.character.toString()
        }

        val firstPart = rule.subRules!!
            .joinToString(separator = "") { buildRegexBasedOnRules(rules[it]!!, rules) }

        return if (rule.orSubRules == null) {
            firstPart
        } else {
            val secondPart = rule.orSubRules
                .joinToString(separator = "") { buildRegexBasedOnRules(rules[it]!!, rules) }
            "($firstPart|$secondPart)"
        }
    }

    override fun runPart1(inputFile: File): String {
        val inputText = readFileAsString(inputFile)
        val rules = parseRules(inputText)
        val messages = parseMessages(inputText)

        val regexStr = buildRegexBasedOnRules(rules[0]!!, rules)
        println("Regex string based on rules: $regexStr")
        val regex = Regex(regexStr)

        return messages
            .filter { regex.matches(it) }
            .count()
            .toString()
    }

    override fun runPart2(inputFile: File): String {
        val inputText = readFileAsString(inputFile)
        val rules = parseRules(inputText)
        val messages = parseMessages(inputText)
        TODO("Not yet implemented")
    }
}
