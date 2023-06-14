package pt.ipg.cao

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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

        init {
            contentor.setOnClickListener{
                viewHolderSeleccionado?.desSeleciona()
                seleciona()
            }
        }

        internal var cao: Cao? = null
            set(value) {
                field = value
                textViewTitulo.text = cao?.raca ?: ""
                textViewCategoria.text = cao?.idCategoria.toString() ?: ""
            }
        fun seleciona(){
            viewHolderSeleccionado = this
            fragment.caoSelecionado = cao
            itemView.setBackgroundResource(R.color.item_selecionado)
        }
        fun desSeleciona(){
            itemView.setBackgroundResource(android.R.color.white)
        }
    }
    private var viewHolerSeleccionado : ViewHolderCao? = null

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