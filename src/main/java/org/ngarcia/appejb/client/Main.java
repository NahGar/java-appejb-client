package org.ngarcia.appejb.client;

import org.ngarcia.webapp.ejb.models.Producto;
import org.ngarcia.webapp.ejb.service.ServiceEjbRemote;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;
import java.util.Properties;

public class Main {
   public static void main(String[] args) {

      ServiceEjbRemote service = null;

      final Properties env = new Properties();
      env.put(Context.INITIAL_CONTEXT_FACTORY,"org.jboss.naming.remote.client.InitialContextFactory");
      env.put(Context.PROVIDER_URL,"http-remoting://localhost:8080");
      env.put("jboss.naming,client.ejb.context",true);
      try {
         InitialContext ctx = new InitialContext(env);
         service = (ServiceEjbRemote) ctx.lookup("ejb:/appejb-remote/ServiceEjb!org.ngarcia.webapp.ejb.service.ServiceEjbRemote");

         String saludo = service.saludar("Horacio");
         String saludo2 = service.saludar("Micaela");
         System.out.println(saludo);
         System.out.println(saludo2);

         Producto p = service.crear(new Producto("peras"));
         System.out.println(p);

         service.listar().forEach(System.out::println);


      } catch (NamingException e) {
         e.printStackTrace();
      }
   }
}
