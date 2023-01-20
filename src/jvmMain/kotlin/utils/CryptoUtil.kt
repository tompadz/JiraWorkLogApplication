package utils

import java.util.*

class CryptoUtil {

    fun encodeToBase64(input:String):String {
       return Base64.getEncoder().encodeToString(input.toByteArray())
    }

    fun decodeFromBase64(input: String) : String {
        val decodedBytes: ByteArray = Base64.getDecoder().decode(input)
        return String(decodedBytes)
    }

}