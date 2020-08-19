package be.technifutur.devmob9.sdsftryone.fragment


import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundColor(Color.WHITE)
        view.isClickable = true
    }
}