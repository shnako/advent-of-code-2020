package solutions.day07

import org.apache.commons.lang3.StringUtils
import solutions.GenericSolution
import util.readLines
import java.io.File

/**
 * This solution is based on the observation that
 * these rules can be represented as a polytree starting from the bags with no contents.
 * Representing it like this reduces the problem to finding the subgraph of a node.
 */
class Solution : GenericSolution {
    private fun parseBagContents(bagLine: String): Pair<String, ArrayList<Content>> {
        val components = bagLine.split(Regex("[ ,]+"))
        val color = components[0] + " " + components[1]
        // component 2 skipped - bags
        // component 3 skipped - contain

        if (components[4] == "no") {
            // no contents so we don't care about the rest.
            return Pair(color, ArrayList())
        }

        var index = 4
        val contents = ArrayList<Content>()
        while (index < components.size) {
            val content = Content(
                components[index].toInt(),
                components[index + 1] + " " + components[index + 2]
            )
            contents.add(content)
            // skip the bags word and jump 4 lines
            index += 4
        }

        return Pair(color, contents)
    }

    private fun convertBagContentsToBagMap(bagContents: List<Pair<String, ArrayList<Content>>>): HashMap<String, Bag> {
        // Populate the rule map from the bag contents.
        val ruleMap = HashMap<String, Bag>()
        for (polytreeContentPair in bagContents) {
            val bag = Bag(polytreeContentPair.first, polytreeContentPair.second, ArrayList())
            ruleMap[bag.color] = bag
        }
        return ruleMap
    }

    private fun populateBagContainers(bagMap: HashMap<String, Bag>) {
        for (bag in bagMap.values) {
            for (content in bag.contents) {
                bagMap[content.color]!!.containers += bag
            }
        }
    }

    private fun parseInputToBagPolytreeMap(inputFile: File): Map<String, Bag> {
        val bagContents = readLines(inputFile)
            .filter { StringUtils.isNotBlank(it) }
            .map { parseBagContents(it) }

        val bagMap = convertBagContentsToBagMap(bagContents)
        populateBagContainers(bagMap)
        return bagMap
    }

    private fun populateContainerBagSet(containers: List<Bag>, subtreeBags: HashSet<Bag>) {
        subtreeBags.addAll(containers)
        for (container in containers) {
            populateContainerBagSet(container.containers, subtreeBags)
        }
    }

    override fun runPart1(inputFile: File): String {
        val bagPolytreeMap = parseInputToBagPolytreeMap(inputFile)
        val containerBagSet = HashSet<Bag>()
        populateContainerBagSet(bagPolytreeMap["shiny gold"]!!.containers, containerBagSet)
        return containerBagSet.count().toString()
    }

    private fun getNumberOfBoxContents(contents: ArrayList<Content>, containerBagSet: Map<String, Bag>): Int {
        return contents
            .map { content ->
                content.number * getNumberOfBoxContents(
                    containerBagSet[content.color]!!.contents,
                    containerBagSet
                )
            }
            .sum() + 1
    }

    override fun runPart2(inputFile: File): String {
        val bagPolytreeMap = parseInputToBagPolytreeMap(inputFile)
        return (getNumberOfBoxContents(bagPolytreeMap["shiny gold"]!!.contents, bagPolytreeMap) - 1).toString()
    }

    data class Bag(
        val color: String,
        val contents: ArrayList<Content>,
        val containers: ArrayList<Bag>
    )

    data class Content(
        val number: Int,
        val color: String
    )
}
