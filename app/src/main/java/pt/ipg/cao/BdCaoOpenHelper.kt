package pt.ipg.cao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


private const val VERSAO_BASE_DADOS = 1

class BdCaoOpenHelper (
    context: Context?,
) : SQLiteOpenHelper(context, NOME_BASE_DADOS, null, VERSAO_BASE_DADOS) {
    override fun onCreate(db: SQLiteDatabase?) {
        requireNotNull(db)
        TabelaCategorias(db).cria()
        TabelaCao(db).cria()
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
    companion object {
        const val NOME_BASE_DADOS = "cao.db"
    }
}

