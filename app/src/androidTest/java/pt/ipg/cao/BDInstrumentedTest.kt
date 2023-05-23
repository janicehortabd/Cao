package pt.ipg.cao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BDInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("pt.ipg.cao", appContext.packageName)
    }

    class BdInstrumentedTest {
        private fun getAppContext(): Context =
            InstrumentationRegistry.getInstrumentation().targetContext

        @Before
        fun apagaBaseDados() {
            getAppContext().deleteDatabase(BdCaoOpenHelper.NOME_BASE_DADOS)
        }

        @Test
        fun consegueAbrirBaseDados() {
            val openHelper = BdCaoOpenHelper(getAppContext())
            val bd = openHelper.readableDatabase
            assert(bd.isOpen)
        }

        private fun getWritableDatabase(): SQLiteDatabase {
            val openHelper = BdCaoOpenHelper(getAppContext())
            return openHelper.writableDatabase
        }

        @Test
        fun consegueInserirCategorias() {
            val bd = getWritableDatabase()

            val categoria = Categoria("Drama")
            insereCategoria(bd, categoria)
        }

        private fun insereCategoria(
            bd: SQLiteDatabase,
            categoria: Categoria
        ) {
            categoria.id = TabelaCategorias(bd).insere(categoria.toContentValues())
            assertNotEquals(-1, categoria.id)
        }

        @Test
        fun consegueInserirLivros() {
            val bd = getWritableDatabase()

            val categoria = Categoria("Cães de caça")
            insereCategoria(bd, categoria)

            val cao1 = Cao("Pastor Alemão", categoria.id)
            insereCao(bd, cao1)

            val cao2 =
                Cao("Labrador Retriever", categoria.id, "7117889789896")
            insereCao(bd, cao2)
        }

        private fun insereCao(bd: SQLiteDatabase, cao: Cao) {
            cao.id = TabelaCao(bd).insere(cao.toContentValues())
            assertNotEquals(-1, cao.id)
        }

        @Test
        fun consegueLerCategorias() {
            val bd = getWritableDatabase()

            val categRomance = Categoria("Romance")
            insereCategoria(bd, categRomance)

            val categFiccao = Categoria("Ficção Científica")
            insereCategoria(bd, categFiccao)

            val tabelaCategorias = TabelaCategorias(bd)

            val cursor = tabelaCategorias.consulta(
                TabelaCategorias.CAMPOS,
                "${BaseColumns._ID}=?",
                arrayOf(categFiccao.id.toString()),
                null,
                null,
                null
            )

            assert(cursor.moveToNext())

            val categBD = Categoria.fromCursor(cursor)

            assertEquals(categFiccao, categBD)

            val cursorTodasCategorias = tabelaCategorias.consulta(
                TabelaCategorias.CAMPOS,
                null, null, null, null,
                TabelaCategorias.CAMPO_DESCRICAO
            )

            assert(cursorTodasCategorias.count > 1)
        }

        @Test
        fun consegueLerLivros() {
            val bd = getWritableDatabase()

            val categoria = Categoria("Contos")
            insereCategoria(bd, categoria)

            val cao1 = Cao("Todos os Contos", categoria.id)
            insereCao(bd, cao1)

            val dataNascimento = Calendar.getInstance()
            dataNascimento.set(2016, 4, 1)

            val cao2 = Cao("Contos de Grimm", categoria.id, "978-1473683556", dataNascimento)
            insereCao(bd, cao2)

            val tabelaCao = TabelaCao(bd)

            val cursor = tabelaCao.consulta(
                TabelaCao.CAMPOS,
                "${BaseColumns._ID}=?",
                arrayOf(cao1.id.toString()),
                null,
                null,
                null
            )

            assert(cursor.moveToNext())

            val caoBD = Cao.fromCursor(cursor)

            assertEquals(cao1, caoBD)

            val cursorTodosLivros = tabelaCao.consulta(
                TabelaCao.CAMPOS,
                null, null, null, null,
                TabelaCao.CAMPO_RACA
            )

            assert(cursorTodosLivros.count > 1)
        }

    }
}