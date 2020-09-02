package be.technifutur.devmob9.sdsftryone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.model.MatchData
import kotlinx.android.synthetic.main.team_row.view.*
import java.util.*
import kotlin.collections.ArrayList

class HomeAdapter (val teamHeading: ArrayList<String>,
                   val feedMatchList: Map<String, List<MatchData>>,
                   val clickListener: View.OnClickListener,
                   val subCellClickListener: HomeMatchCellClickListener
):RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun getItemCount(): Int {
        return teamHeading.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.team_row,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = position
        holder.itemView.setOnClickListener(clickListener)
        val format = holder.teamName.resources.getString(R.string.team_name_format)
        holder.teamName?.text = String.format(format, teamHeading[position])

        val subLayoutManager = LinearLayoutManager(holder.itemView.matchRecyclerView.context, RecyclerView.VERTICAL, false)

        holder.itemView.matchRecyclerView.apply {
            layoutManager = subLayoutManager
            // il faut envoyer une liste de match au sous adapter
            var matchList: ArrayList<MatchData> = arrayListOf()
            var lastInWeek = false
            var nextFound = false
            val now = Date()
            feedMatchList[teamHeading[position]]?.forEach { match ->
                if (match.getMatchDate() < now) {
                    if (!lastInWeek) {
                        matchList.clear()
                        matchList.add(match)
                        lastInWeek = match.isInWeek()
                    }
                    else {
                        matchList.add(match)
                    }
                }
                else if (match.isInWeek() || !nextFound) {
                    matchList.add(match)
                    nextFound = true
                }
            }
            adapter = SubHomeAdapter(matchList, subCellClickListener)
            setRecycledViewPool(viewPool)
        }
    }

    // ViewHolder Inner Class
    class ViewHolder (view: View): RecyclerView.ViewHolder(view){
        val teamName = view.teamName
    }
}