package itoh.engine.polygon

import itoh.engine.Resources.Companion.loadResource
import org.joml.Vector2f
import org.joml.Vector3f
import java.util.*


open class ObjLoader {
    fun objLoader(fileName:String): Mesh {
        //ファイル読み込み
        val objData = loadResource(fileName).split("\n")
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
                    val face = Face(parsed[1], parsed[2], parsed[3])
                    fList.add(face)
                }
                else -> {
                }
            }
        }
        return reorderLists(vList, vtList, vnList, fList)
    }


    private fun reorderLists(posList: List<Vector3f>, textCoordList: List<Vector2f>,
                             normList: List<Vector3f>, facesList: List<Face>): Mesh {
        val indices: MutableList<Int> = ArrayList()
        val posArr = FloatArray(posList.size * 3)
        var i = 0
        for (pos in posList) {
            posArr[i * 3] = pos.x
            posArr[i * 3 + 1] = pos.y
            posArr[i * 3 + 2] = pos.z
            i++
        }
        val textCoordArr = FloatArray(posList.size * 2)
        val normArr = FloatArray(posList.size * 3)
        for (face in facesList) {
            val faceVertexIndices = face.faceVertexIndices
            for (indValue in faceVertexIndices) {
                processFaceVertex(indValue, textCoordList, normList,
                        indices, textCoordArr, normArr)
            }
        }
        var indicesArr: IntArray? = IntArray(indices.size)
        indicesArr = indices.stream().mapToInt { v: Int? -> v!! }.toArray()
        return Mesh(posArr, textCoordArr, normArr, indicesArr)
    }

    private fun processFaceVertex(indices: IdxGroup?, textCoordList: List<Vector2f>,
                                  normList: List<Vector3f>, indicesList: MutableList<Int>,
                                  texCoordArr: FloatArray, normArr: FloatArray) {

        val posIndex = indices!!.idxPos
        indicesList.add(posIndex)

        if (indices.idxTextCoords >= 0) {
            val textCoord = textCoordList[indices.idxTextCoords]
            texCoordArr[posIndex * 2] = textCoord.x
            texCoordArr[posIndex * 2 + 1] = 1 - textCoord.y
        }
        if (indices.idxVecNormal >= 0) {
            val vecNorm = normList[indices.idxVecNormal]
            normArr[posIndex * 3] = vecNorm.x
            normArr[posIndex * 3 + 1] = vecNorm.y
            normArr[posIndex * 3 + 2] = vecNorm.z
        }
    }

    protected class Face(v1: String, v2: String, v3: String) {
        var faceVertexIndices = arrayOfNulls<IdxGroup>(3)

        private fun parseLine(line: String): IdxGroup {
            val idxGroup = IdxGroup()
            val lineTokens = line.split("/".toRegex()).toTypedArray()
            val length = lineTokens.size
            idxGroup.idxPos = lineTokens[0].toInt() - 1
            if (length > 1) {
                val textCoord = lineTokens[1]
                idxGroup.idxTextCoords = if (textCoord.isNotEmpty()) textCoord.toInt() - 1 else IdxGroup.NO_VALUE
                if (length > 2) {
                    idxGroup.idxVecNormal = lineTokens[2].toInt() - 1
                }
            }
            return idxGroup
        }

        init {
            faceVertexIndices = arrayOfNulls(3)
            faceVertexIndices[0] = parseLine(v1)
            faceVertexIndices[1] = parseLine(v2)
            faceVertexIndices[2] = parseLine(v3)
        }
    }

    class IdxGroup {
        var idxPos: Int
        var idxTextCoords: Int
        var idxVecNormal: Int

        companion object {
            const val NO_VALUE = -1
        }

        init {
            idxPos = NO_VALUE
            idxTextCoords = NO_VALUE
            idxVecNormal = NO_VALUE
        }
    }
}