package itoh.engine

import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream

class Utils {
    companion object {
        fun loadResource(fileName: String): String{
            println("Load Resource   : $fileName")
            return File(System.getProperty("user.dir") + "/src/main/resources/$fileName").readText(Charsets.UTF_8)
        }
        fun loadBinary(fileName: String): DataInputStream = DataInputStream(BufferedInputStream(FileInputStream(System.getProperty("user.dir") + "/src/main/resources/$fileName")))
    }
}