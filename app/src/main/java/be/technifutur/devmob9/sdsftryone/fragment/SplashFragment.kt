package be.technifutur.devmob9.sdsftryone.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.dao.DbManager
import be.technifutur.devmob9.sdsftryone.webservice.WebService
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment() {

    companion object{
        const val SPLASH_SCREEN_DURATION: Int = 3000
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

        WebService.updateDataBase(findNavController())

        //this.testTextView.text = DbManager.sharedInstance().getAllClubData().size.toString()
    }
}
