package be.technifutur.devmob9.sdsftryone.tools

import java.text.SimpleDateFormat
import java.util.*

class StringConverter {

    companion object {
        private lateinit var dateFormat: SimpleDateFormat
        private lateinit var dateTimeFormat: SimpleDateFormat

        fun getDateConverter(): SimpleDateFormat {
            if (!(this::dateFormat.isInitialized)) {
                dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            }
            return dateFormat
        }

        fun getDateTimeConverter(): SimpleDateFormat {
            if (!(this::dateTimeFormat.isInitialized)) {
                dateTimeFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
            }
            return dateTimeFormat
        }
    }
}