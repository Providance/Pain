package pain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentTypeInformation {

    @JacksonXmlProperty(localName = "SvcLvl")
    private ServiceLevel svcLvl;

    // Getters and Setters
    public ServiceLevel getSvcLvl() {
        return svcLvl;
    }

    public void setSvcLvl(ServiceLevel svcLvl) {
        this.svcLvl = svcLvl;
    }
}

