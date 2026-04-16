package pain.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import pain.*;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PainValidationSteps {

    private Document document;
    private CstmrCdtTrfInitn cstmrCdtTrfInitn;
    private PmtInf pmtInf;
    private GroupHeader grpHdr;
    private List<CdtTrfTxInf> transactions;
    private List<IbanValidationResult> ibanResults;
    private int validIbanCount;
    private int totalIbanCount;
    private Double debtrTotalAmount;
    private Double creditSumAmount;
    private String paymentExecutionDate;

    static class IbanValidationResult {
        String iban;
        String party;
        String accountType;
        boolean isValid;

        IbanValidationResult(String iban, String party, String accountType, boolean isValid) {
            this.iban = iban;
            this.party = party;
            this.accountType = accountType;
            this.isValid = isValid;
        }
    }

    @Before
    public void setUp() {
        ibanResults = new ArrayList<>();
        validIbanCount = 0;
        totalIbanCount = 0;
    }

    @Given("I have loaded the {string} file")
    public void loadPainXmlFile(String file) throws Exception {
        XmlMapper mapper = new XmlMapper();
        File xmlFile = new File("src/main/resources/" + file + ".xml");
        assertTrue(xmlFile.exists(), "file should exist");
        document = mapper.readValue(xmlFile, Document.class);
        assertNotNull(document, "Document should be loaded");
    }

    @When("I parse the document")
    public void parseDocument() {
        assertNotNull(document, "Document should be loaded");
        cstmrCdtTrfInitn = document.getCstmrCdtTrfInitn();
        assertNotNull(cstmrCdtTrfInitn, "CstmrCdtTrfInitn should not be null");

        grpHdr = cstmrCdtTrfInitn.getGrpHdr();
        pmtInf = cstmrCdtTrfInitn.getPmtInf();
        transactions = pmtInf.getCdtTrfTxInf();

        assertNotNull(grpHdr, "Group Header should not be null");
        assertNotNull(pmtInf, "Payment Info should not be null");
        assertNotNull(transactions, "Transactions should not be null");
    }

    @When("I validate all IBANs in the payment")
    public void validateAllIban() {
        ibanResults.clear();
        validIbanCount = 0;
        totalIbanCount = 0;

        if (pmtInf.getDbtrAcct().getId() != null) {
            String debtrIban = pmtInf.getDbtrAcct().getId().getIban();
            boolean isValid = Iban.isValidIban(debtrIban);
            ibanResults.add(new IbanValidationResult(
                debtrIban,
                pmtInf.getDbtr().getNm(),
                "Debtor",
                isValid
            ));
            if (isValid) validIbanCount++;
            totalIbanCount++;
        }

        for (CdtTrfTxInf txn : transactions) {
            if (txn.getCdtrAcct().getId() != null) {
                String cdtrIban = txn.getCdtrAcct().getId().getIban();
                boolean isValid = Iban.isValidIban(cdtrIban);
                ibanResults.add(new IbanValidationResult(
                    cdtrIban,
                    txn.getCdtr().getNm(),
                    "Creditor",
                    isValid
                ));
                if (isValid) validIbanCount++;
                totalIbanCount++;
            }
        }
    }

    @Then("all IBANs should be valid")
    public void verifyAllIbansAreValid() {
        assertEquals(validIbanCount, totalIbanCount, "All IBANs should be valid");
        for (IbanValidationResult result : ibanResults) {
            assertTrue(result.isValid, "IBAN " + result.iban + " should be valid");
        }
    }

    @When("I extract the debtor total amount")
    public void extractDebtrTotalAmount() {
        assertNotNull(pmtInf.getCtrlSum(), "Total amount should not be null");
        debtrTotalAmount = pmtInf.getCtrlSum();
    }

    @Then("the debtor total amount should be at least {int} digits")
    public void verifyDebtrTotalAmountDigits(int minDigits) {
        assertNotNull(debtrTotalAmount, "Debtor total amount should not be null");
        String amountString = debtrTotalAmount.toString().replace(".", "");
        int digitCount = amountString.length();
        assertTrue(digitCount >= minDigits,
            "Debtor total amount should have at least " + minDigits + " digits, but has " + digitCount);
    }

    @When("I calculate the sum of all credit amounts")
    public void calculateSumOfCredits() {
        assertFalse(transactions.isEmpty(), "Should have at least one transaction");

        creditSumAmount = 0.0;
        for (CdtTrfTxInf txn : transactions) {
            if (txn.getAmt().getInstdAmt() != null) {
                try {
                    double amount = Double.parseDouble(txn.getAmt().getInstdAmt());
                    creditSumAmount += amount;
                } catch (NumberFormatException e) {
                    fail("Invalid amount format: " + txn.getAmt().getInstdAmt());
                }
            }
        }
    }

    @Then("the debtor total amount should equal the sum of all credits")
    public void verifyDebtrEqualsSumOfCredits() {
        assertNotNull(debtrTotalAmount, "Debtor total amount should not be null");
        assertNotNull(creditSumAmount, "Credit sum amount should not be null");

        assertEquals(debtrTotalAmount, creditSumAmount, 0.01,
            "Debtor total amount (" + debtrTotalAmount + ") should equal sum of credits (" + creditSumAmount + ")");
    }

    @When("I extract the payment execution date")
    public void extractPaymentExecutionDate() {
        paymentExecutionDate = pmtInf.getReqdExctnDt();
        assertNotNull(paymentExecutionDate, "Payment execution date should not be null");
    }

    @Then("the payment execution date should not be in the future")
    public void verifyPaymentDateNotInFuture() {
        LocalDate executionDate = LocalDate.parse(paymentExecutionDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate today = LocalDate.now();

        assertFalse(executionDate.isAfter(today),
            "Payment execution date (" + paymentExecutionDate + ") should not be in the future (today is " + today + ")");
    }

    @When("I validate the document structure is complete")
    public void validateDocumentStructure() {
        assertNotNull(document, "Document should not be null");
        assertNotNull(cstmrCdtTrfInitn, "CstmrCdtTrfInitn should not be null");
        assertNotNull(grpHdr, "Group header should not be null");
        assertNotNull(pmtInf, "Payment info should not be null");
        assertNotNull(transactions, "Transactions list should not be null");
        assertFalse(transactions.isEmpty(), "Transactions list should not be empty");
    }

    @When("I validate all required fields are present")
    public void validateRequiredFields() {
        assertNotNull(grpHdr.getMsgId(), "Message ID should not be null");
        assertFalse(grpHdr.getMsgId().isEmpty(), "Message ID should not be empty");
        assertNotNull(grpHdr.getCreDtTm(), "Creation date/time should not be null");
        assertNotNull(grpHdr.getNbOfTxs(), "Number of transactions should not be null");

        assertNotNull(pmtInf.getPmtInfId(), "Payment info ID should not be null");
        assertFalse(pmtInf.getPmtInfId().isEmpty(), "Payment info ID should not be empty");
        assertNotNull(pmtInf.getPmtMtd(), "Payment method should not be null");
        assertNotNull(pmtInf.getReqdExctnDt(), "Required execution date should not be null");
        assertNotNull(pmtInf.getDbtrAcct(), "Debtor account should not be null");

        for (CdtTrfTxInf txn : transactions) {
            assertNotNull(txn.getPmtId(), "Payment ID should not be null");
            assertNotNull(txn.getAmt(), "Amount should not be null");
            assertNotNull(txn.getCdtr(), "Creditor should not be null");
            assertNotNull(txn.getCdtrAcct(), "Creditor account should not be null");
        }
    }

    @When("I validate all party names are not empty")
    public void validatePartyNames() {
        assertNotNull(pmtInf.getDbtr(), "Debtor should not be null");
        assertNotNull(pmtInf.getDbtr().getNm(), "Debtor name should not be null");
        assertFalse(pmtInf.getDbtr().getNm().isEmpty(), "Debtor name should not be empty");

        assertNotNull(grpHdr.getInitgPty(), "Initiating party should not be null");
        assertNotNull(grpHdr.getInitgPty().getNm(), "Initiating party name should not be null");
        assertFalse(grpHdr.getInitgPty().getNm().isEmpty(), "Initiating party name should not be empty");

        for (CdtTrfTxInf txn : transactions) {
            assertNotNull(txn.getCdtr().getNm(), "Creditor name should not be null");
            assertFalse(txn.getCdtr().getNm().isEmpty(), "Creditor name should not be empty");
        }
    }

    @When("I validate all amounts are positive and numeric")
    public void validateAmounts() {
        // Group header control sum
        assertNotNull(grpHdr.getCtrlSum(), "Group header control sum should not be null");
        assertTrue(grpHdr.getCtrlSum() > 0,
            "Group header control sum should be positive");

        // Payment info control sum
        assertNotNull(pmtInf.getCtrlSum(), "Payment info control sum should not be null");
        assertTrue(pmtInf.getCtrlSum() > 0,
            "Payment info control sum should be positive");

        // Transaction amounts
        for (CdtTrfTxInf txn : transactions) {
            String amountStr = txn.getAmt().getInstdAmt();
            assertNotNull(amountStr, "Amount string should not be null");

            try {
                double amount = Double.parseDouble(amountStr);
                assertTrue(amount > 0, "Transaction amount should be positive: " + amount);
            } catch (NumberFormatException e) {
                fail("Invalid amount format: " + amountStr);
            }
        }
    }

    @When("I validate all IBANs are properly formatted")
    public void validateIbanFormats() {
        String debtrIbanValue = pmtInf.getDbtrAcct().getId().getIban();
        assertNotNull(debtrIbanValue, "Debtor IBAN should not be null");
        assertFalse(debtrIbanValue.isEmpty(), "Debtor IBAN should not be empty");
        assertTrue(debtrIbanValue.matches("^[A-Z]{2}[0-9]{2}[0-9]+$"),
            "Debtor IBAN format should be valid");
        assertTrue(debtrIbanValue.length() >= 15 && debtrIbanValue.length() <= 34,
            "Debtor IBAN length should be between 15 and 34");

        for (CdtTrfTxInf txn : transactions) {
            String cdtrIbanValue = txn.getCdtrAcct().getId().getIban();
            assertNotNull(cdtrIbanValue, "Creditor IBAN should not be null");
            assertFalse(cdtrIbanValue.isEmpty(), "Creditor IBAN should not be empty");
            assertTrue(cdtrIbanValue.matches("^[A-Z]{2}[0-9]{2}[0-9]+$"),
                "Creditor IBAN format should be valid");
            assertTrue(cdtrIbanValue.length() >= 15 && cdtrIbanValue.length() <= 34,
                "Creditor IBAN length should be between 15 and 34");
        }
    }

    @When("I validate all BICs are properly formatted")
    public void validateBicFormats() {
        assertNotNull(pmtInf.getDbtrAgt(), "Debtor agent should not be null");
        String debtrBic = pmtInf.getDbtrAgt().getFinInstnId().getBic();
        assertNotNull(debtrBic, "Debtor BIC should not be null");
        assertFalse(debtrBic.isEmpty(), "Debtor BIC should not be empty");
        assertTrue(debtrBic.matches("^[A-Z]{6}[A-Z0-9]{2}([A-Z0-9]{3})?$"),
            "Debtor BIC format should be valid");

        for (CdtTrfTxInf txn : transactions) {
            assertNotNull(txn.getCdtrAgt(), "Creditor agent should not be null");
            String cdtrBic = txn.getCdtrAgt().getFinInstnId().getBic();
            assertNotNull(cdtrBic, "Creditor BIC should not be null");
            assertFalse(cdtrBic.isEmpty(), "Creditor BIC should not be empty");
            assertTrue(cdtrBic.matches("^[A-Z]{6}[A-Z0-9]{2}([A-Z0-9]{3})?$"),
                "Creditor BIC format should be valid");
        }
    }
}

