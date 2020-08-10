package be.technifutur.devmob9.sdsftryone.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.adapter.HomeAdapter
import be.technifutur.devmob9.sdsftryone.dao.DbManager
import be.technifutur.devmob9.sdsftryone.model.MatchData
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    var preferedTeam: ArrayList<String> = arrayListOf()

    val adapter by lazy {
        var matchMap: MutableMap<String, List<MatchData>> = mutableMapOf()
        preferedTeam.forEach { team ->
                matchMap[team] = DbManager.getCalendar(team)
            print (matchMap)
        }
        return@lazy HomeAdapter(preferedTeam, matchMap, View.OnClickListener {
            val navController = Navigation.findNavController(it)
            val direction = HomeFragmentDirections.actionHomeFragmentToCalendarFragment2()
            navController.navigate(direction)        })
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

        // récupérer la liste des équipe sélectionée dans les préférences
        getPreferedTeam()
        // rechercher la listes des matches par équipes et l'envoyer à l'adapteur
        // comme ça on le fais qu'une seulez fois

        homeRecyclerView.layoutManager = LinearLayoutManager(context)
        homeRecyclerView.adapter = adapter
    }

    fun getPreferedTeam() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        val keyFormat = "team_%s_prefs"
        arrayOf("A", "B", "C", "U16", "U14", "U13", "U12", "U11").forEach { team ->
            val key = String.format(keyFormat, team.toLowerCase())
            if (prefs.getBoolean(key, true)) {
                preferedTeam.add(team)
            }
        }
    }
}



