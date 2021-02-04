/*
 * Copyright (c) 2021. Team Biscochito.
 *
 * Licensed under the GNU General Public License v3.0
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 *
 * Permissions of this strong copyleft license are conditioned on making available complete
 * source code of licensed works and modifications, which include larger works using a licensed
 * work, under the same license. Copyright and license notices must be preserved. Contributors
 * provide an express grant of patent rights.
 */

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