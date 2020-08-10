package be.technifutur.devmob9.sdsftryone.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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

    lateinit var preferedTeam: ArrayList<String>

    val adapter by lazy {
        var matchMap: MutableMap<String, List<MatchData>> = mutableMapOf()
        preferedTeam.forEach { team ->
                matchMap[team] = DbManager.getCalendar(team)
        }
        return@lazy HomeAdapter(preferedTeam, matchMap, View.OnClickListener {
            val pos = it?.tag as Int
        })
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

        if (prefs.getBoolean("team_a_prefs", true)) preferedTeam.add(resources.getString(R.string.team_a_menu_label))
        if (prefs.getBoolean("team_b_prefs", true)) preferedTeam.add(resources.getString(R.string.team_b_menu_label))
        if (prefs.getBoolean("team_c_prefs", true)) preferedTeam.add(resources.getString(R.string.team_c_menu_label))
        if (prefs.getBoolean("team_u16_prefs", true)) preferedTeam.add(resources.getString(R.string.team_u16_menu_label))
        if (prefs.getBoolean("team_u14_prefs", true)) preferedTeam.add(resources.getString(R.string.team_u14_menu_label))
        if (prefs.getBoolean("team_u12_prefs", true)) preferedTeam.add(resources.getString(R.string.team_u12_menu_label))
        if (prefs.getBoolean("team_u11_prefs", true)) preferedTeam.add(resources.getString(R.string.team_u11_menu_label))

    }
}



