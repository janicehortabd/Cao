package pt.ipg.cao

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class AdapterCao(val fragment: ListaCaoFragment) : RecyclerView.Adapter<AdapterCao.ViewHolderCao>() {
    var cursor: Cursor? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolderCao(contentor: View) : ViewHolder(contentor) {
        private val textViewTitulo = contentor.findViewById<TextView>(R.id.textViewTitulo)
        private val textViewCategoria = contentor.findViewById<TextView>(R.id.textViewCategoria)

        internal var cao: Cao? = null
            set(value) {
                field = value
                textViewTitulo.text = cao?.raca ?: ""
                textViewCategoria.text = cao?.idCategoria.toString() ?: ""
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCao {
        return ViewHolderCao(
            fragment.layoutInflater.inflate(R.layout.item_cao, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return cursor?.count ?:0
    }


    override fun onBindViewHolder(holder: ViewHolderCao, position: Int) {
        cursor!!.moveToPosition(position)
        holder.cao = Cao.fromCursor(cursor!!)
    }




}