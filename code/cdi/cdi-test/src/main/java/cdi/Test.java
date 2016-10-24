package cdi;

import javax.enterprise.event.Observes;
import org.jboss.weld.environment.se.events.ContainerInitialized;

public class Test {

    public void printHello(@Observes ContainerInitialized event) {
        System.out.println("Hello World");
    }
}
