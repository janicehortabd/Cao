package pt.ipg.cao

import android.content.ContentValues

data class Cao(
    var raca: String,
    var idCategoria: Int,
    var isbn: String? = null,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaCao.CAMPO_TITULO, titulo)
        valores.put(TabelaCao.CAMPO_ISBN, isbn)
        valores.put(TabelaCao.CAMPO_FK_CATEGORIA, idCategoria)

        return valores
    }
}