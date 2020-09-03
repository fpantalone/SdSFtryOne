package be.technifutur.devmob9.sdsftryone.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import be.technifutur.devmob9.sdsftryone.R
import kotlinx.android.synthetic.main.fragment_tab_container.*


class TabContainerFragment : Fragment() {

    val args: TabContainerFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = requireActivity().findNavController(R.id.tab_nav_host_fragment)
        NavigationUI.setupWithNavController(tabContainerBottomNavView, navController)

        tabContainerToolBar.setupWithNavController(navController)

    }
}
