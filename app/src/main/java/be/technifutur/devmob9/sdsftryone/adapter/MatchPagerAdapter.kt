package be.technifutur.devmob9.sdsftryone.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import be.technifutur.devmob9.sdsftryone.fragment.MatchConfigFragment
import be.technifutur.devmob9.sdsftryone.fragment.MatchEventFragment
import be.technifutur.devmob9.sdsftryone.fragment.MatchManagerFragment
import be.technifutur.devmob9.sdsftryone.fragment.TabContainerFragment

class MatchPagerAdapter (fragment: TabContainerFragment, private var tabCount: Int): FragmentStateAdapter(fragment){

    override fun getItemCount(): Int {
        return tabCount
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            1 ->  MatchEventFragment()
            2 ->  MatchConfigFragment()
            else ->  MatchManagerFragment()
        }
    }
}