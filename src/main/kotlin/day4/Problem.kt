package day4

import util.getResourceAsText

fun main() {
    val input = listOf(
        "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd",
        "byr:1937 iyr:2017 cid:147 hgt:183cm",
        "",
        "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884",
        "hcl:#cfa07d byr:1929",
        "",
        "hcl:#ae17e1 iyr:2013",
        "eyr:2024",
        "ecl:brn pid:760753108 byr:1931",
        "hgt:179cm",
        "",
        "hcl:#cfa07d eyr:2025 pid:166559648",
        "iyr:2011 ecl:brn hgt:59in",
    )

    val data = getResourceAsText("data4.txt")

    val valid = data.parse()
        .filter { it.containsRequiredCategories() }
        .also { it.forEach(::println) }

    println("Count: ${valid.count()}")
}

private fun Passport.containsRequiredCategories(): Boolean {
    return fields
        .map { it.category }
        .containsAll(requiredCategories)
}

fun List<String>.parse(): List<Passport> {
    val operation =  fold(Passport() to listOf<Passport>()) { acc, s ->
        if (s.isEmpty()) {
            Passport() to acc.second + acc.first
        } else {
            acc.first.addFields(s.toPassportField()) to acc.second
        }
    }

    return operation.second + operation.first
}

data class Passport(val fields: List<PassportField> = emptyList())

data class PassportField(val category: Category, val value: String)

enum class Category(val value: String) {
    BYR("byr"),
    IYR("iyr"),
    EYR("eyr"),
    HGT("hgt"),
    HCL("hcl"),
    ECL("ecl"),
    PID("pid"),
    CID("cid")
}

val requiredCategories = Category.values().filter { it != Category.CID }


fun Passport.addFields(field: List<PassportField>): Passport {
    return Passport(this.fields + field)
}

fun String.toPassportField(): List<PassportField> {
    return this.split(" ")
        .map { it.split(":") }
        .map { fieldAndValue ->
            PassportField(
                Category.values().first { it.value == fieldAndValue[0] },
                fieldAndValue[1]
            )
        }
}