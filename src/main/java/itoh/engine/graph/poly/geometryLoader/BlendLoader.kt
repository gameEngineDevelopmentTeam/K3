package itoh.engine.graph.poly.geometryLoader

import itoh.engine.Utils
import itoh.engine.graph.poly.GeometryLoader

class BlendLoader {
    companion object : GeometryLoader {
        private lateinit var fileData: MutableList<Int>
        private lateinit var fileName: String
        private var defaultLength: Int = 0
        private var nowLength: Int = 0
        private lateinit var arrayArray: MutableList<MutableList<Int>>
        override fun readFile(fileName: String) {
            this.fileName = fileName
            var tmp: Int
            val data = Utils.loadBinary(fileName = fileName)
            while (true) {
                tmp = data.read()
                if (tmp == -1) break
                fileData.add(tmp)
            }
            defaultLength = fileData.size
            nowLength = defaultLength
        }

        override fun dataParser() {
            //fileHeader
            val magicNumber: String = "BLENDER"
            val identifier = (fileData pop 0..6).map { it.toChar() }.joinToString("")
            if (identifier != magicNumber) System.err.println("不正なファイルが読み込まれました : $fileName")
            val pointerSize: Boolean = (fileData pop 7).toChar() != '_'  // 32bit -> false | 64bit -> true
            var endian: Boolean = (fileData pop 8).toChar() != 'v'  // little endian -> false | big endian -> true
            var version: Int = Integer.parseInt((fileData pop 9..11).map { it.toChar() }.joinToString(""))  // blender version ex.(2.83 -> 283)

            if (pointerSize) {
                for (i in 0..fileData.size / 16) {
                    lateinit var array: MutableList<Int>
                    for (j in 16 * i until 16 * (i + 1)) {
                        array.add(fileData pop j)
                    }
                    arrayArray.add(array)
                }
            } else {
                for (i in 0..fileData.size / 12) {
                    lateinit var array: MutableList<Int>
                    for (j in 12 * i until 12 * (i + 1)) {
                        array.add(fileData pop j)
                    }
                    arrayArray.add(array)
                }
            }
        }

        private fun fileBlock() {

        }

        private infix fun MutableList<Int>.pop(indices: Int): Int {
            val data = this[indices]
            this.removeAt(indices)
            nowLength--
            return data
        }

        private infix fun MutableList<Int>.pop(indices: IntRange): List<Int> {
            val data = this.slice(indices)
            for (i in indices) {
                this.removeAt(i)
                nowLength--
            }
            return data
        }
    }
}