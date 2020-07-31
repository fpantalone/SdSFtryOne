package be.technifutur.devmob9.sdsftryone.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.webservice.WebService
import kotlinx.android.synthetic.main.fragment_splash.*

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment() {

    companion object {
        const val SPLASH_SCREEN_DURATION: Int = 5000
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_splash, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        StandardTranslater()
        deLiegeTranslater()
        animMaskFader()

        WebService.updateDataBase(findNavController())

        //this.testTextView.text = DbManager.sharedInstance().getAllClubData().size.toString()
    }


    fun StandardTranslater () {
        val animator = ObjectAnimator.ofFloat(standardTextView, View.TRANSLATION_Y, -150f)
        animator.repeatCount = 1
        animator.duration = 2000
        animator.start()
    }

    fun deLiegeTranslater() {
        val animator = ObjectAnimator.ofFloat(deLiegeTextView, View.TRANSLATION_Y, -150f)
        animator.repeatCount = 1
        animator.duration = 2000
        animator.startDelay = 800
        animator.start()
    }

    fun animMaskFader () {
        val animator = ObjectAnimator.ofFloat(animMaskLayout, View.ALPHA, 0f)
        animator.repeatCount = 1
        animator.duration = 1000
        animator.startDelay = 1600
        animator.start()
    }
}
