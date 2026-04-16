package pain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class DbtrAcct {

    @JacksonXmlProperty(localName = "Id")
    private IbanId id;

    public IbanId getId() {
        return id;
    }

    public void setId(IbanId id) {
        this.id = id;
    }
}
