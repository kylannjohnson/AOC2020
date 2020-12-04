package day2

import util.getResourceAsText

fun main() {
    val pw = listOf(
        "1-3 a: abcde",
        "1-3 b: cdefg",
        "2-9 c: ccccccccc",
    )

    println(enforcePolicies(pw))

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

            if(policy.password.length < policy.range.last) {
                false
            } else {

                val f = policy.password[policy.range.first-1].toString() == policy.letter
                val l = policy.password[policy.range.last-1].toString() == policy.letter

                f xor l
            }
        }
}

data class Policy(val range: IntRange, val letter: String, val password: String)