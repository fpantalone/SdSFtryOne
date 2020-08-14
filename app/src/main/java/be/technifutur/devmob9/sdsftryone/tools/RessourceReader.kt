package be.technifutur.devmob9.sdsftryone.tools

import android.content.Context

class RessourceReader {

    companion object {
        private lateinit var context: Context

       fun init (context: Context){
            RessourceReader.context = context
        }

        fun getString (ressourceId: Int): String {
            return context.getString(ressourceId)
        }

        fun getString (ressourceId: Int, vararg args: Any): String {
            return context.getString(ressourceId, *args)
        }
    }
}