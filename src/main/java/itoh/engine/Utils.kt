package itoh.engine

import itoh.engine.polygon.Texture
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream


class Utils {
    companion object {
        fun loadResource(fileName: String): String {
            println("Load Resource   : $fileName")
            return File(
                    System.getProperty("user.dir") + "/src/main/resources/$fileName"
            ).readText(Charsets.UTF_8)
        }

        fun loadResourceCR(fileName: String): List<String> {
            println("Load Resource   : $fileName")
            var data =  File(
                    System.getProperty("user.dir") + "/src/main/resources/$fileName"
            ).readText(Charsets.UTF_8)
            return data.split("\n")
        }
    }
}

/*ex*/
class TextureEx{

}
fun Texture.ex(){

}