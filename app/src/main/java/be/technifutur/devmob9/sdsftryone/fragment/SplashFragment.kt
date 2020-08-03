package be.technifutur.devmob9.sdsftryone.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
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
import kotlinx.coroutines.delay
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

        translateStandart()


        // TODO Faire le webservice dans un autre process
        // TODO mettre l'animation dans un autre process
    }

    fun translateStandart () {

        val animSet = AnimationSet(true)

        val translateAnim = TranslateAnimation (0f,0f, 600f, 0f)
        translateAnim.duration = 2000
        animSet.addAnimation(translateAnim)

        val alphaAnim = AlphaAnimation(0.0f, 1.0f)
        alphaAnim.duration = 2000
        animSet.addAnimation(alphaAnim)

        standardTextView.startAnimation(animSet)

        translateDeLiege ()

    }

    fun translateDeLiege () {

        val animSet = AnimationSet(true)

        val translateAnim = TranslateAnimation (0f,0f, 600f, 0f)
        translateAnim.duration = 2000
        translateAnim.startOffset = 500
        animSet.addAnimation(translateAnim)

        val alphaAnim = AlphaAnimation (0.0f, 1.0f)
        alphaAnim.duration = 2000
        alphaAnim.startOffset = 500
        animSet.addAnimation(alphaAnim)

        deLiegeTextView.startAnimation(animSet)

        fadeFemina()

    }

    fun fadeFemina () {
        val fadeAnim = AlphaAnimation (1.0f, 0.0f)
        fadeAnim.duration = 3000
        fadeAnim.startOffset = 500
        animMaskLayout.startAnimation(fadeAnim)
    }

}
