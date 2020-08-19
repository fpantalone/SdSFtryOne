package be.technifutur.devmob9.sdsftryone.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.adapter.CalendarAdapter
import be.technifutur.devmob9.sdsftryone.dao.DbManager
import kotlinx.android.synthetic.main.fragment_calendar.*

class CalendarFragment: BaseFragment() {

    val args: CalendarFragmentArgs by navArgs()

    val adapter by lazy {
        val dataFeed = DbManager.getCalendar(args.teamName)
        return@lazy CalendarAdapter (dataFeed)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = getString(R.string.team_name_format)

        setHasOptionsMenu(true)
        activity?.title = String.format(title, args.teamName)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        activity?.actionBar?.setDisplayShowHomeEnabled(true)
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

       calendarRecyclerView.layoutManager = LinearLayoutManager(context)
        calendarRecyclerView.adapter = adapter
    }
}
