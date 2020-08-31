package be.technifutur.devmob9.sdsftryone.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.adapter.HomeAdapter
import be.technifutur.devmob9.sdsftryone.adapter.HomeMatchCellClickListener
import be.technifutur.devmob9.sdsftryone.dao.DbManager
import be.technifutur.devmob9.sdsftryone.model.MatchData
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), HomeMatchCellClickListener {

    var preferedTeam: ArrayList<String> = arrayListOf()

    val adapter by lazy {
        val matchMap: MutableMap<String, List<MatchData>> = mutableMapOf()
        preferedTeam.forEach { team ->
            matchMap[team] = DbManager.getCalendar(team)
        }
        return@lazy HomeAdapter(preferedTeam, matchMap, View.OnClickListener {
            val pos = it?.tag as Int
            val title = String.format(getString(R.string.team_name_format, preferedTeam[pos]))

            val navController = Navigation.findNavController(it)
            val direction = HomeFragmentDirections.actionHomeFragmentToCalendarFragment2(
                title,
                preferedTeam[pos]
            )
            navController.navigate(direction)
        }, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        setupWithNavController(homeToolbar, navController, appBarConfiguration)

        // récupérer la liste des équipe sélectionée dans les préférences

        getPreferedTeam()

        homeRecyclerView.layoutManager = LinearLayoutManager(context)
        homeRecyclerView.adapter = adapter

    }

    fun getPreferedTeam() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        preferedTeam.clear()
        val keyFormat = "team_%s_prefs"
        arrayOf("A", "B", "C", "U16", "U14", "U13", "U12", "U11").forEach { team ->
            val key = String.format(keyFormat, team.toLowerCase())
            if (prefs.getBoolean(key, true)) {
                preferedTeam.add(team)
            }
        }
    }

    override fun matchCellLongClicked(matchId: Int) {
        // passer l'id du match plutôt que le MatchData complet, Realm object not parcelable !!!

        val navController = findNavController()
        val direction = HomeFragmentDirections.actionHomeFragmentToTabContainerFragment(matchId)
        navController.navigate(direction)
    }

    override fun matchCellClicked(matchId: Int) {
        // TODO Afficher le compte-rendu
  }

}



