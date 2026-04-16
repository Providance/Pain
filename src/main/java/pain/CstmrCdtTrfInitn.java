package pain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CstmrCdtTrfInitn {

    @JacksonXmlProperty(
            localName = "GrpHdr",
            namespace = "urn:iso:std:iso:20022:tech:xsd:pain.001.001.11"
    )
    private GroupHeader grpHdr;

    @JacksonXmlProperty(
            localName = "PmtInf",
            namespace = "urn:iso:std:iso:20022:tech:xsd:pain.001.001.11"
    )
    private PmtInf pmtInf;

    public GroupHeader getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GroupHeader grpHdr) {
        this.grpHdr = grpHdr;
    }

    public PmtInf getPmtInf() {
        return pmtInf;
    }

    public void setPmtInf(PmtInf pmtInf) {
        this.pmtInf = pmtInf;
    }
}
