package solutions.day21

import solutions.GenericSolution
import util.readLines
import java.io.File

data class Food(
    val ingredients: List<String>,
    val allergens: List<String>,
)

/**
 * We can determine what ingredient contains which allergen by repeatedly for each allergen not yet identified:
 * 1. finding all foods declaring that allergen
 * 2. finding all common ingredients of those
 * 3. filtering out the ingredients we already know to contain allergens
 * 4. if there's only one ingredient left, then that's the one containing the allergen
 *
 * Repeating this a few times will eventually identify all the ingredients containing allergens.
 *
 * Once this is done, finding out the solutions to part 1 and 2 is trivial.
 */
class Solution : GenericSolution {
    private fun parseFood(foodText: String): Food {
        val foodComponents = foodText
            .split(" (contains ")

        return Food(
            foodComponents[0].split(" "),
            foodComponents[1]
                .removeSuffix(")")
                .split(", ")
        )
    }

    private fun parseFoods(inputFile: File): List<Food> {
        return readLines(inputFile)
            .map { parseFood(it) }
    }

    private fun extractAllergens(foods: List<Food>): MutableSet<String> {
        return foods
            .map { it.allergens }
            .flatten()
            .toMutableSet()
    }

    private fun findPotentialIngredientsContainingAllergen(
        allergen: String,
        foods: List<Food>,
        ingredientsKnownToContainAllergens: Set<String>
    ): Set<String> {
        return foods
            .asSequence()
            .filter { it.allergens.contains(allergen) }
            .map { it.ingredients }
            .reduce { acc, it -> acc.intersect(it).toList() }
            .filter { !ingredientsKnownToContainAllergens.contains(it) }
            .toSet()
    }

    private fun findIngredientsContainingAllergens(foods: List<Food>): Map<String, String> {
        var allergensNotYetIdentified = extractAllergens(foods)
        val ingredientsKnownToContainAllergens = mutableSetOf<String>()
        val ingredientsToAllergens = mutableMapOf<String, String>()

        while (allergensNotYetIdentified.isNotEmpty()) {
            for (allergen in allergensNotYetIdentified) {
                val potentialIngredients =
                    findPotentialIngredientsContainingAllergen(allergen, foods, ingredientsKnownToContainAllergens)

                if (potentialIngredients.size == 1) {
                    val ingredient = potentialIngredients.first()

                    ingredientsKnownToContainAllergens.add(ingredient)
                    ingredientsToAllergens[ingredient] = allergen
                }
            }

            allergensNotYetIdentified = allergensNotYetIdentified.minus(ingredientsToAllergens.values).toMutableSet()
        }

        return ingredientsToAllergens
    }

    override fun runPart1(inputFile: File): String {
        val foods = parseFoods(inputFile)
        val ingredientsContainingAllergens = findIngredientsContainingAllergens(foods)

        return foods
            .map { it.ingredients }
            .flatten()
            .filter { !ingredientsContainingAllergens.containsKey(it) }
            .count()
            .toString()
    }

    override fun runPart2(inputFile: File): String {
        val foods = parseFoods(inputFile)
        val ingredientsContainingAllergens = findIngredientsContainingAllergens(foods)
        val allergensToIngredients = ingredientsContainingAllergens
            .entries
            .associateBy({ it.value }) { it.key }

        return allergensToIngredients
            .keys
            .sorted()
            .map { allergensToIngredients[it] }
            .joinToString(",")
    }
}
