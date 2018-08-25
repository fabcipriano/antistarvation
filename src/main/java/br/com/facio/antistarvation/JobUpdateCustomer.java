package br.com.facio.antistarvation;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author fabiano
 */
public class JobUpdateCustomer implements Runnable {
    private static Logger LOG = LogManager.getLogger();
    
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
    
    Integer id;
    Date updateAt;
    

    public JobUpdateCustomer(Integer id) {
        this.id = id;
        this.updateAt = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return updateAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.updateAt = createdAt;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            LOG.error("interrupted", ex);
        }
        LOG.debug("executed - {} .", this);
    }

    @Override
    public String toString() {
        return "Event Update Customer{id=" + id + ", updateAt=" + sdf.format(updateAt) + '}';
    }

}
