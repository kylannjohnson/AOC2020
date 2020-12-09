package day7

import util.getResourceAsText

fun main() {

    val rules1 = listOf(
        "light red bags contain 1 bright white bag, 2 muted yellow bags.",
        "dark orange bags contain 3 bright white bags, 4 muted yellow bags.",
        "bright white bags contain 1 shiny gold bag.",
        "muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.",
        "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.",
        "dark olive bags contain 3 faded blue bags, 4 dotted black bags.",
        "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.",
        "faded blue bags contain no other bags.",
        "dotted black bags contain no other bags."
    )

    val rules2 = listOf(
        "shiny gold bags contain 2 dark red bags.",
        "dark red bags contain 2 dark orange bags.",
        "dark orange bags contain 2 dark yellow bags.",
        "dark yellow bags contain 2 dark green bags.",
        "dark green bags contain 2 dark blue bags.",
        "dark blue bags contain 2 dark violet bags.",
        "dark violet bags contain no other bags.",
    )

//    val rulesList = rules2.toRulesList()
    val rulesList = getResourceAsText("data7.txt").toRulesList()

    println(findBags(rulesList, listOf("shiny gold"), emptySet()))
    println(findTotalBags(rulesList, 1 to "shiny gold") - 1)
}

fun findBags(rulesList: List<Rule>, targetBags: List<String>, currentCargo: Set<String>): Int {
    val found =
        rulesList.filter { bag -> targetBags.any { it in bag.canContain.map { c -> c.second } } }
            .map { it.containingBagColor }

    return if (found.isEmpty()) currentCargo.size else findBags(rulesList, found, currentCargo + found)
}

fun findTotalBags(rulesList: List<Rule>, targetBags: Pair<Int, String>): Int {
    val found: List<Pair<Int, String>> =
        rulesList.first { bag -> bag.containingBagColor == targetBags.second }.canContain

    return if (found.isEmpty()) {
        targetBags.first
    } else {
        targetBags.first + targetBags.first * found.fold(0) { acc, pair ->
            acc + findTotalBags(rulesList, pair)
        }
    }
}

// H/t to this snippet to simplify this https://github.com/brentwatson/AOC2020/blob/main/src/day7.ws.kts
fun List<String>.toRulesList() = map { rule ->
    val (color, amountAndCargo) = rule.split(" bags contain ")
    Rule(
        containingBagColor = color,
        canContain = amountAndCargo
            .split(", ")
            .mapNotNull {
                "(\\d)(.*) bag(.*)".toRegex().find(it)?.groupValues?.let { g ->
                    g[1].toInt() to g[2].trim()
                }
            }
    )
}

data class Rule(val containingBagColor: String, val canContain: List<Pair<Int, String>>)
