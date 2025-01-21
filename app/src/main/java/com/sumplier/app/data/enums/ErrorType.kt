package com.sumplier.app.data.enums

enum class ErrorType(val message: String) {
    NO_VALID_EMAIL("Geçerli bir e-posta adresi giriniz!"),
    NO_VALID_PASSWORD("Geçerli bir parola giriniz!"),
    INTERNET_ERROR("Internet bağlantı hatası!"),
    CONNECTION_ERROR("Bağlantı Hatası!"),
}