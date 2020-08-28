package be.technifutur.devmob9.sdsftryone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val navController = findNavController(R.id.nav_host_fragment)
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        val toolbar = findViewById<Toolbar>(R.id.theToolbar)
        // setupWithNavController(appBarConfiguration, navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.navigation.nav_graph)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}
