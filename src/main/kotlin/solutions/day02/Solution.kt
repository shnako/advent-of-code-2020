package solutions.day02

import solutions.GenericSolution
import util.readLines
import java.io.File

/**
 * Simple solution which parses the input to PasswordPolicy objects and then validates them.
 */
class Solution : GenericSolution {
    private fun convertLineToPasswordPolicy(line: String): PasswordPolicy {
        val components = line.split(Regex("[^\\w']+"))
        return PasswordPolicy(components[0].toInt(), components[1].toInt(), components[2].single(), components[3])
    }

    private fun parseInput(inputFile: File): List<PasswordPolicy> {
        val inputLines = readLines(inputFile)
        return inputLines.map { convertLineToPasswordPolicy(it) }
    }

    private fun isValidPasswordPolicyPart1(passwordPolicy: PasswordPolicy): Boolean {
        val occurrences = passwordPolicy.password.count { c -> c == passwordPolicy.letter }
        return occurrences >= passwordPolicy.v1 && occurrences <= passwordPolicy.v2
    }

    private fun isValidPasswordPolicyPart2(passwordPolicy: PasswordPolicy): Boolean {
        val charAtPos1 = passwordPolicy.password[passwordPolicy.v1 - 1]
        val charAtPos2 = passwordPolicy.password[passwordPolicy.v2 - 1]

        if (charAtPos1 == charAtPos2) {
            return false
        }

        return charAtPos1 == passwordPolicy.letter || charAtPos2 == passwordPolicy.letter
    }

    override fun runPart1(inputFile: File): String {
        val input = parseInput(inputFile)
        return input.count { isValidPasswordPolicyPart1(it) }.toString()
    }

    override fun runPart2(inputFile: File): String {
        val input = parseInput(inputFile)
        return input.count { isValidPasswordPolicyPart2(it) }.toString()
    }

    data class PasswordPolicy(
        val v1: Int,
        val v2: Int,
        val letter: Char,
        val password: String
    )
}
