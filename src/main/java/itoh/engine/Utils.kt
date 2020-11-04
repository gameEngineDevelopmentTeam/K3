package itoh.engine

import java.io.File

class Utils {
    companion object {
        fun loadResource(fileName: String): String =
                File(System.getProperty("user.dir") + "/src/main/resources/$fileName").readText(Charsets.UTF_8)
    }
}
