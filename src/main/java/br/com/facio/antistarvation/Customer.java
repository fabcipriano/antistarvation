package br.com.facio.antistarvation;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author fabiano
 */
public class Customer implements Comparable<Customer>, Runnable {
    private static Logger LOG = LogManager.getLogger();
    
    Integer id;
    String name;
    Date createdAt;
    boolean executedAtLeastOnce;
    Long priority;

    public Customer(Integer id, String name, Date createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        executedAtLeastOnce = false;
        priority = calculePriority(id);
    }

    private long calculePriority(Integer id1) {
        return System.currentTimeMillis() + (id1 * 1000);
    }
        

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int compareTo(Customer o) {
        //return id.compareTo(o.getId());
        
        /* calculo abaixo evita starvation */
        return priority.compareTo(o.priority);
    }

    @Override
    public void run() {
        executedAtLeastOnce = true;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
        LOG.debug("executed Customer - id={}, name={}, date={} .", id, name, createdAt);
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name=" + name + ", createdAt=" + createdAt + ", executedAtLeastOnce=" + executedAtLeastOnce + '}';
    }

}
