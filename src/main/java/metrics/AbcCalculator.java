package metrics;

import java.util.Map;

import analyzer.ClassInfo;

public class AbcCalculator {

    public static int totalAssignments(Map<String, ClassInfo> classes) {
        int sum = 0;

        for (ClassInfo cls : classes.values()) {
            sum += cls.assignmentCount;
        }

        return sum;
    }
}
