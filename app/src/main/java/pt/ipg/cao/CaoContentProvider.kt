package pt.ipg.cao

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class CaoContentProvider : ContentProvider(){
    private var bdOpenHelper: BdCaoOpenHelper? = null
    override fun onCreate(): Boolean {
        bdOpenHelper = BdCaoOpenHelper(context);
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val bd = bdOpenHelper!!.readableDatabase

        val endereco = uriMatcher().match(uri)
        val tabela = when (endereco) {
            URI_CATEGORIAS, URI_CATEGORIA_ID -> TabelaCategorias(bd)
            URI_CAO, URI_CAO_ID -> TabelaCao(bd)
            else -> null
        }

        val id = uri.lastPathSegment

        val (selecao, argsSel) = when (endereco) {
            URI_CATEGORIA_ID, URI_CAO_ID -> Pair("${BaseColumns._ID}=?", arrayOf(id))
            else -> Pair(selection, selectionArgs)
        }

        return tabela?.consulta(
            projection as Array<String>,
            selecao,
            argsSel as Array<String>?,
            null,
            null,
            sortOrder)
    }
    override fun getType(uri: Uri): String? {
        val endereco = uriMatcher().match(uri)

        return when(endereco) {
            URI_CATEGORIAS -> "vnd.android.cursor.dir/$CATEGORIAS"
            URI_CATEGORIA_ID -> "vnd.android.cursor.item/$CATEGORIAS"
            URI_CAO -> "vnd.android.cursor.dir/$CAO"
            URI_CAO_ID -> "vnd.android.cursor.item/$CAO"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val bd = bdOpenHelper!!.writableDatabase

        val endereco = uriMatcher().match(uri)
        val tabela = when (endereco) {
            URI_CATEGORIAS -> TabelaCategorias(bd)
            URI_CAO -> TabelaCao(bd)
            else -> return null
        }

        val id = tabela.insere(values!!)
        if (id == -1L) {
            return null
        }

        return Uri.withAppendedPath(uri, id.toString())
    }
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val bd = bdOpenHelper!!.writableDatabase

        val endereco = uriMatcher().match(uri)
        val tabela = when (endereco) {
            URI_CATEGORIA_ID -> TabelaCategorias(bd)
            URI_CAO_ID -> TabelaCao(bd)
            else -> return 0
        }

        val id = uri.lastPathSegment!!
        return tabela.elimina("${BaseColumns._ID}=?", arrayOf(id))
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val bd = bdOpenHelper!!.writableDatabase

        val endereco = uriMatcher().match(uri)
        val tabela = when (endereco) {
            URI_CATEGORIA_ID -> TabelaCategorias(bd)
            URI_CAO_ID -> TabelaCao(bd)
            else -> return 0
        }

        val id = uri.lastPathSegment!!
        return tabela.altera(values!!, "${BaseColumns._ID}=?", arrayOf(id))
    }
    companion object{
        private const val AUTORIDADE = "pt.ipg.cao"
        const val CATEGORIAS = "categorias"
        const val CAO = "cao"

        private const val URI_CATEGORIAS = 100
        private const val URI_CATEGORIA_ID = 101
        private const val URI_CAO = 200
        private const val  URI_CAO_ID = 201

        private val ENDERECO_BASE = Uri.parse("content://$AUTORIDADE")

        val ENDERECO_CATEGORIAS = Uri.withAppendedPath(ENDERECO_BASE, CATEGORIAS)
        val ENDERECO_CAO = Uri.withAppendedPath(ENDERECO_BASE, CAO)

        fun uriMatcher() = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTORIDADE, CATEGORIAS, URI_CATEGORIAS)
            addURI(AUTORIDADE, "$CATEGORIAS/#", URI_CATEGORIA_ID)
            addURI(AUTORIDADE, CAO, URI_CAO)
            addURI(AUTORIDADE, "$CAO/#", URI_CAO_ID)
        }
        /*
            content://pt.ipg.cao/top10cao
         */
    }




}