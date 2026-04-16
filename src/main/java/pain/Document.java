package pain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(
        localName = "Document",
        namespace = "urn:iso:std:iso:20022:tech:xsd:pain.001.001.11"
)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Document {

    @JacksonXmlProperty(
            localName = "CstmrCdtTrfInitn",
            namespace = "urn:iso:std:iso:20022:tech:xsd:pain.001.001.11"
    )
    private CstmrCdtTrfInitn cstmrCdtTrfInitn;

    public CstmrCdtTrfInitn getCstmrCdtTrfInitn() {
        return cstmrCdtTrfInitn;
    }

    public void setCstmrCdtTrfInitn(CstmrCdtTrfInitn cstmrCdtTrfInitn) {
        this.cstmrCdtTrfInitn = cstmrCdtTrfInitn;
    }
}
