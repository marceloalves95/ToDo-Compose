package extensions

import java.util.Locale

fun String.capitalizeWord(locale: Locale):String{
    return replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }
}