package be.technifutur.devmob9.sdsftryone.adapter

import be.technifutur.devmob9.sdsftryone.model.MatchData

interface HomeMatchCellClickListener {

    fun matchCellLongClicked (match: MatchData)
    fun matchCellClicked (match: MatchData)

}