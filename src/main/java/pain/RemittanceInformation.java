package pain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RemittanceInformation {

    @JacksonXmlProperty(localName = "Ustrd")
    private String ustrd;

    // Getters and Setters
    public String getUstrd() {
        return ustrd;
    }

    public void setUstrd(String ustrd) {
        this.ustrd = ustrd;
    }
}

