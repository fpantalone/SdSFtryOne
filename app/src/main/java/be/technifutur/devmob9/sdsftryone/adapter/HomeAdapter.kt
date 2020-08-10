package be.technifutur.devmob9.sdsftryone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.model.MatchData
import kotlinx.android.synthetic.main.team_row.view.*

class HomeAdapter (val teamHeading: ArrayList<String>,
                   val feedMatchList: Map<String, List<MatchData>>,
                   val clickListener: View.OnClickListener
):RecyclerView.Adapter<HomeAdapter.ViewHoldert>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun getItemCount(): Int {
        return teamHeading.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoldert {
        return ViewHoldert(LayoutInflater.from(parent.context).inflate(R.layout.team_row,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHoldert, position: Int) {
        holder.itemView.tag = position
        holder.itemView.setOnClickListener(clickListener)
        holder.teamName?.text = teamHeading[position]

        val subLayoutManager = LinearLayoutManager(holder.itemView.matchRecyclerView.context, RecyclerView.VERTICAL, false)

        holder.itemView.matchRecyclerView.apply {
            layoutManager = subLayoutManager
            // il faut envoyer une liste de match au sous adapter
            //feedMatchList.get(teamHeading[position])
           adapter = SubHomeAdater(feedMatchList[teamHeading[position]]?.filter { it.isInWeek() } ?: listOf())
            setRecycledViewPool(viewPool)
        }
    }

    // ViewHolder Inner Class
    class ViewHoldert (view: View): RecyclerView.ViewHolder(view){
        val teamName = view.teamName
    }
}