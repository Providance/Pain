package pain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FinancialInstitution {

    @JacksonXmlProperty(localName = "FinInstnId")
    private FinancialInstitutionId finInstnId;

    // Getters and Setters
    public FinancialInstitutionId getFinInstnId() {
        return finInstnId;
    }

    public void setFinInstnId(FinancialInstitutionId finInstnId) {
        this.finInstnId = finInstnId;
    }
}

