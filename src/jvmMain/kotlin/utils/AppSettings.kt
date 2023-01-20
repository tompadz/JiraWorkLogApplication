package utils

import com.google.gson.Gson
import data.models.UserModel
import java.io.File

object AppSettings {

    private const val FILE_NAME = "config.json"

    lateinit var settings:Settings
        private set

    data class Settings(
        val token:String? = null,
        val user:UserModel? = null,
        val url:String? = null,
    )

    fun initSettings() {

        val file = File(FILE_NAME)

        if (file.createNewFile()) {
            //file created
            settings = Settings()
            val text = Gson().toJson(Settings())
            file.writeText(text)
            println("settings file created")
        }else {
            //file already exists
            val settings:Settings = Gson().fromJson(file.readText(), Settings::class.java)
            this.settings = settings
            println("settings file read")
        }
    }

    fun changeSettings(settings:Settings) {
        val file = File(FILE_NAME)
        val text = Gson().toJson(settings)
        file.writeText(text)
        this.settings = settings
        println("settings file changed")
    }
}