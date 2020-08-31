package be.technifutur.devmob9.sdsftryone.adapter

import android.view.View
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.model.PlayerData
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import kotlinx.android.synthetic.main.mm_player_row.view.*

class MmPlayerListAdapter (var player: PlayerData) : AbstractItem<MmPlayerListAdapter.ViewHolder>() {

    override val layoutRes: Int
        get() = R.layout.mm_player_row
    override val type: Int
        get() = R.id.mmPlayerNumberTextView

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

    class ViewHolder(view: View): FastAdapter.ViewHolder<MmPlayerListAdapter>(view) {

        var numPlayer = view.mmPlayerNumberTextView
        var namePlayer = view.mmPlayerNameTextView
        var selected = view.mmSelectPlayerChechBox
        var capKeep = view.mmcapKeepTextView
        var delPlayer = view.mmRemovePlayerButton

        override fun bindView(item: MmPlayerListAdapter, payloads: List<Any>) {
            numPlayer.setText(item.player.number.toString())
            namePlayer.text = item.player.fullName
        }

        override fun unbindView(item: MmPlayerListAdapter) {
            numPlayer.text.clear()
            namePlayer.text = ""
            selected.isChecked = false
            capKeep.text = ""
        }
    }
}