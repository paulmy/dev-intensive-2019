package ru.skillbranch.devintensive.extensions
import java.lang.Exception


private class EscData(
    val symbol:Char,
    val code:Int,
    val mnemocode:String,
    val hexCode:String = Integer.toHexString(code)){

    companion object Factory{
        private val dictEsc = listOf(
            EscData('"',34, "quot"),
            EscData('&' ,39, "amp"),
            EscData('<',60, "lt"),
            EscData('>',62, "gt"),
            EscData('\'',8216, "lsquo"),
            EscData('\'',8217, "rsquo")
        )
        fun parse(str:String):EscData?
        {
            return dictEsc.firstOrNull { data -> (str == data.mnemocode || str == "#x${data.hexCode}" || str == "#x${data.hexCode.toUpperCase()}" || str == "#${data.code}") }
        }
    }
}

fun String.truncate(len:Int = 16):String{
    if(len<0)
        throw IndexOutOfBoundsException("Unexpected target len. ($len) is less then 0")

    var res = this.trim()
    if(res.length <=len)
        return res
    res = res.substring(0, len).trimEnd() + "..."
    return res
}

/*

*String.stripHtml
Необходимо реализовать метод stripHtml для очистки строки от лишних пробелов, html тегов, escape последовательностей
+1
Реализуй extension позволяющий очистить строку от html тегов и html escape последовательностей ("& < > ' ""),
 а так же удалить пустые символы (пробелы) между словами если их больше 1. Необходимо вернуть модифицированную строку
Пример:
"<p class="title">Образовательное IT-сообщество Skill Branch</p>".stripHtml() //Образовательное IT-сообщество Skill Branch
"<p>Образовательное       IT-сообщество Skill Branch</p>".stripHtml() //Образовательное IT-сообщество Skill Branch

*/
fun String.stripHtml():String{

    var res = ""
    var indexStack = 0
    var index = 0
    val len = this.length
    var isPreviousSpace = false
    while (index<len)
    {
        val symbol = this[index]

        if(symbol == '<') {
            indexStack++
            index++
            continue
        }
        else if(symbol == '>') {
            indexStack--
            index++
            continue
        }
        else if(indexStack<0)
            throw Exception("count \"<\" less then count \">\"")
        if (indexStack>0)
        {
            index++
            continue
        }

        if(symbol == '&')
        {
            val lastEsc = this.substring(index).indexOfFirst {el -> el == ';'}
            if(lastEsc == -1)
                throw Exception("Esc-code error: start symbol \'&\' found, but last symbol \';\' not found")
            val escStr = this.substring(index + 1, index + lastEsc)
            val esc = EscData.parse(escStr)
            if(esc != null) {
                index += lastEsc + 1
                continue
            }
        }
        if(symbol == ' ')
        {
            if(!isPreviousSpace) {
                res += symbol
                isPreviousSpace = true
            }
            index++
            continue
        }
        isPreviousSpace = false
        res += symbol
        index++
    }
    return res
}