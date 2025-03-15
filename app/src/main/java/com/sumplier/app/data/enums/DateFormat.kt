package com.sumplier.app.data.enums

enum class DateFormat(val pattern: String) {

    /** ISO 8601 UTC format */
    CLOUD_FORMAT("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),

    CLOUD_REQUEST_FORMAT("yyyy-MM-dd HH:mm:ss.SSS"),

    DDMMYYYY_HHMM("dd.MM.yyyy HH:mm")
}
