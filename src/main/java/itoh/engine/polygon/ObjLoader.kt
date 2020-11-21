package itoh.engine.polygon

import itoh.engine.Resources.Companion.loadResource


class ObjLoader {
    //ファイル読み込み
    private val objData = loadResource("cube.obj").split("\n")
    fun objLoader() {
        for (dataLine in objData) {
            val parsed = dataLine.split(" ")
            when (parsed[0]) {
                // 頂点
                "v" -> {
                }
                "vt" -> {
                }
                "vn" -> {
                }
                "f" -> {
                }
                else -> {
                }
            }
        }
    }
    //Meshへ引き渡し

}

data class Object(
        val vList: FloatArray = floatArrayOf(),
        val vtList: FloatArray = floatArrayOf(),
        val vnList: FloatArray = floatArrayOf(),
        val fList: IntArray = intArrayOf()
)