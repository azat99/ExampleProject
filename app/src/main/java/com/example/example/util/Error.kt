package com.example.example.util

sealed class Error {

    class Unknown(val text: String) : Error()

    sealed class Predefined : Error() {
        object Timeout : Predefined()
        object NoNetwork : Predefined()
        object Server : Predefined()
        object Illegal : Predefined()
    }
}
