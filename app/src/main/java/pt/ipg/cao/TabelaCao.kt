package pt.ipg.cao

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


class TabelaCao(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, titulo TEXT NOT NULL, isbn TEXT, id_categoria INTEGER NOT NULL, FOREIGN KEY (id_categoria) REFERENCES ${TabelaCategorias.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    companion object {
        const val NOME_TABELA = "cao"
        const val CAMPO_RACA = "raca"
        const val CAMPO_ISBN = "isbn"
        const val CAMPO_FK_CATEGORIA = "id_categoria"

    }
}