package pt.ipg.cao

import android.content.ContentValues
import android.database.Cursor
import android.icu.util.Calendar
import android.os.Build
import android.provider.BaseColumns
import androidx.annotation.RequiresApi


data class Cao(
    var raca: String,
    var idCategoria: Long,
    var isbn: String? = null,
    var dataNascimento: java.util.Calendar = null,
    var id: Long = -1
) {
    @RequiresApi(Build.VERSION_CODES.N)
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaCao.CAMPO_RACA, raca)
        valores.put(TabelaCao.CAMPO_ISBN, isbn)
        valores.put(TabelaCao.CAMPO_DATA_NASCIMENTO, dataNascimento?.timeInMillis)
        valores.put(TabelaCao.CAMPO_FK_CATEGORIA, idCategoria)

        return valores
    }
    companion object {
        @RequiresApi(Build.VERSION_CODES.N)
        fun fromCursor(cursor: Cursor) : Cao {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posRaca = cursor.getColumnIndex(TabelaCao.CAMPO_RACA)
            val posISBN = cursor.getColumnIndex(TabelaCao.CAMPO_ISBN)
            val posDataPub = cursor.getColumnIndex(TabelaCao.CAMPO_DATA_NASCIMENTO)
            val posCategoriaFK = cursor.getColumnIndex(TabelaCao.CAMPO_FK_CATEGORIA)

            val id = cursor.getLong(posId)
            val raca = cursor.getString(posRaca)
            val isbn = cursor.getString(posISBN)

            var dataNascimento: Calendar?

            if (cursor.isNull(posDataPub)) {
                dataNascimento = null
            } else {
                dataNascimento = Calendar.getInstance()
                dataNascimento.timeInMillis = cursor.getLong(posDataPub)
            }

            val categoriaId = cursor.getLong(posCategoriaFK)

            return Cao(raca, categoriaId, isbn, dataNascimento, id)
        }
    }
}