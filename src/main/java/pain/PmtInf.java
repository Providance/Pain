package pain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PmtInf {

    @JacksonXmlProperty(localName = "PmtInfId")
    private String pmtInfId;

    @JacksonXmlProperty(localName = "PmtMtd")
    private String pmtMtd;

    @JacksonXmlProperty(localName = "BtchBookg")
    private Boolean batchBooking;

    @JacksonXmlProperty(localName = "NbOfTxs")
    private Integer nbOfTxs;

    @JacksonXmlProperty(localName = "CtrlSum")
    private Double ctrlSum;

    @JacksonXmlProperty(localName = "PmtTpInf")
    private PaymentTypeInformation pmtTpInf;

    @JacksonXmlProperty(localName = "ReqdExctnDt")
    private String reqdExctnDt;

    @JacksonXmlProperty(localName = "Dbtr")
    private Party dbtr;

    @JacksonXmlProperty(localName = "DbtrAcct")
    private DbtrAcct dbtrAcct;

    @JacksonXmlProperty(localName = "DbtrAgt")
    private FinancialInstitution dbtrAgt;

    @JacksonXmlProperty(localName = "ChrgBr")
    private String chrgBr;

    @JacksonXmlProperty(localName = "CdtTrfTxInf")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<CdtTrfTxInf> cdtTrfTxInf;

    // Getters and Setters
    public String getPmtInfId() {
        return pmtInfId;
    }

    public void setPmtInfId(String pmtInfId) {
        this.pmtInfId = pmtInfId;
    }

    public String getPmtMtd() {
        return pmtMtd;
    }

    public void setPmtMtd(String pmtMtd) {
        this.pmtMtd = pmtMtd;
    }

    public Boolean getBatchBooking() {
        return batchBooking;
    }

    public void setBatchBooking(Boolean batchBooking) {
        this.batchBooking = batchBooking;
    }

    public Integer getNbOfTxs() {
        return nbOfTxs;
    }

    public void setNbOfTxs(Integer nbOfTxs) {
        this.nbOfTxs = nbOfTxs;
    }

    public Double getCtrlSum() {
        return ctrlSum;
    }

    public void setCtrlSum(Double ctrlSum) {
        this.ctrlSum = ctrlSum;
    }

    public PaymentTypeInformation getPmtTpInf() {
        return pmtTpInf;
    }

    public void setPmtTpInf(PaymentTypeInformation pmtTpInf) {
        this.pmtTpInf = pmtTpInf;
    }

    public String getReqdExctnDt() {
        return reqdExctnDt;
    }

    public void setReqdExctnDt(String reqdExctnDt) {
        this.reqdExctnDt = reqdExctnDt;
    }

    public Party getDbtr() {
        return dbtr;
    }

    public void setDbtr(Party dbtr) {
        this.dbtr = dbtr;
    }

    public DbtrAcct getDbtrAcct() {
        return dbtrAcct;
    }

    public void setDbtrAcct(DbtrAcct dbtrAcct) {
        this.dbtrAcct = dbtrAcct;
    }

    public FinancialInstitution getDbtrAgt() {
        return dbtrAgt;
    }

    public void setDbtrAgt(FinancialInstitution dbtrAgt) {
        this.dbtrAgt = dbtrAgt;
    }

    public String getChrgBr() {
        return chrgBr;
    }

    public void setChrgBr(String chrgBr) {
        this.chrgBr = chrgBr;
    }

    public List<CdtTrfTxInf> getCdtTrfTxInf() {
        return cdtTrfTxInf;
    }

    public void setCdtTrfTxInf(List<CdtTrfTxInf> cdtTrfTxInf) {
        this.cdtTrfTxInf = cdtTrfTxInf;
    }
}
