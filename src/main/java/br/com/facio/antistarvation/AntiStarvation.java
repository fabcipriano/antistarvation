package br.com.facio.antistarvation;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author fabiano
 */
public class AntiStarvation {
    
    private static Logger LOG = LogManager.getLogger();
    private static boolean IS_RUNNING = true;
        

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Customer> queue = new PriorityBlockingQueue<>();
        
        LOG.info("Starting ...");
        
        ExecutorService executorService = new ThreadPoolExecutor(3, 3, 60l, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        
        queue.add(new Customer(20, "Renata", new Date()));
        queue.add(new Customer(10, "Fabiano", new Date()));
        queue.add(new Customer(9, "Cecilia", new Date()));
        queue.add(new Customer(2, "Claudia", new Date()));
        queue.add(new Customer(3, "Beatriz", new Date()));
        queue.add(new Customer(4, "Lucas", new Date()));
        queue.add(new Customer(1, "Eleusa", new Date()));
        for (int i = 0; i < 1000; i++) {
            queue.add(new Customer(10, "Fabiano", new Date()));
            Thread.sleep(100);
            queue.add(new Customer(1, "Eleusa", new Date()));
        }
        
        initializePool(executorService, queue);

        Thread.sleep(60000);
        IS_RUNNING = false;
        executorService.shutdownNow();
        
        LOG.info("Started.");
        
    }

    private static void initializePool(ExecutorService executorService, final BlockingQueue<Customer> queue) {
        for (int i = 0; i < 3; i++) {
            executorService.submit(() -> {
                try {
                    while (IS_RUNNING) {                        
                        Customer c = queue.take();
                        c.run();
                    }
                } catch (InterruptedException ex) {
                    LOG.error("failed to execute", ex);
                }
            });
        }
    }
    
}
