package be.technifutur.devmob9.sdsftryone.fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.adapter.FastCalendarAdapter
import be.technifutur.devmob9.sdsftryone.dao.DbManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.coroutines.channels.Channel

class CalendarFragment: Fragment() {

    val args: CalendarFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.setActionBar(calendarToolBar)
        calendarToolBar?.let { it.title = "Title" }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // tous les matches de l'équi dans l'ordre chronologique, pour les matchs qui sont isinweek afficher tous les matchs de la journée même si c'est pas ceux du standard

        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataFeed = DbManager.getCalendar(args.teamName)
        val itemAdapter = ItemAdapter<FastCalendarAdapter>()
        itemAdapter.add( dataFeed.map { FastCalendarAdapter(it) })

        val fastAdapter = FastAdapter.with(itemAdapter)

        calendarRecyclerView.adapter = fastAdapter
        calendarRecyclerView.layoutManager = LinearLayoutManager (context)
    }
}
