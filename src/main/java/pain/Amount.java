package pain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Amount {

    @JacksonXmlProperty(localName = "InstdAmt")
    private String instdAmt;

    // Getters and Setters
    public String getInstdAmt() {
        return instdAmt;
    }

    public void setInstdAmt(String instdAmt) {
        this.instdAmt = instdAmt;
    }
}

