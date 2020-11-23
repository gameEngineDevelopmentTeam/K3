package itoh.engine.polygon

import itoh.engine.Resources.Companion.loadResource
import org.joml.Vector2f
import org.joml.Vector3f


class ObjLoader {
    fun objLoader() {
        //ファイル読み込み
        val objData = loadResource("cube.obj").split("\n")
        val vList: ArrayList<Vector3f> = ArrayList()
        val vtList: ArrayList<Vector2f> = ArrayList()
        val vnList: ArrayList<Vector3f> = ArrayList()
        val fList: ArrayList<Face> = ArrayList()

        for (dataLine in objData) {
            val parsed = dataLine.split(" ")
            when (parsed[0]) {
                // 頂点
                "v" -> {
                    vList.add(Vector3f(
                            parsed[1].toFloat(),
                            parsed[2].toFloat(),
                            parsed[3].toFloat()
                    ))
                }
                "vt" -> {
                    vtList.add(Vector2f(
                            parsed[1].toFloat(),
                            parsed[2].toFloat()
                    ))
                }
                "vn" -> {
                    vnList.add(Vector3f(
                            parsed[1].toFloat(),
                            parsed[2].toFloat(),
                            parsed[3].toFloat()
                    ))
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


class Face {

}