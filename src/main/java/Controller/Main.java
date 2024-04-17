/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Controller;

/**
 *
 * @author edith_000
 */

import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.catalina.webresources.DirResourceSet;

import java.io.File;
import org.apache.catalina.LifecycleException;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try{
            String webPort = System.getenv("JAVA_OPTS");
            if(webPort == null || webPort.isEmpty()) {
                webPort = "8080"; // Puerto por defecto en caso de que no esté definido por Heroku
            }

            Tomcat tomcat = new Tomcat();
            tomcat.setPort(Integer.parseInt(webPort));

            String webappDirLocation = "src/main/webapp/";
            StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());

            // Rutas alternativas para "WEB-INF/classes"
            File additionWebInfClassesFolder = new File("target/classes");
            WebResourceRoot resources = new StandardRoot(ctx);
            resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClassesFolder.getAbsolutePath(), "/"));
            ctx.setResources(resources);

            tomcat.start();
            tomcat.getServer().await();
        }catch (LifecycleException e) {
            e.printStackTrace(); 
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        
    }
    
}
