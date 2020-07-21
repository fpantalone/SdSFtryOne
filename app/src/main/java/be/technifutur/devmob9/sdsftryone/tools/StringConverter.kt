package be.technifutur.devmob9.sdsftryone.tools

import java.text.SimpleDateFormat
import java.util.*

class StringConverter {

    companion object {
        private lateinit var dateFormat: SimpleDateFormat
        private lateinit var dateTimeFormat: SimpleDateFormat
        private lateinit var nameCommentConverter: NameCommentConverter
        private lateinit var eventParamConverter: EventParamConverter
        private lateinit var matchConfigConverter: MatchConfigConverter

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

        fun getNameCommentConverter(): ConverterInterface<NameComment> {
            if (!(this::nameCommentConverter.isInitialized)) {
                nameCommentConverter = NameCommentConverter()
            }
            return nameCommentConverter
        }

        fun getEventParamConverter(): ConverterInterface<EventParam> {
            if (!(this::eventParamConverter.isInitialized)) {
                eventParamConverter = EventParamConverter()
            }
            return eventParamConverter
        }

        fun getMatchConfigConverter(): ConverterInterface<MatchConfig> {
            if (!(this::matchConfigConverter.isInitialized)) {
                matchConfigConverter = MatchConfigConverter()
            }
            return matchConfigConverter
        }
    }

    interface ConverterInterface<T> {
        fun parse(string: String): T
        fun format(t: T): String
    }
}