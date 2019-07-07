package ru.skillbranch.devintensive.extensions

fun String.stripHtml(): String {
    var out: String = Regex("^<.*?>").replace(this, "")
    out = Regex("</.*?>$").replace(out, "")
    out = Regex("[\\s]{2,}").replace(out, " ")
    return out.trim()
}

fun String.truncate(num: Int = 16): String {
    if (num >= this.length - 1)
        return this
    return when(this[num]) {
        ' ' -> "${this.substring(0 until num)}..."
        else -> "${this.substring(0..num)}..."
    }
}