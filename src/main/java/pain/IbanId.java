package pain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class IbanId {

    @JacksonXmlProperty(localName = "IBAN")
    private String iban;

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
