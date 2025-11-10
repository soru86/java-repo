package ahp_duplicate_detection;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class CustomerRecord {

    private final Map<String, String> fields;

    public CustomerRecord(Map<String, String> fields) {
        Objects.requireNonNull(fields, "fields");
        this.fields = new LinkedHashMap<>(fields);
    }

    public String get(String fieldName) {
        return fields.getOrDefault(fieldName, "");
    }

    public Map<String, String> getFields() {
        return Map.copyOf(fields);
    }

    public String getCustomerId() {
        return get("CustomerID");
    }

    @Override
    public String toString() {
        return "CustomerRecord{" + getCustomerId() + "}";
    }
}

