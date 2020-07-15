package be.technifutur.devmob9.sdsftryone

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import be.technifutur.devmob9.sdsftryone.webservice.WebService
import be.technifutur.devmob9.sdsftryone.webservice.TableUpdateTimes
import be.technifutur.devmob9.sdsftryone.webservice.WebServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()



    }
}
