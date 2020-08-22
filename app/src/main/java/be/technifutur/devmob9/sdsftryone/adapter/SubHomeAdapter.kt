package be.technifutur.devmob9.sdsftryone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.fragment.HomeFragment
import be.technifutur.devmob9.sdsftryone.fragment.HomeFragmentDirections
import be.technifutur.devmob9.sdsftryone.model.MatchData
import be.technifutur.devmob9.sdsftryone.tools.LockStatus
import be.technifutur.devmob9.sdsftryone.tools.MatchStatus
import be.technifutur.devmob9.sdsftryone.tools.TeamSide
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.home_finish_row.view.*
import kotlinx.android.synthetic.main.home_live_row.view.*
import kotlinx.android.synthetic.main.home_row.view.*
import java.text.SimpleDateFormat
import java.util.*

class SubHomeAdapter(val data: List<MatchData>, val listener: HomeMatchCellClickListener) :
    RecyclerView.Adapter<SubHomeAdapter.SubViewHolder>() {

    companion object {
        private val dateFormat = SimpleDateFormat("E. dd MMM", Locale.ROOT)
        private const val FUTURE_TYPE = 1
        private const val LIVE_TYPE = 2
        private const val PLAYED_TYPE = 3
    }

    override fun getItemCount()= data.count()

    // !! afficher les matchs qui sont dans le range -7 +6 par rapport à la date du jour !!
    override fun onBindViewHolder(holder: SubViewHolder, position: Int) {
        holder.bind(data[position], listener)
    }

    override fun getItemViewType(position: Int): Int {
        val match = data[position]
        val comment = match.getComment()

        if (comment?.status == MatchStatus.LIVE)
            return LIVE_TYPE

        if (null != match.homeScore && null != match.awayScore)
            return PLAYED_TYPE

        return FUTURE_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {

        LIVE_TYPE -> {
            LiveMatchViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.home_live_row, parent, false)
            )
        }
        PLAYED_TYPE -> {
            PlayedMatchViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.home_finish_row, parent, false)
            )
        }
        else -> FuturMatchViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_row, parent, false)
        )
    }

    abstract class SubViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        abstract fun bind(match: MatchData, listener: HomeMatchCellClickListener)
    }

    class FuturMatchViewHolder(view: View) : SubViewHolder(view) {
        val champName = view.champTextView
        val champDay = view.champDayTextView
        val team_A_Logo = view.team_a_imageView
        val team_A_Name = view.team_a_TextView
        val team_B_Logo = view.team_b_imageView
        val team_B_Name = view.team_b_TextView
        val matchDate = view.homeDateTextView
        val valmatchHour = view.homeHourTextView
        val locker = view.lockImageView
        val clickView = view.titleView

        override fun bind(match: MatchData, listener: HomeMatchCellClickListener) {

            val day = match.day?.firstOrNull()
            val champ = day?.champ?.firstOrNull()

            if (match.isInWeek()) {
                champName.text = champ?.name
                champDay.text = day?.getName(Locale.getDefault())

                if (null != match.getTeam(TeamSide.HOME).logo) {
                    Glide.with(team_A_Logo)
                        .load(match.getTeam(TeamSide.HOME).getLogoURL())
                        .into(team_A_Logo)
                } else {
                    team_A_Logo.setImageResource(R.drawable.default_logo)
                }

                team_A_Name.text = match.getTeam(TeamSide.HOME).fullName

                if (null != match.getTeam(TeamSide.AWAY).logo) {
                    Glide.with(team_B_Logo)
                        .load(match.getTeam(TeamSide.AWAY).getLogoURL())
                        .into(team_B_Logo)
                } else {
                    team_B_Logo.setImageResource(R.drawable.default_logo)
                }
                team_B_Name.text = match.getTeam(TeamSide.AWAY).fullName

                matchDate.text = dateFormat.format(match.getMatchDate())
                valmatchHour.text = match.hour

                when (match.getLockStatus()) {
                    LockStatus.OWNED -> {
                        locker.setImageResource(R.drawable.lock_24px)
                        locker.setColorFilter(R.color.dark_red)
                    }
                    LockStatus.CLOSED -> {
                        locker.setImageResource(R.drawable.lock_24px)
                    }
                    LockStatus.OPEN -> {
                        locker.setImageResource(R.drawable.lock_open_24px)
                    }
                    else -> {
                        locker.visibility = View.INVISIBLE
                    }
                }

                clickView.setOnClickListener{ listener.matchCellLongClicked(match) }
            }
        }
    }

    class LiveMatchViewHolder(view: View) : SubViewHolder(view) {

        val champName = view.liveChampTextView
        val champDay = view.liveChampDayTextView
        val homeLogo = view.liveHomeTeamImageView
        val homeName = view.liveHomeTeamTextView
        val awayLogo = view.liveAwayTeamImageView
        val awayName = view.liveAwayTeamTextView
        val homeScore = view.liveHomeScoreTextView
        val awayScore = view.liveAwayScoreTextView
        val locker = view.liveLockImageView
        val clickView = view.liveTitleView

        // TODO: mettre le timer
        override fun bind(match: MatchData, listener: HomeMatchCellClickListener) {

            val day = match.day?.firstOrNull()
            val champ = day?.champ?.firstOrNull()

            champName.text = champ?.name
            champDay.text = day?.getName(Locale.getDefault())

            if (null != match.getTeam(TeamSide.HOME).logo) {
                Glide.with(homeLogo)
                    .load(match.getTeam(TeamSide.HOME).getLogoURL())
                    .into(homeLogo)
            } else {
                homeLogo.setImageResource(R.drawable.default_logo)
            }

            homeName.text = match.getTeam(TeamSide.HOME).fullName

            if (null != match.getTeam(TeamSide.AWAY).logo) {
                Glide.with(awayLogo)
                    .load(match.getTeam(TeamSide.AWAY).getLogoURL())
                    .into(awayLogo)
            } else {
                awayLogo.setImageResource(R.drawable.default_logo)
            }
            awayName.text = match.getTeam(TeamSide.AWAY).fullName

            homeScore.text = match.homeScore.toString()
            awayScore.text = match.awayScore.toString()

            when (match.getLockStatus()) {
                LockStatus.OWNED -> {
                    locker.setImageResource(R.drawable.lock_24px)
                    locker.setColorFilter(R.color.dark_red)
                }
                LockStatus.CLOSED -> {
                    locker.setImageResource(R.drawable.lock_24px)
                }
                LockStatus.OPEN -> {
                    locker.setImageResource(R.drawable.lock_open_24px)
                }
                else -> {
                    locker.visibility = View.INVISIBLE
                }
            }

            clickView.setOnClickListener { listener.matchCellLongClicked(match) }

            // ToDO: !!!! AffichageLive !!!!
        }
    }

    class PlayedMatchViewHolder(view: View) : SubViewHolder(view) {

        val champName = view.finishChampTextView
        val champDay = view.finishChampDayTextView
        val date = view.finishDateTextView
        val homeLogo = view.finishHomeTeamImageView
        val homeName = view.finishHomeTeamTextView
        val awayLogo = view.finisAwayTeamImageView
        val awayName = view.finishAwayTeamTextView
        val homeScore = view.finisHomeScoreTextView
        val awayScore = view.finishAwayScoreTextView
        val locker = view.finishLockImageView
        val clickView = view.finishTitleView

        override fun bind(match: MatchData, listener: HomeMatchCellClickListener) {

            val day = match.day?.firstOrNull()
            val champ = day?.champ?.firstOrNull()

            if (match.isInWeek()) {
                champName.text = champ?.name
                champDay.text = day?.getName(Locale.getDefault())

                date.text = dateFormat.format(match.getMatchDate())

                if (null != match.getTeam(TeamSide.HOME).logo) {
                    Glide.with(homeLogo)
                        .load(match.getTeam(TeamSide.HOME).getLogoURL())
                        .into(homeLogo)
                } else {
                    homeLogo.setImageResource(R.drawable.default_logo)
                }

                homeName.text = match.getTeam(TeamSide.HOME).fullName

                if (null != match.getTeam(TeamSide.AWAY).logo) {
                    Glide.with(awayLogo)
                        .load(match.getTeam(TeamSide.AWAY).getLogoURL())
                        .into(awayLogo)
                } else {
                    awayLogo.setImageResource(R.drawable.default_logo)
                }
                awayName.text = match.getTeam(TeamSide.AWAY).fullName

                homeScore.text = match.homeScore.toString()
                awayScore.text = match.awayScore.toString()

                when (match.getLockStatus()) {
                    LockStatus.OWNED -> {
                        locker.setImageResource(R.drawable.lock_24px)
                        locker.setColorFilter(R.color.dark_red)
                    }
                    LockStatus.CLOSED -> {
                        locker.setImageResource(R.drawable.lock_24px)
                    }
                    LockStatus.OPEN -> {
                        locker.setImageResource(R.drawable.lock_open_24px)
                    }
                    else -> {
                        locker.visibility = View.INVISIBLE
                    }
                }
                clickView.setOnClickListener { listener.matchCellLongClicked(match) }
            }
        }
    }
}
