package be.technifutur.devmob9.sdsftryone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.dao.DbManager
import kotlinx.android.synthetic.main.fragment_match_manager.*


class MatchManagerFragment :Fragment() {


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

        // lien entre l'AutoCompleteTextView et la liste des joueuses
        searchPlayerSearchView.setAdapter(ArrayAdapter (requireContext(), android.R.layout.simple_list_item_1, playersList))
        // on récupère la joueuse sélectionée dans la liste
        val selected = searchPlayerSearchView.text.toString()

        mmAddPlayerButton.setOnClickListener {
            if (selected.isNotBlank()) {
                // texte pas vide recherche des infos dans la DB pour mettre la liste à jour
                // et créer un MatchPlayerData ! mettre à jour l'id

            }
        }
    }
}
