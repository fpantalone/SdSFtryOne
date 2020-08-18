package be.technifutur.devmob9.sdsftryone.adapter

import android.view.View
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.model.MatchData
import be.technifutur.devmob9.sdsftryone.tools.TeamSide
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import kotlinx.android.synthetic.main.calendar_match_row.view.*

class FastCalendarAdapter(val data: MatchData) :
    AbstractItem<FastCalendarAdapter.CalendarViewHolder>() {

    override val layoutRes: Int
        get() = R.layout.calendar_match_row
    override val type: Int
        get() = R.id.calendarHomeTeamImageView

    override fun getViewHolder(v: View): CalendarViewHolder {
        return CalendarViewHolder(v)
    }

    // Inner class ViewHolder
    class CalendarViewHolder(view: View) : FastAdapter.ViewHolder<FastCalendarAdapter>(view) {

        val homeLogo = view.calendarHomeTeamImageView
        val awayLogo = view.calendarAwayTeamImageView
        val homeName = view.calendarHomeTextView
        val awayName = view.calendarAwayTextView
        val liveCalView = view.CalendarLiveLinLayout
        val homeScore = view.calendarHomeScoreTextWiew
        val awayScore = view.calendarAwayScoreTextView
        val homePenalty = view.calendarHomeShootOutTextView
        val awayPenalty = view.calendarAwayShootoutTextView
        val matchTime = view.calendarMatchTimeTextView


        override fun bindView(item: FastCalendarAdapter, payloads: List<Any>) {

            with(item) {
                if (null != data.getTeam(TeamSide.HOME).logo) {
                    Glide.with(homeLogo)
                        .load(data.getTeam(TeamSide.HOME).getLogoURL())
                        .into(homeLogo)
                } else {
                    homeLogo.setImageResource(R.drawable.default_logo)
                }

                if (null != data.getTeam(TeamSide.AWAY).logo) {
                    Glide.with(awayLogo)
                        .load(data.getTeam(TeamSide.AWAY).getLogoURL())
                        .into(awayLogo)
                } else {
                    homeLogo.setImageResource(R.drawable.default_logo)
                }

                homeName.text = data.getTeam(TeamSide.HOME).fullName
                awayName.text = data.getTeam(TeamSide.AWAY).fullName

                data.homeScore.let { homeScore.text = it.toString() }
                data.awayScore.let { awayScore.text = it.toString() }
            }
        }


        override fun unbindView(item: FastCalendarAdapter) {
            homeLogo.setImageBitmap(null)
            awayLogo.setImageBitmap(null)
            homeName.text = ""
            awayName.text = ""
            liveCalView.visibility = View.INVISIBLE
            homeScore.text = ""
            awayScore.text = ""
            homePenalty.text = ""
            homePenalty.visibility = View.GONE
            awayPenalty.text = ""
            awayPenalty.visibility = View.GONE
            matchTime.visibility = View.INVISIBLE
            matchTime.text = ""
        }
    }
}