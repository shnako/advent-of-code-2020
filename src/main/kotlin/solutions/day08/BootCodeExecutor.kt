package solutions.day08

import org.apache.commons.lang3.StringUtils

class BootCodeExecutor {
    data class Instruction(
        var operation: String,
        var argument: Int,
        var executed: Boolean
    )

    private var accumulator = 0
    private var operationIndex = 0
    val instructions: List<Instruction>

    constructor(instructionsText: Collection<String>) {
        instructions = parseInstructionsText(instructionsText)
    }

    constructor(instructions: List<Instruction>) {
        this.instructions = instructions
        this.instructions.forEach { instruction -> instruction.executed = false }
    }

    private fun parseInstructionText(instructionText: String): Instruction {
        val components = instructionText.split(" ")
        return Instruction(
            operation = components[0],
            argument = components[1].toInt(),
            executed = false
        )
    }

    private fun parseInstructionsText(instructionsText: Collection<String>): List<Instruction> {
        return instructionsText
            .filter { StringUtils.isNotBlank(it) }
            .map { parseInstructionText(it) }
    }

    private fun executeInstruction(instruction: Instruction) {
        when (instruction.operation) {
            "acc" -> accumulator += instruction.argument
            "jmp" -> operationIndex += instruction.argument - 1
            // "nop" -> Do Nothing
        }
        instruction.executed = true
    }

    fun execute(): Int {
        while (operationIndex < instructions.size) {
            val instruction = instructions[operationIndex]
            if (instruction.executed) {
                // Infinite loop detected
                break
            }
            executeInstruction(instruction)
            operationIndex++
        }

        return accumulator
    }

    fun hasInfiniteLoop(): Boolean {
        while (operationIndex < instructions.size) {
            val instruction = instructions[operationIndex]
            if (instruction.executed) {
                // Infinite loop detected
                return true
            }
            executeInstruction(instruction)
            operationIndex++
        }

        return false
    }
}
