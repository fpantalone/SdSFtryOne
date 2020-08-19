package be.technifutur.devmob9.sdsftryone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.model.MatchData
import be.technifutur.devmob9.sdsftryone.tools.TeamSide
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.calendar_match_row.view.*

class SubCalendarAdapter (val match: MatchData) : RecyclerView.Adapter<SubCalendarAdapter.ViewHolder>() {

    private var data = listOf<MatchData>()
    init {
        if (match.isInWeek()) {
            data = match.day?.firstOrNull()?.matches ?: listOf()
        }
        else {
            data = listOf(match)
        }
    }

    override fun getItemCount() = data.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.calendar_match_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {
            if (null != data[position].getTeam(be.technifutur.devmob9.sdsftryone.tools.TeamSide.HOME).logo) {
                Glide.with(holder.homeLogo)
                    .load(data[position].getTeam(TeamSide.HOME).getLogoURL())
                    .into(holder.homeLogo)
            } else {
                holder.homeLogo.setImageResource(be.technifutur.devmob9.sdsftryone.R.drawable.default_logo)
            }

            if (null != data[position].getTeam(be.technifutur.devmob9.sdsftryone.tools.TeamSide.AWAY).logo) {
                com.bumptech.glide.Glide.with(holder.awayLogo)
                    .load(
                        data[position].getTeam(be.technifutur.devmob9.sdsftryone.tools.TeamSide.AWAY)
                            .getLogoURL()
                    )
                    .into(holder.awayLogo)
            } else {
                holder.homeLogo.setImageResource(be.technifutur.devmob9.sdsftryone.R.drawable.default_logo)
            }

            holder.homeName.text =
                data[position].getTeam(be.technifutur.devmob9.sdsftryone.tools.TeamSide.HOME).fullName
            holder.awayName.text =
                data[position].getTeam(be.technifutur.devmob9.sdsftryone.tools.TeamSide.AWAY).fullName

            if (null == data[position].homeScore && null == data[position].awayScore) {
                homeScore.visibility = View.INVISIBLE
                awayScore.visibility = View.INVISIBLE
                matchTime.text = data[position].hour
                matchTime.visibility = View.VISIBLE
            } else {
                homeScore.text = data[position].homeScore.toString()
                homeScore.visibility = View.VISIBLE
                awayScore.text = data[position].awayScore.toString()
                awayScore.visibility = View.VISIBLE
            }

            if (null == data[position].getComment()?.homePenalty && null == data[position].getComment()?.awayPenalty) {
                homePenalty.visibility = View.GONE
                awayPenalty.visibility = View.GONE
            }
            else {
                homePenalty.visibility = View.VISIBLE
                homePenalty.text = data[position].getComment()?.homePenalty.toString()
                awayPenalty.visibility = View.VISIBLE
                awayPenalty.text = data[position].getComment()?.awayPenalty.toString()
            }
        }
    }

    class ViewHolder (view: View): RecyclerView.ViewHolder (view) {

        val homeLogo: ImageView = view.calendarHomeTeamImageView
        val awayLogo: ImageView = view.calendarAwayTeamImageView
        val homeName: TextView = view.calendarHomeTextView
        val awayName: TextView = view.calendarAwayTextView
        val liveCalView: LinearLayout = view.CalendarLiveLinLayout
        val homeScore:TextView = view.calendarHomeScoreTextWiew
        val awayScore:TextView = view.calendarAwayScoreTextView
        val homePenalty: TextView = view.calendarHomeShootOutTextView
        val awayPenalty: TextView = view.calendarAwayShootoutTextView
        val matchTime: TextView = view.calendarMatchTimeTextView

    }

}