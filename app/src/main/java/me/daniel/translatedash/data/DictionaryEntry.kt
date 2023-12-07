package me.daniel.translatedash.data

data class DictionaryEntry(val lang: String, val hits: Array<Hit>)

data class Hit(val type: String, val opendict: Boolean, val roms: Array<Rom>);

data class Rom(val headword: String, val headword_full: String, val wordclass: String, val arabs: Array<Arab>)

data class Arab(val header: String, val translations: Array<ArabTranslation>)

data class ArabTranslation(val source: String, val target: String)