package be.technifutur.devmob9.sdsftryone.adapter

import android.view.View
import be.technifutur.devmob9.sdsftryone.R
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem

class HomeItemAdapter() : AbstractItem<HomeItemAdapter.HomeViewHolder>() {
    override val layoutRes: Int
        get() = R.layout.calendar_row
    override val type: Int
        get() = R.id.champTextView
    override fun getViewHolder(v: View): HomeItemAdapter.HomeViewHolder {
        return HomeViewHolder(v)
    }


    class HomeViewHolder(view: View) : FastAdapter.ViewHolder<HomeItemAdapter>(view) {
        override fun bindView(item: HomeItemAdapter, payloads: List<Any>) {
            TODO("Not yet implemented")
        }

        override fun unbindView(item: HomeItemAdapter) {
            TODO("Not yet implemented")
        }
    }
}