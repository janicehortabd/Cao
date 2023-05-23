package pt.ipg.cao

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


class TabelaCao(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_RACA TEXT NOT NULL, $CAMPO_ISBN TEXT, $CAMPO_DATA_NASCIMENTO INTEGER, $CAMPO_FK_CATEGORIA INTEGER NOT NULL, FOREIGN KEY ($CAMPO_FK_CATEGORIA) REFERENCES ${TabelaCategorias.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    companion object {
        const val NOME_TABELA = "cao"

        const val CAMPO_RACA = "raca"
        const val CAMPO_ISBN = "isbn"
        const val CAMPO_DATA_NASCIMENTO = "data_nascimento"
        const val CAMPO_FK_CATEGORIA = "id_categoria"

        val CAMPOS = arrayOf(BaseColumns._ID, CAMPO_RACA, CAMPO_ISBN, CAMPO_DATA_NASCIMENTO, CAMPO_FK_CATEGORIA)
    }

}