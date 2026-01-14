package metrics;

import java.util.Map;

import analyzer.ClassInfo;

public class FieldMetricsCalculator {

    public static double averageFields(Map<String, ClassInfo> classes) {
        if (classes.isEmpty()) return 0.0;

        int sum = 0;

        for (ClassInfo cls : classes.values()) {
            sum += cls.fieldsCount;
        }

        return (double) sum / classes.size();
    }
}
