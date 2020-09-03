package be.technifutur.devmob9.sdsftryone.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.dao.DbManager
import be.technifutur.devmob9.sdsftryone.webservice.AllTable
import be.technifutur.devmob9.sdsftryone.webservice.WebService
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_splash.*

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment() {

    companion object {
        const val SPLASH_SCREEN_DURATION: Int = 5000
    }

    private var animationFinished = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //activity?.title = "Live Score"


        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //WebService.clearDbSyncTime()

        startAnimations()

        // Lecture des données
        WebService.getAllData(
            { data: AllTable ->
                DbManager.updateData(data, Consumer { success ->
                    if (success) {
                        WebService.updateDbSyncTime()
                    }
                })
                // attend la fin de l'animation
                while (!animationFinished) {
                    try {
                        Thread.sleep(10)
                    }
                    catch (ex: InterruptedException) {}
                }

                // passe à l'écran d'accueil
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    //val navController = Navigation.findNavController(view)
                    //navController.navigate(R.id.homeFragment)
                    val direction = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                    val options = NavOptions.Builder().setLaunchSingleTop(true).build()
                    val navController = findNavController()
                    navController.navigate(direction, options)
                }
            },
            { error: Throwable ->
                if (error is IllegalStateException) {
                    alertBox(R.string.network_error_title, R.string.networ_error_msg)
                }
                else {
                        alertBox(R.string.db_error_title, R.string.db_error_msg)
                }
                // dans les deux cas la boîte de dialogue propose de "Continuer" avec les données actuelles (à droite)
                // ou de "Réessayer" de charger les données (à gauche).
            }
        )
    }

    fun startAnimations () {
        val translateY = 600f

        val firstLineAnimationSet = AnimationSet(true)

        val flScaleAnim = ScaleAnimation(0.01f, 1f, 0.01f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        flScaleAnim.duration = 2000
        firstLineAnimationSet.addAnimation(flScaleAnim)

        val flTranslateAnim = TranslateAnimation (0f,0f, translateY, 0f)
        flTranslateAnim.duration = 2000
        firstLineAnimationSet.addAnimation(flTranslateAnim)

        val flAlphaAnim = AlphaAnimation(0.0f, 1.0f)
        flAlphaAnim.duration = 2000
        firstLineAnimationSet.addAnimation(flAlphaAnim)

        standardTextView.startAnimation(firstLineAnimationSet)

        val secondLineAnimationSet = AnimationSet(true)

        val slScaleAnim = ScaleAnimation(0.01f,1f, 0.01f,1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        slScaleAnim.duration = 2000
        slScaleAnim.startOffset = 400
        secondLineAnimationSet.addAnimation(slScaleAnim)

        val slTranslateAnim = TranslateAnimation (0f,0f, translateY, 0f)
        slTranslateAnim.duration = 2000
        slTranslateAnim.startOffset = 400
        secondLineAnimationSet.addAnimation(slTranslateAnim)

        val slAlphaAnim = AlphaAnimation (0.0f, 1.0f)
        slAlphaAnim.duration = 2000
        slAlphaAnim.startOffset = 400
        secondLineAnimationSet.addAnimation(slAlphaAnim)

        deLiegeTextView.startAnimation(secondLineAnimationSet)

        val maskAnim = AlphaAnimation (1.0f, 0.0f)
        maskAnim.duration = 1000
        maskAnim.startOffset = 1600
        maskAnim.fillAfter = true
        maskAnim.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}
            override fun onAnimationRepeat(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                animationFinished = true
            }
        })
        animMaskLayout.startAnimation(maskAnim)
    }

    fun alertBox (title: Int, message: Int) {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle(title)
        dialog.setMessage(message)
        dialog.setPositiveButton(R.string.retryDialog, DialogInterface.OnClickListener { _, _ ->
            view?.let { Navigation.findNavController(it) }?.navigate(R.id.homeFragment)
        })
        dialog.setNegativeButton(R.string.cancelDialog, DialogInterface.OnClickListener { _, _ ->
            view?.let { Navigation.findNavController(it) }?.navigate(R.id.homeFragment)

        })
        dialog.show()
    }
}