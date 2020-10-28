package itoh.engine.graph.poly

interface GeometryLoader {
    fun readFile(fileName: String)
    fun dataParser()
}