package be.technifutur.devmob9.sdsftryone.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.animation.addListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.adapter.HomeItemAdapter
import be.technifutur.devmob9.sdsftryone.webservice.WebService
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import kotlinx.android.synthetic.main.fragment_splash.*
import java.lang.Exception
import java.util.ArrayList
import kotlin.concurrent.thread

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment() {

    companion object {
        const val SPLASH_SCREEN_DURATION: Int = 5000
    }

    var listAnimationsEnded= ArrayList<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_splash, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        translationY(standardTextView, -150f, 2000, 0)
        translationY(deLiegeTextView, -150f,  2000, 400)
        animMaskFader(animMaskLayout, 1000, 1600)

        WebService.updateDataBase(findNavController())


        //this.testTextView.text = DbManager.sharedInstance().getAllClubData().size.toString()

        // TODO Faire le webservice dans un autre process
        // TODO mettre l'animation dans un autre process
    }


    fun translationY (view: View, distance: Float, duration: Long, delay: Long) {
        val animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, distance)
        animator.repeatCount = 1
        animator.duration = duration
        animator.startDelay = delay
        animator.start()

        val numberAnimation= listAnimationsEnded.size
        listAnimationsEnded.add(false)

        animator.addListener(onEnd = {
            listAnimationsEnded[numberAnimation] = true
        })

    }

    fun animMaskFader (view: View, duration: Long, delay: Long) {
        val animator = ObjectAnimator.ofFloat(view, View.ALPHA, 0f)
        animator.repeatCount = 1
        animator.duration = duration
        animator.startDelay = delay
        animator.start()

        val numberAnimation= listAnimationsEnded.size
        listAnimationsEnded.add(false)

        animator.addListener(onEnd = {
            listAnimationsEnded[numberAnimation] = true
        })
    }

    fun checkAnimationsEnded() : Boolean{
        var animationsEnded = true

        if(listAnimationsEnded.contains(false))
            animationsEnded = false

        return animationsEnded
    }
}
