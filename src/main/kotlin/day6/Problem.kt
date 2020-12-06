package day6

import util.getResourceAsText

fun main() {

    listOf(
        "abc",
        "",
        "a",
        "b",
        "c",
        "",
        "ab",
        "ac",
        "",
        "a",
        "a",
        "a",
        "a",
        "",
        "b"
    )
    val total = getResourceAsText("data6.txt").toGroups()
        .map { it.answers.size }
        .sum()

    println(total)
}

private fun List<String>.toGroups(): List<Group> {
    return fold(listOf(Group())) { acc, s ->
        if (s.isNotEmpty()) {
            val last: Group = acc.last().addTo(s.map { it.toString() })
            acc.dropLast(1) + last
        } else acc + Group()
    }
}

data class Group(val answers: Map<String, Boolean> = emptyMap())

fun Group.addTo(newQuestions: List<String>): Group {
    val map = this.answers.toMutableMap().apply { putAll(newQuestions.map { it to true }) }
    return Group(map)
}