package day6

import util.getResourceAsText

fun main() {

    val input = listOf(
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
//
    val total = getResourceAsText("data6.txt").toGroups()
        .map { it.answers.values.filter { v -> v == it.people }.size }
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

data class Group(val people: Int = 0, val answers: Map<String, Int> = emptyMap())

fun Group.addTo(newQuestions: List<String>): Group {
    val current = this.answers.toMutableMap()

    newQuestions.forEach {
        current[it] = if (current.containsKey(it)) {
            current[it]!! + 1
        } else 1
    }
    return copy(people = people + 1, answers = current)
}