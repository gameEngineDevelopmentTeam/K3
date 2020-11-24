package itoh.engine

import java.io.File

class Resources {
    companion object {
        fun loadResource(fileName: String): String {
            println("Load Resource   : $fileName")
            return File(
                    System.getProperty("user.dir") + "/src/main/resources/$fileName"
            ).readText(Charsets.UTF_8)
        }
    }
}

data class ClearColor(val r: Float, val g: Float, val b: Float, val a: Float)
