# PAIN.001 Payment Validation Framework

## Quick Start

### Run All Tests
```bash
mvn test
```

### Run Direct Test (Console Output)
```bash
mvn exec:java -Dexec.mainClass="pain.MainTest"
```

## Test Scenarios

1. **Extract and validate debtor IBAN** - Validates debtor account IBAN format and checksum
2. **Extract and validate creditor IBANs** - Validates all creditor account IBANs
3. **Validate all IBANs in payment** - Comprehensive IBAN validation with success rate
4. **Validate debtor amount has at least 2 digits** - Ensures amount values are meaningful
5. **Validate debtor amount equals sum of credits** - Payment reconciliation check
6. **Validate transaction date is not in the future** - Date validation
7. **Validate XML according to ISO 20022** - Complete compliance check

## Project Structure

```
src/
├── main/java/pain/
│   ├── MainTest.java                    - Direct test runner
│   ├── Iban.java                        - IBAN validation utility
│   ├── Document.java                    - Root XML element
│   ├── [16 more POJO classes]           - XML mapping structure
│   └── ...
├── test/
│   ├── java/pain/
│   │   ├── steps/PainValidationSteps.java    - 45+ step definitions
│   │   └── test/PainValidationCucumberTest.java - Cucumber runner
│   └── resources/pain_validation.feature     - 7 test scenarios
└── resources/pain.xml                   - Sample payment data

pom.xml                                  - Maven configuration
```

## Technologies

- **Java 11** - Core language
- **Cucumber 7.14.0** - BDD framework
- **JUnit 5** - Testing framework
- **Jackson 2.17.0** - XML parsing
- **Maven** - Build management

## Test Data

The project includes a sample `pain.xml` with:
- **Debtor**: Joe Doe (DE89370400440532013000)
- **3 Creditors**: Peter Parker, Carl White, Frank Black
- **Total Amount**: 8000.2 EUR (2000.2 + 1000.0 + 5000.0)
- **Payment Date**: 2024-02-20 (SEPA credit transfer)

## IBAN & BIC Validation

```
Format Validation:
  IBAN: ^[A-Z]{2}[0-9]{2}[A-Z0-9]+$ (15-34 characters)
  BIC:  ^[A-Z]{6}[A-Z0-9]{2}([A-Z0-9]{3})?$ (8 or 11 characters)

Checksum Validation:
  ISO 13616 mod-97 algorithm for IBAN verification
```

## Build & Test

```bash
# Compile
mvn clean compile

# Run all tests
mvn test

# Run with verbose output
mvn test -X

# View HTML report (if generated)
open target/cucumber-reports.html
```

## References

- [ISO 20022 PAIN.001](https://www.iso20022.org/) - Payment standard
- [ISO 13616](https://en.wikipedia.org/wiki/International_Bank_Account_Number) - IBAN standard
- [ISO 9362](https://en.wikipedia.org/wiki/Bank_identifier_code) - BIC standard
- [SEPA Scheme](https://www.europeanpaymentscouncil.eu/) - Single Euro Payments Area

## What type of operation is described according to the attached message.

The sample `pain.xml` file in this project describes a **SEPA Credit Transfer (SCT)** operation:

### Key Indicators

- **Payment Method**: `<PmtMtd>TRF</PmtMtd>` (Credit Transfer)
- **Service Level**: `<SvcLvl><Cd>SEPA</Cd></SvcLvl>` (SEPA scheme)
- **Document Type**: `<CstmrCdtTrfInitn>` (Customer Credit Transfer Initiation)

### Payment Details

```
Debtor (Payer):
  Name: Joe Doe
  IBAN: DE89370400440532013000
  Bank: DEUTDEBBXXX (Deutsche Bank)

Creditors (Payees):
  1. Peter Parker (DE) - 2000.2 EUR
  2. Carl White (DK) - 1000.0 EUR
  3. Frank Black (CZ) - 5000.0 EUR

Total Amount: 8000.2 EUR
Payment Date: 2024-02-20
Scheme: SEPA (Single Euro Payments Area)
Charge Bearer: SLEV (Shared)
Batch Processing: Enabled
```

## Describe the operation in more details.

### Document Structure Overview

This PAIN.001.001.11 message represents a **Customer Credit Transfer Initiation** - a standardized format for initiating cross-border SEPA payments.

### Message Header (GrpHdr - Group Header)

**Details:**
- **Message ID**: DEUTDEBBXXX0020240220042409
  - Bank code: DEUTDEBBXXX (Deutsche Bank)
  - Unique identifier for this payment file
- **Creation Date/Time**: 2024-02-20T04:24:09.000Z (ISO 8601 format)
- **Number of Transactions**: 3 (total credit transfers in this message)
- **Control Sum**: 8000.2 EUR (total amount of all payments)
- **Initiating Party**: Joe Doe (the debtor/payer initiating the transfers)

### Payment Information (PmtInf - Payment Information Block)

**Payment Details:**

| Field | Value | Meaning |
|-------|-------|---------|
| **Payment Info ID** | PMT-ID0-20240220042409 | Unique ID for this payment batch |
| **Payment Method** | TRF | Credit Transfer |
| **Batch Booking** | true | All transactions processed together |
| **Number of Transactions** | 3 | Three separate credit transfers |
| **Control Sum** | 8000.2 EUR | Total amount across all transactions |
| **Service Level** | SEPA | Single Euro Payments Area scheme |
| **Execution Date** | 2024-02-20 | When payments should be executed |
| **Charge Bearer** | SLEV | Shared (debtor pays bank charges) |

**Debtor Information:**
- **Name**: Joe Doe
- **IBAN**: DE89370400440532013000 (German account)
- **Bank**: DEUTDEBBXXX (Deutsche Bank Frankfurt)

### Credit Transfer Transactions

#### Transaction 1: Peter Parker (Germany)
- **Amount**: 2000.2 EUR
- **Creditor IBAN**: DE05500105173195282731
- **Creditor Bank**: VBRSDE33347 (Volksbank Rhein-Sieg)
- **Reference**: "Salary PP 2018-07"

#### Transaction 2: Carl White (Denmark)
- **Amount**: 1000.0 EUR
- **Creditor IBAN**: DK5250511963137134
- **Creditor Bank**: UINVDEFFXXX (UniCredit Frankfurt)
- **Reference**: "Salary CW 2015-12"

#### Transaction 3: Frank Black (Czech Republic)
- **Amount**: 5000.0 EUR
- **Creditor IBAN**: CZ7627005991764514418145
- **Creditor Bank**: SWBSDESSXXX (Südwestbank Stuttgart)
- **Reference**: "Salary FB 2017-05"

### Payment Flow & Processing

```
Step 1: Initiation
  └─ Joe Doe creates payment file on 2024-02-20

Step 2: Validation
  └─ Total amount: 2000.2 + 1000.0 + 5000.0 = 8000.2 EUR ✓
  └─ All IBANs valid and checksummed

Step 3: Submission to Bank
  └─ Deutsche Bank (DEUTDEBBXXX) receives the file

Step 4: Processing
  └─ Bank debits Joe Doe's account: 8000.2 EUR
  └─ Bank processes 3 separate SEPA transactions

Step 5: Settlement
  └─ Peter Parker's bank: receives 2000.2 EUR
  └─ Carl White's bank: receives 1000.0 EUR
  └─ Frank Black's bank: receives 5000.0 EUR

Step 6: Completion
  └─ Expected execution date: 2024-02-20 (or next business day)
```

### IBAN Validation

All IBANs use the ISO 13616 standard with mod-97 checksum:

| Party | IBAN | Country | Length | Valid |
|-------|------|---------|--------|-------|
| Joe Doe (Debtor) | DE89370400440532013000 | Germany | 22 | ✅ |
| Peter Parker | DE05500105173195282731 | Germany | 22 | ✅ |
| Carl White | DK5250511963137134 | Denmark | 18 | ✅ |
| Frank Black | CZ7627005991764514418145 | Czech Republic | 24 | ✅ |
