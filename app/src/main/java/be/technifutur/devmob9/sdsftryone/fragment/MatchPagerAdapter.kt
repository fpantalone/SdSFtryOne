package be.technifutur.devmob9.sdsftryone.fragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MatchPagerAdapter (fragment: Fragment, private var tabCount: Int): FragmentStateAdapter(fragment){

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