package day2

import util.getResourceAsText

fun main() {
    val pw = listOf(
        "1-3 a: abcde",
        "1-3 b: cdefg",
        "2-9 c: ccccccccc",
    )

    enforcePolicies(pw)

    val f = enforcePolicies(getResourceAsText("data2.txt"))

    println(f.count())

}

private fun enforcePolicies(pw: List<String>): List<Policy> {
    return pw
        .map { it.split(" ") }
        .map {
            val foo = it[0].split("-").map { n -> n.toInt() }
            Policy(IntRange(foo[0], foo[1]), it[1].dropLast(1), it[2])
        }
        .filter { policy ->
            val f = policy.password.filter { it.toString() == policy.letter }
            f.length in policy.range
        }
}

data class Policy(val range: IntRange, val letter: String, val password: String)