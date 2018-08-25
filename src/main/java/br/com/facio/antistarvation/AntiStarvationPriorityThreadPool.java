package br.com.facio.antistarvation;

import br.com.facio.antistarvation.threadpool.DelayedPriorityThreadPool;
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
public class AntiStarvationPriorityThreadPool {
    
    private static Logger LOG = LogManager.getLogger();
    private static boolean IS_RUNNING = true;
        

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        LOG.info("Starting ...");
        
        DelayedPriorityThreadPool executorService = new DelayedPriorityThreadPool(50, 50, 60l, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());
        
        for (int i = 0; i < 1000; i++) {
            executorService.submit(new JobUpdateCustomer(10), null, 10000);
            executorService.submit(new JobUpdateCustomer(10), null, 10000);
            executorService.submit(new JobUpdateCustomer(10), null, 10000);
            executorService.submit(new JobUpdateCustomer(10), null, 10000);
            executorService.submit(new JobUpdateCustomer(10), null, 10000);
            executorService.submit(new JobUpdateCustomer(10), null, 10000);
            executorService.submit(new JobUpdateCustomer(7), null, 7000);
            executorService.submit(new JobUpdateCustomer(7), null, 7000);
            executorService.submit(new JobUpdateCustomer(7), null, 7000);
            executorService.submit(new JobUpdateCustomer(5), null, 5000);
            executorService.submit(new JobUpdateCustomer(5), null, 5000);
            executorService.submit(new JobUpdateCustomer(5), null, 5000);
            executorService.submit(new JobUpdateCustomer(5), null, 5000);
            executorService.submit(new JobUpdateCustomer(3), null, 3000);
            executorService.submit(new JobUpdateCustomer(3), null, 3000);
            executorService.submit(new JobUpdateCustomer(3), null, 3000);
            executorService.submit(new JobUpdateCustomer(3), null, 3000);
            executorService.submit(new JobUpdateCustomer(3), null, 3000);
            executorService.submit(new JobUpdateCustomer(3), null, 3000);
            executorService.submit(new JobUpdateCustomer(3), null, 3000);
            executorService.submit(new JobUpdateCustomer(1), null, 0);
            executorService.submit(new JobUpdateCustomer(1), null, 0);
            Thread.sleep(200);
        }
        
        Thread.sleep(120000);
        LOG.info("Started.");
        
    }
}
