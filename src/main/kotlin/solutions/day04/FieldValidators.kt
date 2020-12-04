package solutions.day04

class FieldValidators {
    companion object {
        fun isValidByr(passport: Map<String, String>): Boolean {
            val byr = passport["byr"]!!.toInt()
            return byr in 1920..2002
        }

        fun isValidIyr(passport: Map<String, String>): Boolean {
            val iyr = passport["iyr"]!!.toInt()
            return iyr in 2010..2020
        }

        fun isValidEyr(passport: Map<String, String>): Boolean {
            val eyr = passport["eyr"]!!.toInt()
            return eyr in 2020..2030
        }

        fun isValidHgt(passport: Map<String, String>): Boolean {
            val hgt = passport["hgt"]!!
            if (hgt.length < 3) {
                return false
            }

            val unit = hgt.takeLast(2)
            val size = hgt.substringBefore(unit).toInt()

            if (unit == "cm") {
                if (size < 150 || size > 193) {
                    return false
                }
            } else if (unit == "in") {
                if (size < 59 || size > 76) {
                    return false
                }
            } else {
                return false
            }

            return true
        }

        fun isValidHcl(passport: Map<String, String>): Boolean {
            val hcl = passport["hcl"]!!
            return Regex("#[0-9a-f]{6}").matches(hcl)
        }

        private var validEcls = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
        fun isValidEcl(passport: Map<String, String>): Boolean {
            val ecl = passport["ecl"]!!
            return validEcls.contains(ecl)
        }

        fun isValidPid(passport: Map<String, String>): Boolean {
            val pid = passport["pid"]!!
            return Regex("[0-9]{9}").matches(pid)
        }
    }
}
