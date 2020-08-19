package be.technifutur.devmob9.sdsftryone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.model.MatchData
import kotlinx.android.synthetic.main.calendar_header_row.view.*
import java.text.SimpleDateFormat
import java.util.*

class CalendarAdapter (val data: List<MatchData>): RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    val dayMonthFormat = SimpleDateFormat ("dd/MM", Locale.ROOT)
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun getItemCount(): Int {
        return data.size

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.calendar_header_row,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val journee = data[position].day?.firstOrNull()
        val champ  = journee?.champ?.firstOrNull()

        with (holder) {
            calChampDate.text = dayMonthFormat.format(data[position].getMatchDate())
            calChampType.text = champ?.name
            calDay.text = journee?.getName(Locale.getDefault())
            // mettre l'adapter pour la seconde recyclerView

            val subLayoutManager = LinearLayoutManager(itemView.calendarMatchRecyclerView.context,RecyclerView.VERTICAL, false)

            itemView.calendarMatchRecyclerView.apply {
                layoutManager = subLayoutManager
                addItemDecoration(DividerItemDecoration(context,RecyclerView.VERTICAL))
                adapter = SubCalendarAdapter(data[position])
                setRecycledViewPool(viewPool)
            }
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

       val calChampDate = view.dateChampTextView
       val calChampType = view.champTypeTextView
       val calDay = view.CalandarDayTextView
    }
}