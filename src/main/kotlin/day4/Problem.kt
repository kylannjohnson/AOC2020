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

    val validPassports = listOf(
        "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980",
        "hcl:#623a2f",
        "",
        "eyr:2029 ecl:blu cid:129 byr:1989",
        "iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm",
        "",
        "hcl:#888785",
        "hgt:164cm byr:2001 iyr:2015 cid:88",
        "pid:545766238 ecl:hzl",
        "eyr:2022",
        "",
        "iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719",
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
    val operation = fold(Passport() to listOf<Passport>()) { acc, s ->
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

enum class Category(val value: String, val validator: (String) -> Boolean) {
    BYR("byr", { value -> value.toInt() in 1920..2002 }),
    IYR("iyr", { value -> value.toInt() in 2010..2020 }),
    EYR("eyr", { value -> value.toInt() in 2020..2030 }),
    HGT("hgt", { value ->
        when {
            value.takeLast(2) == "in" -> value.dropLast(2).toInt() in 59..76
            value.takeLast(2) == "cm" -> value.dropLast(2).toInt() in 150..193
            else -> false
        }
    }),
    HCL("hcl", { value -> value.matches("\\#[a-f|0-9]{6}".toRegex()) }),
    ECL("ecl", { value -> value in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") }),
    PID("pid", { value -> value.length == 9 && value.fold(true) { acc, c -> acc && c.isDigit() } }),
    CID("cid", { _ -> true })
}

val requiredCategories = Category.values().filter { it != Category.CID }


fun Passport.addFields(field: List<PassportField>): Passport {
    return Passport(this.fields + field)
}

fun String.toPassportField(): List<PassportField> {
    return this.split(" ")
        .map { it.split(":") }
        .mapNotNull { fieldAndValue ->
            val c = Category.values().first { it.value == fieldAndValue[0] }
            val isValid = c.validator(fieldAndValue[1])
            println("$c ${fieldAndValue[1]}   $isValid")
            if (isValid) {
                PassportField(c, fieldAndValue[1])
            } else null
        }
}