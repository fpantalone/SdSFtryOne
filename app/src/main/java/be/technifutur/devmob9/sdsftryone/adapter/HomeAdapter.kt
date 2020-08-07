package be.technifutur.devmob9.sdsftryone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.devmob9.sdsftryone.R
import kotlinx.android.synthetic.main.team_row.view.*

class HomeAdapter (val teamHeading: ArrayList<String>,
                   val clickListener: View.OnClickListener
):RecyclerView.Adapter<HomeAdapter.VewHoldert>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun getItemCount(): Int {
        return teamHeading.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VewHoldert {
        return VewHoldert(LayoutInflater.from(parent.context).inflate(R.layout.team_row,parent,false))
    }

    override fun onBindViewHolder(holder: VewHoldert, position: Int) {
        holder.itemView.tag = position
        holder.itemView.setOnClickListener(clickListener)
        holder.teamName?.text = teamHeading[position]

        val subLayoutManager = LinearLayoutManager(holder.itemView.matchRecyclerView.context, RecyclerView.VERTICAL, false)

        holder.itemView.matchRecyclerView.apply {
            layoutManager = subLayoutManager
            // il faut envoyer une liste de match au sous adapter
           // adapter = SubHomeAdater(teamHeading[position])
            setRecycledViewPool(viewPool)
        }
    }

    // ViewHolder Inner Class
    class VewHoldert (view: View): RecyclerView.ViewHolder(view){
        val teamName = view.teamName
    }
}





//class HomeAdapter(var datafeed: MatchData) : AbstractItem<HomeAdapter.HomeViewHolder>() {
//
//    override val layoutRes: Int
//        get() = R.layout.home_row
//    override val type: Int
//        get() = R.id.champTextView
//
//    override fun getViewHolder(v: View): HomeAdapter.HomeViewHolder {
//        return HomeViewHolder(v)
//    }
//
//
//    class HomeViewHolder(itemView: View) : FastAdapter.ViewHolder<HomeAdapter>(itemView) {
//        override fun bindView(item: HomeAdapter, payloads: List<Any>) {
//
//            val rowData = item.datafeed
//
//            itemView.champTextView.text = ""
//            itemView.champDayTextView.text = ""
//            itemView.team_a_imageView.setImageDrawable(null)
//            itemView.team_a_TextView.text = ""
//            itemView.team_b_imageView.setImageDrawable(null)
//            itemView.team_b_TextView.text = ""
//            itemView.date_timeTextView.text = ""
//
//            when (rowData.getLockStatus()) {
//
//                LockStatus.OWNED -> {
//
//                }
//                LockStatus.CLOSED -> {
//
//                }
//                else -> {
//                   // itemView.lockImageView.setImageDrawable(getDrawable(itemView.context,R.drawable.lock_open_24px))
//
//                }
//            }
//
//
//        }
//
//        override fun unbindView(item: HomeAdapter) {
//
//            itemView.champTextView.text = ""
//            itemView.champDayTextView.text = ""
//            itemView.team_a_imageView.setImageDrawable(null)
//            itemView.team_a_TextView.text = ""
//            itemView.team_b_imageView.setImageDrawable(null)
//            itemView.team_b_TextView.text = ""
//            itemView.date_timeTextView.text = ""
//            itemView.lockImageView.setImageDrawable(null)
//
//
//        }
//    }
//}