package be.technifutur.devmob9.sdsftryone.adapter

import android.graphics.drawable.Drawable
import android.view.View
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.model.ChampData
import be.technifutur.devmob9.sdsftryone.model.MatchData
import be.technifutur.devmob9.sdsftryone.tools.LockStatus
import com.bumptech.glide.load.resource.drawable.DrawableDecoderCompat.getDrawable
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import kotlinx.android.synthetic.main.calendar_row.view.*

class HomeItemAdapter(var datafeed: MatchData) : AbstractItem<HomeItemAdapter.HomeViewHolder>() {

    override val layoutRes: Int
        get() = R.layout.calendar_row
    override val type: Int
        get() = R.id.champTextView

    override fun getViewHolder(v: View): HomeItemAdapter.HomeViewHolder {
        return HomeViewHolder(v)
    }


    class HomeViewHolder(itemView: View) : FastAdapter.ViewHolder<HomeItemAdapter>(itemView) {
        override fun bindView(item: HomeItemAdapter, payloads: List<Any>) {

            val rowData = item.datafeed

            itemView.champTextView.text = ""
            itemView.champDayTextView.text = ""
            itemView.team_a_imageView.setImageDrawable(null)
            itemView.team_a_TextView.text = ""
            itemView.team_b_imageView.setImageDrawable(null)
            itemView.team_b_TextView.text = ""
            itemView.date_timeTextView.text = ""

            when (rowData.getLockStatus()) {

                LockStatus.OWNED -> {

                }
                LockStatus.CLOSED -> {

                }
                else -> {
                   // itemView.lockImageView.setImageDrawable(getDrawable(itemView.context,R.drawable.lock_open_24px))

                }
            }


        }

        override fun unbindView(item: HomeItemAdapter) {

            itemView.champTextView.text = ""
            itemView.champDayTextView.text = ""
            itemView.team_a_imageView.setImageDrawable(null)
            itemView.team_a_TextView.text = ""
            itemView.team_b_imageView.setImageDrawable(null)
            itemView.team_b_TextView.text = ""
            itemView.date_timeTextView.text = ""
            itemView.lockImageView.setImageDrawable(null)


        }
    }
}