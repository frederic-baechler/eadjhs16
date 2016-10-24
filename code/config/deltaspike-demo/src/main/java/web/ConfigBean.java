package web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.deltaspike.core.api.config.ConfigProperty;

@Named
@RequestScoped
public class ConfigBean {

    @Inject
    @ConfigProperty(name = "greeting")
    private String greeting;

    @Inject
    @ConfigProperty(name = "value.from.apache-deltaspike.properties")
    private String deltaSpikeValue;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getDeltaSpikeValue() {
        return deltaSpikeValue;
    }

    public void setDeltaSpikeValue(String deltaSpikeValue) {
        this.deltaSpikeValue = deltaSpikeValue;
    }

}
