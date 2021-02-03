package teambiscochito.toptrumpsgame.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <h2 align="center">Team Biscochito</h2><hr>
 * <p>
 * Clase para crear e instanciar los hilos, con un máximo de hilos de 4.
 */
public class UtilThread {

    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService threadExecutorPool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
}