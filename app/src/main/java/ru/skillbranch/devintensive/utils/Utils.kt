package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")
        var firstName:String? = null
        var lastName:String? = null
        if (parts?.getOrNull(0) != "")
            firstName = parts?.getOrNull(0)
        if (parts?.getOrNull(1) != "")
            lastName = parts?.getOrNull(1)
        return Pair(firstName, lastName)
    }

    fun transliteration(payload: String, divider: String = " "): String {
        var output = ""
        payload.forEach {
            output+=when(it) {
                'а', 'А' -> {if (it == 'а') "a" else "A"}
                'б', 'Б' -> {if (it == 'б') "b" else "B"}
                'в', 'В' -> {if (it == 'в') "v" else "V"}
                'г', 'Г' -> {if (it == 'г') "g" else "G"}
                'д', 'Д' -> {if (it == 'д') "d" else "D"}
                'е', 'Е', 'Ё', 'ё', 'Э', 'э' -> {if (it == 'е' || it == 'ё' || it == 'э') "e" else "E"}
                'ж', 'Ж' -> {if (it == 'ж') "zh" else "Zh"}
                'з', 'З' -> {if (it == 'з') "z" else "Z"}
                'и', 'И', 'й', 'Й', 'ы' -> {if (it == 'й' || it == 'и' || it == 'ы') "i" else "I"}
                'к', 'К' -> {if (it == 'к') "k" else "K"}
                'л', 'Л' -> {if (it == 'л') "l" else "L"}
                'м', 'М' -> {if (it == 'м') "m" else "M"}
                'н', 'Н' -> {if (it == 'н') "n" else "N"}
                'о', 'О' -> {if (it == 'о') "o" else "O"}
                'п', 'П' -> {if (it == 'п') "p" else "P"}
                'р', 'Р' -> {if (it == 'р') "r" else "R"}
                'с', 'С' -> {if (it == 'с') "s" else "S"}
                'т', 'Т' -> {if (it == 'т') "t" else "T"}
                'у', 'У' -> {if (it == 'у') "u" else "U"}
                'ф', 'Ф' -> {if (it == 'ф') "f" else "F"}
                'х', 'Х' -> {if (it == 'х') "h" else "H"}
                'ц', 'Ц' -> {if (it == 'ц') "c" else "C"}
                'ч', 'Ч' ->  {if (it == 'ч') "ch" else "Ch"}
                'ш', 'Ш', 'Щ', 'щ' -> {if (it == 'ш' || it == 'щ') "sh" else "Sh"}
                'ъ', 'ь' -> ""
                'ю', 'Ю' -> {if (it == 'ю') "yu" else "Yu"}
                'я', 'Я' -> {if (it == 'я') "ya" else "Ya"}
                ' ' -> divider
                else -> it.toString()
            }
        }
        return output
    }
    fun toInitials(firstName: String?, lastName: String?): String? {
        var output: String?
        if ((firstName?.replace(" ", "") == "" && lastName?.replace(" ", "") == "") ||
            (firstName == null && lastName == null))
            return null
        else
            return "${firstName?.replace(" ", "")?.getOrNull(0) ?: ""}${lastName?.replace(" ", "")?.getOrNull(0)
                ?: ""}".toUpperCase()
    }
}