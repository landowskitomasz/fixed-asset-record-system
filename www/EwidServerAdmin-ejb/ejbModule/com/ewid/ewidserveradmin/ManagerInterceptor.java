package com.ewid.ewidserveradmin;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.interceptor.InvocationContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.intercept.AroundInvoke;
import org.jboss.seam.annotations.intercept.Interceptor;
import org.jboss.seam.intercept.JavaBeanInterceptor;

import com.ewid.ewidmanagers.client.exceptions.EwidRuntimeException;

@Name("managerInterceptor")
@Interceptor(around = { JavaBeanInterceptor.class })
public class ManagerInterceptor implements Serializable {

    private static final long serialVersionUID = 2908547443022709382L;

    @AroundInvoke
    public Object aroundInvoke(InvocationContext invocation) throws Exception {
       try {
	       InitialContext ctx = new InitialContext();
	       Object target = invocation.getTarget();
	       for (Field field : target.getClass().getDeclaredFields()) {
	           if (field.isAnnotationPresent(Manager.class)) {
	              field.setAccessible(true);
	              Object value = field.get(target);
	              if (value == null) {
                      try {
                          String jndiName = (String) field.getType().getField("JNDI_NAME").get(null);
                          Object manager = ctx.lookup(jndiName);
                          field.set(target, manager);
                      } catch (NoSuchFieldException e) {
                    	  throw new EwidRuntimeException("Cannot inject Manager into field: " + target.getClass().getCanonicalName() + "." + field.getName() + ". Interface doesn't have JNDI_NAME field.", e);
                      } catch (NamingException e) {
                    	  throw new EwidRuntimeException("Cannot inject Manager into field: " + target.getClass().getCanonicalName() + "." + field.getName() + ". Lookup failed", e);
                      }
	              }
	              field.setAccessible(false);
	           }
	       }
       	} catch (Exception e) {
                       throw new EwidRuntimeException("Unexpected ManagerInterceptor error", e);
        }
       	return invocation.proceed();
    }
}
