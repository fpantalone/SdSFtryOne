package be.technifutur.devmob9.sdsftryone.adapter

import be.technifutur.devmob9.sdsftryone.model.MatchData

interface HomeMatchCellClickListener {

    fun matchCellLongClicked (matchId: Int)
    fun matchCellClicked (matchId: Int)

}