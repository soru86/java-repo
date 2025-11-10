# AHP-Based Duplicate Detection

This module demonstrates how to use the Analytic Hierarchy Process (AHP) to weight customer attributes while clustering duplicate records in a CSV dataset.

## Contents

- `sample_car_customers.csv` &mdash; synthetic dataset with 1,000 car customer rows that includes intentional duplication.
- `AhpDuplicateDetectionMain.java` &mdash; entry point that runs the duplicate detection workflow.
- Supporting classes (`AnalyticHierarchyProcess`, `CsvLoader`, `CustomerRecord`, `DuplicateDetector`, `DuplicateDetectionResult`, `DisjointSet`).
- `unique_car_customers.csv` &mdash; generated output containing the deduplicated records after a run.

## How It Works

1. **Weight derivation:** The program defines a pairwise comparison matrix for eight criteria (`Email`, `Phone`, `FirstName`, `LastName`, `PostalCode`, `City`, `CarMake`, `PurchaseDate`). The AHP implementation computes the priority weights and consistency ratio for the matrix.
2. **Similarity scoring:** Each pair of records is compared using criterion-specific similarity functions (exact and fuzzy matching for strings, phone normalization, date proximity, etc.). The final similarity score is the weighted sum of these criterion scores.
3. **Duplicate clustering:** Records whose similarity exceeds the configured threshold (default `0.78`) are grouped via union-find. The first record in each group is treated as the canonical representative.

## Running The Program

From the repository root:

```bash
cd /Users/soru86/Documents/ALL_WORK/ALL_CODE/java-repo/java_programs
javac ahp_duplicate_detection/*.java
java ahp_duplicate_detection.AhpDuplicateDetectionMain \
  ahp_duplicate_detection/sample_car_customers.csv \
  ahp_duplicate_detection/unique_car_customers.csv \
  0.78
```

### Parameters

1. **Input CSV** (optional): defaults to `ahp_duplicate_detection/sample_car_customers.csv`.
2. **Output CSV** (optional): defaults to `ahp_duplicate_detection/unique_car_customers.csv`.
3. **Threshold** (optional): similarity threshold in `(0,1)`; defaults to `0.78`.

### Sample Output

```
Loaded 1,000 records from /…/sample_car_customers.csv
Derived weights via Analytic Hierarchy Process (AHP):
  Email        : 0.3902
  Phone        : 0.2206
  ...
Consistency ratio: 0.0291 (λ_max = 8.2873)
...
Unique dataset written to: /…/unique_car_customers.csv
```

After execution the deduplicated dataset is available at the target path, and the console lists the most similar duplicate clusters for quick verification.



