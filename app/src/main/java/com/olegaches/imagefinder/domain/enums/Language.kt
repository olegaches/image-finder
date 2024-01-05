package com.olegaches.imagefinder.domain.enums

import com.squareup.moshi.Json

enum class Language(val value: String) {
    @Json(name = "af")
    AFRIKAANS("Afrikaans"),
    @Json(name = "ak")
    AKAN("Akan"),
    @Json(name = "sq")
    ALBANIAN("Albanian"),
    @Json(name = "ws")
    SAMOA("Samoa"),
    @Json(name = "am")
    AMHARIC("Amharic"),
    @Json(name = "ar")
    ARABIC("Arabic"),
    @Json(name = "hy")
    ARMENIAN("Armenian"),
    @Json(name = "az")
    AZERBAIJANI("Azerbaijani"),
    @Json(name = "eu")
    BASQUE("Basque"),
    @Json(name = "be")
    BELARUSIAN("Belarusian"),
    @Json(name = "bem")
    BEMBA("Bemba"),
    @Json(name = "bn")
    BENGALI("Bengali"),
    @Json(name = "bh")
    BIHARI("Bihari"),
    @Json(name = "xx-bork")
    BORK_BORK_BORK("Bork, bork, bork!"),
    @Json(name = "bs")
    BOSNIAN("Bosnian"),
    @Json(name = "br")
    BRETON("Breton"),
    @Json(name = "bg")
    BULGARIAN("Bulgarian"),
    @Json(name = "bt")
    BHUTANESE("Bhutanese"),
    @Json(name = "km")
    CAMBODIAN("Cambodian"),
    @Json(name = "ca")
    CATALAN("Catalan"),
    @Json(name = "chr")
    CHEROKEE("Cherokee"),
    @Json(name = "ny")
    CHICHEWA("Chichewa"),
    @Json(name = "zh-cn")
    CHINESE_SIMPLIFIED("Chinese (Simplified)"),
    @Json(name = "zh-tw")
    CHINESE_TRADITIONAL("Chinese (Traditional)"),
    @Json(name = "co")
    CORSICAN("Corsican"),
    @Json(name = "hr")
    CROATIAN("Croatian"),
    @Json(name = "cs")
    CZECH("Czech"),
    @Json(name = "da")
    DANISH("Danish"),
    @Json(name = "nl")
    DUTCH("Dutch"),
    @Json(name = "xx-elmer")
    ELMER_FUDD("Elmer Fudd"),
    @Json(name = "en")
    ENGLISH("English"),
    @Json(name = "eo")
    ESPERANTO("Esperanto"),
    @Json(name = "et")
    ESTONIAN("Estonian"),
    @Json(name = "ee")
    EWE("Ewe"),
    @Json(name = "fo")
    FAROESE("Faroese"),
    @Json(name = "tl")
    FILIPINO("Filipino"),
    @Json(name = "fi")
    FINNISH("Finnish"),
    @Json(name = "fr")
    FRENCH("French"),
    @Json(name = "fy")
    FRISIAN("Frisian"),
    @Json(name = "gaa")
    GA("Ga"),
    @Json(name = "gl")
    GALICIAN("Galician"),
    @Json(name = "ka")
    GEORGIAN("Georgian"),
    @Json(name = "de")
    GERMAN("German"),
    @Json(name = "el")
    GREEK("Greek"),
    @Json(name = "kl")
    GREENLANDIC("Greenlandic"),
    @Json(name = "gn")
    GUARANI("Guarani"),
    @Json(name = "gu")
    GUJARATI("Gujarati"),
    @Json(name = "xx-hacker")
    HACKER("Hacker"),
    @Json(name = "ht")
    HAITIAN_CREOLE("Haitian Creole"),
    @Json(name = "ha")
    HAUSA("Hausa"),
    @Json(name = "haw")
    HAWAIIAN("Hawaiian"),
    @Json(name = "iw")
    HEBREW("Hebrew"),
    @Json(name = "hi")
    HINDI("Hindi"),
    @Json(name = "hu")
    HUNGARIAN("Hungarian"),
    @Json(name = "is")
    ICELANDIC("Icelandic"),
    @Json(name = "ig")
    IGBO("Igbo"),
    @Json(name = "id")
    INDONESIAN("Indonesian"),
    @Json(name = "ia")
    INTERLINGUA("Interlingua"),
    @Json(name = "ga")
    IRISH("Irish"),
    @Json(name = "it")
    ITALIAN("Italian"),
    @Json(name = "ja")
    JAPANESE("Japanese"),
    @Json(name = "jw")
    JAVANESE("Javanese"),
    @Json(name = "kn")
    KANNADA("Kannada"),
    @Json(name = "kk")
    KAZAKH("Kazakh"),
    @Json(name = "rw")
    KINYARWANDA("Kinyarwanda"),
    @Json(name = "rn")
    KIRUNDI("Kirundi"),
    @Json(name = "xx-klingon")
    KLINGON("Klingon"),
    @Json(name = "kg")
    KONGO("Kongo"),
    @Json(name = "ko")
    KOREAN("Korean"),
    @Json(name = "kri")
    KRIO_SIERRA_LEONE("Krio (Sierra Leone)"),
    @Json(name = "ku")
    KURDISH("Kurdish"),
    @Json(name = "ckb")
    KURDISH_SORANI("Kurdish (Soranî)"),
    @Json(name = "ky")
    KYRGYZ("Kyrgyz"),
    @Json(name = "lo")
    LAOTHIAN("Laothian"),
    @Json(name = "la")
    LATIN("Latin"),
    @Json(name = "lv")
    LATVIAN("Latvian"),
    @Json(name = "ln")
    LINGALA("Lingala"),
    @Json(name = "lt")
    LITHUANIAN("Lithuanian"),
    @Json(name = "loz")
    LOZI("Lozi"),
    @Json(name = "lg")
    LUGANDA("Luganda"),
    @Json(name = "ach")
    LUO("Luo"),
    @Json(name = "mk")
    MACEDONIAN("Macedonian"),
    @Json(name = "mg")
    MALAGASY("Malagasy"),
    @Json(name = "ms")
    MALAY("Malay"),
    @Json(name = "ml")
    MALAYALAM("Malayalam"),
    @Json(name = "mt")
    MALTESE("Maltese"),
    @Json(name = "mv")
    MALDIVES("Maldives"),
    @Json(name = "mi")
    MAORI("Maori"),
    @Json(name = "mr")
    MARATHI("Marathi"),
    @Json(name = "mfe")
    MAURITIAN_CREOLE("Mauritian Creole"),
    @Json(name = "mo")
    MOLDAVIAN("Moldavian"),
    @Json(name = "mn")
    MONGOLIAN("Mongolian"),
    @Json(name = "sr-me")
    MONTENEGRIN("Montenegrin"),
    @Json(name = "my")
    MYANMAR("Myanmar"),
    @Json(name = "ne")
    NEPALI("Nepali"),
    @Json(name = "pcm")
    NIGERIAN_PIDGIN("Nigerian Pidgin"),
    @Json(name = "nso")
    NORTHERN_SOTHO("Northern Sotho"),
    @Json(name = "no")
    NORWEGIAN("Norwegian"),
    @Json(name = "nn")
    NORWEGIAN_NYNORSK("Norwegian (Nynorsk)"),
    @Json(name = "oc")
    OCCITAN("Occitan"),
    @Json(name = "or")
    ORIYA("Oriya"),
    @Json(name = "om")
    OROMO("Oromo"),
    @Json(name = "ps")
    PASHTO("Pashto"),
    @Json(name = "fa")
    PERSIAN("Persian"),
    @Json(name = "xx-pirate")
    PIRATE("Pirate"),
    @Json(name = "pl")
    POLISH("Polish"),
    @Json(name = "pt")
    PORTUGUESE("Portuguese"),
    @Json(name = "pt-br")
    PORTUGUESE_BRAZIL("Portuguese (Brazil)"),
    @Json(name = "pt-pt")
    PORTUGUESE_PORTUGAL("Portuguese (Portugal)"),
    @Json(name = "pa")
    PUNJABI("Punjabi"),
    @Json(name = "qu")
    QUECHUA("Quechua"),
    @Json(name = "ro")
    ROMANIAN("Romanian"),
    @Json(name = "rm")
    ROMANSH("Romansh"),
    @Json(name = "nyn")
    RUNYAKITARA("Runyakitara"),
    @Json(name = "ru")
    RUSSIAN("Russian"),
    @Json(name = "gd")
    SCOTS_GAELIC("Scots Gaelic"),
    @Json(name = "sr")
    SERBIAN("Serbian"),
    @Json(name = "sh")
    SERBO_CROATIAN("Serbo-Croatian"),
    @Json(name = "st")
    SESOTHO("Sesotho"),
    @Json(name = "tn")
    SETSWANA("Setswana"),
    @Json(name = "crs")
    SEYCHELLOIS_CREOLE("Seychellois Creole"),
    @Json(name = "sn")
    SHONA("Shona"),
    @Json(name = "sd")
    SINDHI("Sindhi"),
    @Json(name = "si")
    SINHALESE("Sinhalese"),
    @Json(name = "sk")
    SLOVAK("Slovak"),
    @Json(name = "sl")
    SLOVENIAN("Slovenian"),
    @Json(name = "so")
    SOMALI("Somali"),
    @Json(name = "es")
    SPANISH("Spanish"),
    @Json(name = "es-419")
    SPANISH_LATIN_AMERICAN("Spanish (Latin American)"),
    @Json(name = "su")
    SUNDANESE("Sundanese"),
    @Json(name = "sw")
    SWAHILI("Swahili"),
    @Json(name = "sv")
    SWEDISH("Swedish"),
    @Json(name = "tg")
    TAJIK("Tajik"),
    @Json(name = "ta")
    TAMIL("Tamil"),
    @Json(name = "tt")
    TATAR("Tatar"),
    @Json(name = "te")
    TELUGU("Telugu"),
    @Json(name = "th")
    THAI("Thai"),
    @Json(name = "ti")
    TIGRINYA("Tigrinya"),
    @Json(name = "to")
    TONGA("Tonga"),
    @Json(name = "lua")
    TSHILUBA("Tshiluba"),
    @Json(name = "tum")
    TUMBUKA("Tumbuka"),
    @Json(name = "tr")
    TURKISH("Turkish"),
    @Json(name = "tk")
    TURKMEN("Turkmen"),
    @Json(name = "tw")
    TWI("Twi"),
    @Json(name = "ug")
    UIGHUR("Uighur"),
    @Json(name = "uk")
    UKRAINIAN("Ukrainian"),
    @Json(name = "ur")
    URDU("Urdu"),
    @Json(name = "uz")
    UZBEK("Uzbek"),
    @Json(name = "vu")
    VANUATU("Vanuatu"),
    @Json(name = "vi")
    VIETNAMESE("Vietnamese"),
    @Json(name = "cy")
    WELSH("Welsh"),
    @Json(name = "wo")
    WOLOF("Wolof"),
    @Json(name = "xh")
    XHOSA("Xhosa"),
    @Json(name = "yi")
    YIDDISH("Yiddish"),
    @Json(name = "yo")
    YORUBA("Yoruba"),
    @Json(name = "zu")
    ZULU("Zulu")
}