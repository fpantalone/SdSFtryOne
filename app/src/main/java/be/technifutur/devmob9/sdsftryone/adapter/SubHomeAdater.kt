package be.technifutur.devmob9.sdsftryone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.model.MatchData
import kotlinx.android.synthetic.main.home_row.view.*

class SubHomeAdater(val data: List<MatchData>) : RecyclerView.Adapter<SubHomeAdater.SubViewHolder>() {

    override fun getItemCount(): Int {
       return data.count()
    }

    // !! afficher les matchs qui sont dans le range -7 +6 par rapport à la date du jour !!
    override fun onBindViewHolder(holder: SubViewHolder, position: Int) {

        if (data[position].isInWeek()) {
            holder.championat.text = data[position].day?.get(0)?.name
            holder.ChampDay
            holder.team_A_Logo
            holder.team_A_Name
            holder.team_B_Logo
            holder.team_B_Name
            holder.matchate
            holder.locker
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubViewHolder {
        return SubViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_row, parent, false))
    }

    class SubViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val championat = view.champTextView
            val ChampDay = view.champDayTextView
            val team_A_Logo = view.team_a_imageView
            val team_A_Name = view.team_a_TextView
            val team_B_Logo = view.team_b_imageView
            val team_B_Name = view.team_b_TextView
            val matchate  = view.date_timeTextView
            val locker = view.lockImageView
    }


}
