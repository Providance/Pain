package pain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupHeader {

    @JacksonXmlProperty(localName = "MsgId")
    private String msgId;

    @JacksonXmlProperty(localName = "CreDtTm")
    private String creDtTm;

    @JacksonXmlProperty(localName = "NbOfTxs")
    private Integer nbOfTxs;

    @JacksonXmlProperty(localName = "CtrlSum")
    private Double ctrlSum;

    @JacksonXmlProperty(localName = "InitgPty")
    private Party initgPty;

    // Getters and Setters
    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getCreDtTm() {
        return creDtTm;
    }

    public void setCreDtTm(String creDtTm) {
        this.creDtTm = creDtTm;
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

    public Party getInitgPty() {
        return initgPty;
    }

    public void setInitgPty(Party initgPty) {
        this.initgPty = initgPty;
    }
}

