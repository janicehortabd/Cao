package pt.ipg.cao

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import pt.ipg.cao.databinding.FragmentListaCaoBinding

private const val ID_LOADER_CAO = 0


class ListaCaoFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentListaCaoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaCaoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private var adapterCao: AdapterCao? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterCao = AdapterCao(this)
        binding.recyclerViewCao.adapter = adapterCao
        binding.recyclerViewCao.layoutManager = LinearLayoutManager(requireContext())

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_CAO, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            CaoContentProvider.ENDERECO_CAO,
            TabelaCao.CAMPOS,
            null, null,
            TabelaCao.CAMPO_RACA
        )
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterCao!!.cursor = null
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterCao!!.cursor = data
    }
}