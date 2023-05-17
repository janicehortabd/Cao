package pt.ipg.cao

import android.database.sqlite.SQLiteDatabase

private const val NOME_TABELA = "categorias"

class TabelaCategorias(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, descricao TEXT NOT NULL)")
    }
}