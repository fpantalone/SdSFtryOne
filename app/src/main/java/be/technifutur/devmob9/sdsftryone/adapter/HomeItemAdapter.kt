package be.technifutur.devmob9.sdsftryone.adapter

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem

open class HomeItemAdapter : AbstractItem<HomeItemAdapter.HomeViewHolder>() {
    override val layoutRes: Int
        get() = TODO("Not yet implemented")
    override val type: Int
        get() = TODO("Not yet implemented")

    override fun getViewHolder(v: View): HomeItemAdapter.HomeViewHolder {
        TODO("Not yet implemented")
    }


    class HomeViewHolder(view: View) : FastAdapter.ViewHolder<HomeItemAdapter>(view) {
        override fun bindView(itemAdapter: HomeItemAdapter, payloads: List<Any>) {
            TODO("Not yet implemented")
        }

        override fun unbindView(itemAdapter: HomeItemAdapter) {
            TODO("Not yet implemented")
        }

    }
}