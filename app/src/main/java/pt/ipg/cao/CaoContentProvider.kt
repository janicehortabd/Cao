package pt.ipg.cao

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class CaoContentProvider : ContentProvider(){
    private var bdCaoOpenHelper: BdCaoOpenHelper? = null
    override fun onCreate(): Boolean {
        bdOpenHelper = BdCaoOpenHelper(context);
        return true
        TODO("Not yet implemented")
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortrOrder: String?
    ): Cursor? {
        val bd = bdCaoOpenHelper!!.readableDatabase

        val endereco = uriMatcher().match(uri)
        val tabela = when (endereco){
            URI_CATEGORIAS, URI_CATEGORIA_ID  -> TabelaCategorias(bd)
            URI_CAO, URI_CAO_ID -> TabelaCao(bd)
            else -> null
        }

        val selecao = when (endereco){
            URI_CATEGORIA_ID, URI_CAO_ID -> ("${BaseColumns._ID}=?", arrayOf(id))
            else -> Pair(selection, selectionArgs)
        }

        return tabela?.consulta(
                projection as Array<String>,
                selection ,
                argsSel =
                selectionArgs,
                null,
                null;
                sortrOrder)


        TODO("Not yet implemented")
    }
    companion object{
         private const val AUTORIDADE = "pt.ipg.cao"
        const val CATEGORIAS = "categorias"
        const val CAO = "cao"

        private const val URI_CATEGORIAS = 100
        private const val URI_CATEGORIA_ID = 101
        private const val URI_CAO = 200
        private const val  URI_CAO_ID = 201

         fun uriMatcher() = UriMatcher(UriMatcher.NO_MATCH).apply {}
        addURI(AUTORIDADE, CATEGORIAS,  100)
        addURI(AUTORIDADE, LIVROS,  100)
        /*
            content://pt.ipg.cao/top10cao
         */
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

}