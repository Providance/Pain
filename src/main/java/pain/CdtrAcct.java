package pain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CdtrAcct {

    @JacksonXmlProperty(localName = "Id")
    private IbanId id;

    public IbanId getId() {
        return id;
    }

    public void setId(IbanId id) {
        this.id = id;
    }
}
