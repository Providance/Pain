package pain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CdtTrfTxInf {

    @JacksonXmlProperty(localName = "PmtId")
    private PaymentIdentification pmtId;

    @JacksonXmlProperty(localName = "Amt")
    private Amount amt;

    @JacksonXmlProperty(localName = "CdtrAgt")
    private FinancialInstitution cdtrAgt;

    @JacksonXmlProperty(localName = "Cdtr")
    private Party cdtr;

    @JacksonXmlProperty(localName = "CdtrAcct")
    private CdtrAcct cdtrAcct;

    @JacksonXmlProperty(localName = "RmtInf")
    private RemittanceInformation rmtInf;

    // Getters and Setters
    public PaymentIdentification getPmtId() {
        return pmtId;
    }

    public void setPmtId(PaymentIdentification pmtId) {
        this.pmtId = pmtId;
    }

    public Amount getAmt() {
        return amt;
    }

    public void setAmt(Amount amt) {
        this.amt = amt;
    }

    public FinancialInstitution getCdtrAgt() {
        return cdtrAgt;
    }

    public void setCdtrAgt(FinancialInstitution cdtrAgt) {
        this.cdtrAgt = cdtrAgt;
    }

    public Party getCdtr() {
        return cdtr;
    }

    public void setCdtr(Party cdtr) {
        this.cdtr = cdtr;
    }

    public CdtrAcct getCdtrAcct() {
        return cdtrAcct;
    }

    public void setCdtrAcct(CdtrAcct cdtrAcct) {
        this.cdtrAcct = cdtrAcct;
    }

    public RemittanceInformation getRmtInf() {
        return rmtInf;
    }

    public void setRmtInf(RemittanceInformation rmtInf) {
        this.rmtInf = rmtInf;
    }
}
