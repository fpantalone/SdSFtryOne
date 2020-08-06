package be.technifutur.devmob9.sdsftryone.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.adapter.HomeAdapter
import be.technifutur.devmob9.sdsftryone.dao.DbManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    lateinit var preferedTeam: ArrayList<String>

    val adapter by lazy {
        HomeAdapter(preferedTeam, View.OnClickListener {
            val pos = it?.tag as Int
            // envoyer la liste des matchs
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
        // rechercher la listes des matches par équipes et l'envoyer à l'adapteur
        // comme ça on le fais qu'une seulez fois

        calendarRecyclerView.layoutManager = LinearLayoutManager(context)
        calendarRecyclerView.adapter = adapter
    }
}



