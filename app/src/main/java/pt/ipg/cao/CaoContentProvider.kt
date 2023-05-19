package pt.ipg.cao

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class CaoContentProvider : ContentProvider(){
    private var bdCaoOpenHelper: BdCaoOpenHelper? = null
    override fun onCreate(): Boolean {
        bdOpenHelper = BdCaoOpenHelper(context);
        return true
        TODO("Not yet implemented")
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }
    companion object{
         private const val AUORIDADE = "pt.ipg.cao"
        const val CATEGORIAS = "categorias"
        const val CAO = "cao"

        private const val URI_CATEGORIAS = 100
        private const val URI_CAO = 200

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