package pt.ipg.cao

import android.database.Cursor
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class AdapterCao: RecyclerView.Adapter<AdapterCao.ViewHolderCao>() {
    var cursor: Cursor? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return cursor?.count ?:0
    }
    inner class ViewHolderCao(itemView: View) : ViewHolder(itemView){

    }

}