package be.technifutur.devmob9.sdsftryone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import be.technifutur.devmob9.sdsftryone.R
import kotlinx.android.synthetic.main.fragment_calendar.*

class CalendarFragment: Fragment() {

    val args: CalendarFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // activity?.setActionBar(calendarToolBar)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        // tpous les matches de l'équi dans l'ordre chronologique, pour les matchs qui sont isinweek afficher tous les matchs de la journée même si c'est pas ceux du standard

        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }
}
