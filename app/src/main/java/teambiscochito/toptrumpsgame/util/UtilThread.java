package teambiscochito.toptrumpsgame.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UtilThread {

    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();

    public static final ExecutorService threadExecutorPool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

}