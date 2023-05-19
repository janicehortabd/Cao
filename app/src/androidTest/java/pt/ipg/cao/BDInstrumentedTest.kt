package pt.ipg.cao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

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

            val categoria = Categoria("Humor")
            insereCategoria(bd, categoria)

            val cao1 = Cao("O Lixo na Minha Cabeça", categoria.id)
            insereLivro(bd, cao1)

            val cao2 = Cao("Novíssimas crónicas da boca do inferno", categoria.id, "9789896711788")
            insereLivro(bd, cao2)
        }

        private fun insereLivro(bd: SQLiteDatabase, cao: Cao) {
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

            val livro1 = Livro("Todos os Contos", categoria.id)
            insereLivro(bd, livro1)

            val dataPub = Calendar.getInstance()
            dataPub.set(2016, 4, 1)

            val livro2 = Livro("Contos de Grimm", categoria.id, "978-1473683556", dataPub)
            insereLivro(bd, livro2)

            val tabelaLivros = TabelaLivros(bd)

            val cursor = tabelaLivros.consulta(
                TabelaLivros.CAMPOS,
                "${BaseColumns._ID}=?",
                arrayOf(livro1.id.toString()),
                null,
                null,
                null
            )

            assert(cursor.moveToNext())

            val livroBD = Livro.fromCursor(cursor)

            assertEquals(livro1, livroBD)

            val cursorTodosLivros = tabelaLivros.consulta(
                TabelaLivros.CAMPOS,
                null, null, null, null,
                TabelaLivros.CAMPO_TITULO
            )

            assert(cursorTodosLivros.count > 1)
        }
    }

    @Test
    fun consegueAlterarCategorias()  {
        val bd = getWritableDatabase()

        val categoria = Categoria("Maine", "Pequeno", "Branco")
        insereCategoria(bd,categoria)

        categoria.nomeRaca = "Poesia"
        //Adicionar restantes campos


        val registosAlterados = TabelaCategorias(bd).altera(
            categoria.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(categoria.id.toString()))


        assertEquals(1, registosAlterados)

    }


    @Test
    fun consegueAlterarLivros()  {
        val bd = getWritableDatabase()

        val categoriaJuvenil = Categoria("Literatura Infanto-Juvenil")
        insereCategoria(bd,categoriaJuvenil)

        val categoria2 = Categoria("Literatura Nacional")
        insereCategoria(bd,categoria2)

        val dataNasc= Calendar.getInstance()
        dataNasc.set(2023, 2, 1)
        val gato = Gato("Stella", "Branco/Preto", "Femea", dataNasc, 3, 1.1 ,"Andre Godinho", "Rua Raul de Matos" , "Medio", categoria2.id)
        insereLivro(bd,gato)


        val novaDataNasc = Calendar.getInstance()
        novaDataNasc.set(2023,2,2)


        gato.idCategoria = categoriaJuvenil.id
        gato.nome = "Bloom"
        gato.dataNascimento = novaDataNasc
        //Adicionar restantes campos


        val registosAlterados = TabelaGatos(bd).alterar(
            gato.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(gato.id.toString()))


        assertEquals(1, registosAlterados)

    }

    @Test

    fun consegueApagarCategorias() {
        val bd = getWritableDatabase()

        val categoria = Categoria('...')
        insereCategoria(bd,categoria)

        categoria.descricao = "Poesia"
        //Adicionar restantes campos


        val registosAlterados = TabelaCategorias(bd).eliminar(
            "${BaseColumns._ID}=?",
            arrayOf(categoria.id.toString()))


        assertEquals(1, registosAlterados)
    }

    @Test

    fun consegueApagarLivros() {
        val bd = getWritableDatabase()

        val categoria = Categoria("Terror")
        insereCategoria(bd,categoria)

        val novaDataNasc = Calendar.getInstance()
        novaDataNasc.set(2023,2,2)

        val gato = Gato("Literatura Nacional", categoria.id)
        insereLivro(bd,gato)

        //Adicionar restantes campos


        val registosAlterados = TabelaGatos(bd).eliminar(
            "${BaseColumns._ID}=?",
            arrayOf(gato.id.toString()))


        assertEquals(1, registosAlterados)
    }




}