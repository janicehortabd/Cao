package pt.ipg.cao

data class Cao(
    var raca: String,
    var idCategoria: Int,
    var isbn: String? = null,
    var id: Long = -1
) {
}