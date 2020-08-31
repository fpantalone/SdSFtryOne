package be.technifutur.devmob9.sdsftryone.adapter

import android.view.View
import be.technifutur.devmob9.sdsftryone.R
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import kotlinx.android.synthetic.main.event_mngr_row.view.*
import kotlinx.android.synthetic.main.fragment_match_event.view.*

class MatchEventAdapter (var eventLabel: String): AbstractItem<MatchEventAdapter.ViewHolder>() {

    override val layoutRes: Int
        get() = R.layout.event_mngr_row
    override val type: Int
        get() = R.id.emRowHomeGoalcounterTextView

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

    class ViewHolder(view: View): FastAdapter.ViewHolder<MatchEventAdapter>(view){

        var homeCoumter = view.emRowHomeGoalcounterTextView
        var centerLabel = view.emRowGoalEventLabel
        var awayCounter = view.emRowAwayGoalCounter

        override fun bindView(item: MatchEventAdapter, payloads: List<Any>) {
            homeCoumter.text = "0"
            centerLabel.text = item.eventLabel
            awayCounter.text = "0"
        }

        override fun unbindView(item: MatchEventAdapter) {
            homeCoumter.text = ""
            centerLabel.text = ""
            awayCounter.text = ""
        }
    }
}