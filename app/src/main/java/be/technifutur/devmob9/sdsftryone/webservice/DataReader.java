package be.technifutur.devmob9.sdsftryone.webservice;

import java.util.LinkedList;
import java.util.function.Consumer;

/**
 * Interface fonctionnelle déclarée en Java pour pouvoir être utilisée comme lambda sou Kotlin
 */

@FunctionalInterface
public interface DataReader extends Consumer<LinkedList<DataReader>> {
    default void callNext (LinkedList<DataReader> nextReaders) {
        DataReader next = nextReaders.poll();
        if (null != next) {
            next.accept(nextReaders);
        }
    }

    static void start(LinkedList<DataReader> nextReaders) {
        DataReader next = nextReaders.poll();
        if (null != next) {
            next.accept(nextReaders);
        }
    }
}
