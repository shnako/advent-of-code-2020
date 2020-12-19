package solutions.day18

import org.apache.commons.lang3.StringUtils
import solutions.GenericSolution
import util.readLines
import java.io.File
import java.util.*

/**
 * This solution transforms the infix notation input to the Reverse Polish Notation and then calculates it.
 *
 * https://en.wikipedia.org/wiki/Reverse_Polish_notation
 *
 * The difference between part 1 and 2 is that for part 1 we don't consider precedence.
 */
class Solution : GenericSolution {
    /**
     * Implementation of the Shunting-Yard algorithm.
     *
     * https://en.wikipedia.org/wiki/Shunting-yard_algorithm#The_algorithm_in_detail
     */
    @SuppressWarnings("kotlin:S3776")
    private fun convertToRpn(infixExpression: String, applyPrecedence: Boolean): Stack<Any> {
        val infixTokens = infixExpression
            .split("")
            .filter { StringUtils.isNotBlank(it) }
            .map { it }

        val outputStack = Stack<Any>()
        val operatorStack = Stack<String>()
        for (token in infixTokens) {
            if (token == "+" || token == "*") {
                while (!operatorStack.isEmpty()
                    && (!applyPrecedence || token == "*" || operatorStack.peek() == "+")
                    && operatorStack.peek() != "("
                ) {
                    val operator = operatorStack.pop()
                    outputStack.push(operator)
                }
                operatorStack.push(token)
            } else if (token == "(") {
                operatorStack.push(token)
            } else if (token == ")") {
                while (true) {
                    val operator = operatorStack.pop()
                    if (operator == "(") {
                        break
                    }
                    outputStack.push(operator)
                }
            } else { // Number
                outputStack.push(token.toLong())
            }
        }

        while (!operatorStack.isEmpty()) {
            val operator = operatorStack.pop()
            outputStack.push(operator)
        }

        println(infixExpression)
        println("->")
        println(outputStack.toString())
        println()

        return outputStack
    }

    private fun calculateRpnExpression(rpnStack: Stack<Any>): Long {
        rpnStack.reverse()
        val calculationStack = Stack<Long>()
        while (!rpnStack.isEmpty()) {
            val token = rpnStack.pop()
            if (token is Long) { // number
                calculationStack.push(token)
            } else if (token is String) { // operator
                val operand1 = calculationStack.pop()
                val operand2 = calculationStack.pop()
                if (token == "+") {
                    calculationStack.push(operand1 + operand2)
                } else if (token == "*") {
                    calculationStack.push(operand1 * operand2)
                }
            }
        }

        return calculationStack.pop()
    }

    override fun runPart1(inputFile: File): String {
        val infixExpressions = readLines(inputFile)
        val rpnExpressions = infixExpressions.map { convertToRpn(it, false) }

        return rpnExpressions
            .map { calculateRpnExpression(it) }
            .sum()
            .toString()
    }

    override fun runPart2(inputFile: File): String {
        val infixExpressions = readLines(inputFile)
        val rpnExpressions = infixExpressions.map { convertToRpn(it, true) }

        return rpnExpressions
            .map { calculateRpnExpression(it) }
            .sum()
            .toString()
    }
}
