package solutions.day04

import org.apache.commons.lang3.StringUtils
import solutions.GenericSolution
import solutions.day04.FieldValidators.Companion.isValidByr
import solutions.day04.FieldValidators.Companion.isValidEcl
import solutions.day04.FieldValidators.Companion.isValidEyr
import solutions.day04.FieldValidators.Companion.isValidHcl
import solutions.day04.FieldValidators.Companion.isValidHgt
import solutions.day04.FieldValidators.Companion.isValidIyr
import solutions.day04.FieldValidators.Companion.isValidPid
import util.readFileAsString
import java.io.File

class Solution : GenericSolution {
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

    private fun isValidPassportPart1(passport: Map<String, String>): Boolean {
        return when (passport.size) {
            8 -> true
            7 -> !passport.containsKey("cid")
            else -> false
        }
    }

    private fun isValidPassportPart2(passport: Map<String, String>): Boolean {
        if (passport.size < 7) {
            return false
        }

        if (passport.size == 7 && passport.containsKey("cid")) {
            return false
        }

        return isValidByr(passport)
                && isValidIyr(passport)
                && isValidEyr(passport)
                && isValidHgt(passport)
                && isValidHcl(passport)
                && isValidEcl(passport)
                && isValidPid(passport)
    }

    override fun runPart1(inputFile: File): String {
        val passports = parseInput(inputFile)
        return passports.count { isValidPassportPart1(it) }.toString()
    }

    override fun runPart2(inputFile: File): String {
        val passports = parseInput(inputFile)
        return passports.count { isValidPassportPart2(it) }.toString()
    }
}
