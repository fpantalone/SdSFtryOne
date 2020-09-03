package be.technifutur.devmob9.sdsftryone.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.adapter.MmPlayerListAdapter
import be.technifutur.devmob9.sdsftryone.dao.DbManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import kotlinx.android.synthetic.main.fragment_match_manager.*


class MatchManagerFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_manager, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val playersList = DbManager.getAllPlayers()?.map { it.fullName }?.sorted() ?: arrayListOf()
//        val match = DbManager.findMatch(
//            TabContainerFragment().args.matchId,
//            TabContainerFragment().args.dayId,
//            TabContainerFragment().args.champId
//        )!!

//        val playerItem = ItemAdapter<MmPlayerListAdapter>()
//        playerItem.add(match.players.map { MmPlayerListAdapter(it) })
//        val playerFastAdapter = FastAdapter.with(playerItem)
//        selectedPlayerRecyclerview.adapter = playerFastAdapter
//        selectedPlayerRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        // lien entre l'AutoCompleteTextView et la liste des joueuses
        searchPlayerSearchView.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                playersList
            )
        )
        // on récupère la joueuse sélectionée dans la liste
        val selectedPlayer = searchPlayerSearchView.text.toString()


        mmAddPlayerButton.setOnClickListener {
//            if (selectedPlayer.isNotBlank()) {
//                // le texte n'est pas vide on ajoute la joueuse dans la Db et la RecyclerView
//                val newMp = DbManager.addMatchPlayer(match, selectedPlayer)
//            }
        }
    }
}
