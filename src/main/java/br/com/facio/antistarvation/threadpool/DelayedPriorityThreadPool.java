package br.com.facio.antistarvation.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author fabiano
 */
public class DelayedPriorityThreadPool extends ThreadPoolExecutor {

    static class DeleyableFutureTask<V> extends FutureTask<V> implements Runnable, Comparable<DeleyableFutureTask<V>> {

        private Long priority;

        public DeleyableFutureTask(Callable<V> callable, long delayMilliseconds) {
            super(callable);
            priority = calculatePriority(delayMilliseconds);
        }

        public DeleyableFutureTask(Runnable runnable, V result, long delayMilliseconds) {
            super(runnable, result);
            priority = calculatePriority(delayMilliseconds);
        }

        @Override
        public int compareTo(DeleyableFutureTask<V> other) {
            return priority.compareTo(other.priority);
        }

        private Long calculatePriority(long delayMilliseconds) {
            return System.currentTimeMillis() + delayMilliseconds;
        }

    }

    public DelayedPriorityThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
            PriorityBlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public DelayedPriorityThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
            PriorityBlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public DelayedPriorityThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
            PriorityBlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    public DelayedPriorityThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, PriorityBlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public <T> Future<T> submit(Runnable task, T result, long delayMs) {
        if (task == null) {
            throw new NullPointerException();
        }
        RunnableFuture<T> ftask = newTaskFor(task, result, delayMs);
        execute(ftask);
        return ftask;
    }

    public <T> Future<T> submit(Callable<T> task, long delayMs) {
        if (task == null) {
            throw new NullPointerException();
        }
        RunnableFuture<T> ftask = newTaskFor(task, delayMs);
        execute(ftask);
        return ftask;
    }

    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable, long delayMs) {
        return new DeleyableFutureTask(callable, delayMs);
    }

    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value, long delayMs) {
        return new DeleyableFutureTask(runnable, value, delayMs);
    }

}
