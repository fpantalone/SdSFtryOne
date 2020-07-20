package be.technifutur.devmob9.sdsftryone.webservice

import java.util.LinkedList
import java.util.function.Consumer

@FunctionalInterface
interface DataReader : Consumer<LinkedList<DataReader>> {

    companion object {

        fun start(nextReaders: LinkedList<DataReader>) {
            nextReaders.poll()?.let {
                it.accept(nextReaders)
            }
        }
    }

    fun callNext(nextReaders: LinkedList<DataReader>) {
        nextReaders.poll()?.let {
            it.accept(nextReaders)
        }
    }
}