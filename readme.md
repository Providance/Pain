# Tranzactie - PAIN.001 Payment Validation Framework

A comprehensive Java-based payment validation framework for processing and validating PAIN.001.001.11 (ISO 20022) XML payment files using Cucumber BDD testing.

## Overview

This project provides robust validation of PAIN.001 (Customer Credit Transfer Initiation) XML documents with:
- **7 comprehensive test scenarios** covering IBAN validation, amount reconciliation, date validation, and ISO 20022 compliance
- **Complete POJO mapping** for XML parsing using Jackson
- **Cucumber BDD framework** for business-readable payment validation tests
- **ISO 20022 compliance checks** for payment processing

## Key Features

✅ **IBAN Validation** - ISO 13616 checksum validation for debtor and creditor accounts
✅ **Payment Amount Validation** - Reconciliation between debtor and credit totals
✅ **Date Validation** - Ensures payment dates are not in the future
✅ **ISO 20022 Compliance** - Comprehensive validation of PAIN.001 structure and data
✅ **BDD Testing** - Human-readable Gherkin scenarios for payment validation
✅ **Automatic XML Parsing** - Complete POJO structure for pain.001.001.11 format

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
