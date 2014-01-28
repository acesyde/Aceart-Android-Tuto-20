package fr.aceart.weatherspice.REST.Model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Observation {

    private Current_observation current_observation;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Current_observation getCurrent_observation() {
        return current_observation;
    }

    public void setCurrent_observation(Current_observation current_observation) {
        this.current_observation = current_observation;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}