package be.technifutur.devmob9.sdsftryone.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import androidx.fragment.app.Fragment
import be.technifutur.devmob9.sdsftryone.R
import kotlinx.android.synthetic.main.fragment_splash.*
import java.util.ArrayList

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

        Handler().postDelayed({
            animMaskLayout.visibility = View.INVISIBLE
        },1000L)


        // TODO Faire le webservice dans un autre process

    }

    fun translateStandart () {

        val animSet = AnimationSet(true)

        val translateAnim = TranslateAnimation (0f,0f, 600f, 0f)
        translateAnim.duration = 2000
        animSet.addAnimation(translateAnim)

        val alphaAnim = AlphaAnimation(0.0f, 1.0f)
        alphaAnim.duration = 2000
        animSet.addAnimation(alphaAnim)

        val scaleAnim = ScaleAnimation(1f,1f, 0f,1f)
        scaleAnim.duration = 2000
        animSet.addAnimation(scaleAnim)

        standardTextView.startAnimation(animSet)

        translateDeLiege ()

    }

    fun translateDeLiege () {

        val animSet = AnimationSet(true)

        val translateAnim = TranslateAnimation (0f,0f, 600f, 0f)
        translateAnim.duration = 2000
        translateAnim.startOffset = 400
        animSet.addAnimation(translateAnim)

        val alphaAnim = AlphaAnimation (0.0f, 1.0f)
        alphaAnim.duration = 2000
        alphaAnim.startOffset = 400
        animSet.addAnimation(alphaAnim)

        val scaleAnim = ScaleAnimation(1f,1f, 0f,1f)
        scaleAnim.duration = 2000
        scaleAnim.startOffset = 400
        animSet.addAnimation(scaleAnim)

        deLiegeTextView.startAnimation(animSet)

        fadeFemina()
    }

    fun fadeFemina () {
        val fadeAnim = AlphaAnimation (1.0f, 0.0f)
        fadeAnim.duration = 2000
        fadeAnim.startOffset = 2000
        animMaskLayout.startAnimation(fadeAnim)
    }

}
