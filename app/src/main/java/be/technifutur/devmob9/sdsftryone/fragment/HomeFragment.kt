package be.technifutur.devmob9.sdsftryone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.technifutur.devmob9.sdsftryone.R

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    val team = arrayOf("équipe A","équipe B", "équipe C","U16","U14","U13","U12","U11")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}
