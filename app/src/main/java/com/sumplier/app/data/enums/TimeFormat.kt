package com.sumplier.app.data.enums

enum class TimeFormat(val pattern: String) {
    ISO_WITH_Z("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"), // UTC
    ISO_WITHOUT_Z("yyyy-MM-dd'T'HH:mm:ss.SSS"), // non-UTC
    DISPLAY_FORMAT("dd.MM.yyyy HH:mm")
}