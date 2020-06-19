package com.example.mot.unit

class Language {

    companion object {
        var langCode: Int = -1

        fun setLangCode(code: Int) : Int {
            langCode = code
            return langCode
        }
    }
}