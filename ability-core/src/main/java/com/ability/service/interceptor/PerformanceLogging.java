package com.ability.service.interceptor;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ikizema on 15-08-21.
 */

@Stateless
public class PerformanceLogging {

    public static final Logger LOG = Logger.getLogger(PerformanceLogging.class.getCanonicalName());

    @PostConstruct
    public void postConstruct() {
    }

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        long x = System.currentTimeMillis();
        Object result = ctx.proceed();
        long duration = System.currentTimeMillis() - x;
        if (duration > 100) {
            LOG.log(Level.INFO, "{0}.{1} finished in {2} msec", new Object[]{ctx.getClass(), ctx.getMethod(), System.currentTimeMillis() - x});
        } else if (duration > 1000) {
            LOG.log(Level.SEVERE, "{0}.{1} finished in {2} msec", new Object[]{ctx.getClass(), ctx.getMethod(), System.currentTimeMillis() - x});
        }
        return result;
    }

}
