package be.technifutur.devmob9.sdsftryone.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.technifutur.devmob9.sdsftryone.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_tab_container.*


class TabContainerFragment : Fragment() {


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
        // récuperation du string-array
        val tabTitleArray = resources.getStringArray(R.array.match_tad_txt)
        // adapter pour le ViewPager
        val matchPagerAdapter = MatchPagerAdapter(this, tabTitleArray.size)
        matchViewPager.adapter = matchPagerAdapter
        // lien entre le Viewpager et le tabLayout
        TabLayoutMediator(matchTabLayout, matchViewPager) { tab, position ->
            tab.text = tabTitleArray[position].substringBefore(' ')
        }.attach()

    }

    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment TabContainerFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            TabContainerFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
    }
}