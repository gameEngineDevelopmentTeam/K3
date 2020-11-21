package itoh.engine.polygon.geometry

import itoh.engine.Utils.Companion.loadResource

//データ格納
data class Object(
        val vList: FloatArray = floatArrayOf(),
        val vtList: FloatArray = floatArrayOf(),
        val vnList: FloatArray = floatArrayOf(),
        val fList: IntArray = intArrayOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Object

        if (!vList.contentEquals(other.vList)) return false
        if (!vtList.contentEquals(other.vtList)) return false
        if (!vnList.contentEquals(other.vnList)) return false
        if (!fList.contentEquals(other.fList)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = vList.contentHashCode()
        result = 31 * result + vtList.contentHashCode()
        result = 31 * result + vnList.contentHashCode()
        result = 31 * result + fList.contentHashCode()
        return result
    }
}


class ObjLoader {
    //ファイル読み込み
    private val objData = loadResource("cube.obj").split("\n")
    fun objLoader() {
        for (dataLine in objData) {
            val parsed = dataLine.split(" ")
            when (parsed[0]) {
                "v" -> {
                }
            }
        }
    }


    //Meshへ引き渡し
}