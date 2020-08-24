package be.technifutur.devmob9.sdsftryone.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import be.technifutur.devmob9.sdsftryone.R
import kotlinx.android.synthetic.main.fragment_tab_container.*


class TabContainerFragment : BaseFragment() {


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

        // lien bottomNavigationView et NavController
        val navController = findNavController()

        NavigationUI.setupWithNavController(bottomNavView, navController)


        bottomNavView.setOnNavigationItemReselectedListener { item ->

            when (item.itemId) {
                R.id.matchManagerFragment -> {
                    true
                }
                R.id.matchEventFragment -> {
                    true
                }
                R.id.matchConfigFragment -> {
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}
