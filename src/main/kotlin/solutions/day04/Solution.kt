package solutions.day04

import org.apache.commons.lang3.StringUtils
import solutions.SolutionInterface
import util.readFileAsString
import java.io.File

class Solution : SolutionInterface {
    private fun parseInput(inputFile: File): List<Map<String, String>> {
        val inputText = readFileAsString(inputFile)
        val passports = inputText.split("\n\n")
        return passports.map { parsePassport(it) }
    }

    private fun parsePassport(passportText: String): Map<String, String> {
        val components = passportText.split(Regex("\\s+"))
        val passportComponents: MutableMap<String, String> = mutableMapOf()
        for (component in components) {
            if (StringUtils.isBlank(component)) {
                continue
            }

            val fieldComponents = component.split(":")
            passportComponents[fieldComponents[0]] = fieldComponents[1]
        }

        return passportComponents
    }

    private fun isValidPassport(passport: Map<String, String>): Boolean {
        return when (passport.size) {
            8 -> true
            7 -> !passport.containsKey("cid")
            else -> false
        }
    }

    override fun runPart1(inputFile: File): String {
        val passports = parseInput(inputFile)
        return passports.count { isValidPassport(it) }.toString()
    }

    override fun runPart2(inputFile: File): String {
        TODO("Not yet implemented")
    }
}
