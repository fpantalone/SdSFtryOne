package be.technifutur.devmob9.sdsftryone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.adapter.MatchEventAdapter
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import kotlinx.android.synthetic.main.fragment_match_event.*


class MatchEventFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val eventArray = arrayListOf(getString(R.string.em_foul),
            getString(R.string.em_offside), getString(R.string.em_corner),
            getString(R.string.em_center_shoot), getString(R.string.em_out_shoot),
            getString(R.string.em_change), getString(R.string.em_card))

        super.onViewCreated(view, savedInstanceState)

        val eventItemAdapter = ItemAdapter<MatchEventAdapter>()
        eventItemAdapter.add(eventArray.map { MatchEventAdapter(it) })
        val eventFastAdapter = FastAdapter.with(eventItemAdapter)
        emRecyclerview.adapter = eventFastAdapter
        emRecyclerview.layoutManager = LinearLayoutManager(requireContext())
    }
}