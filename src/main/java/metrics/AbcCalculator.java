package metrics;

import java.util.Map;

import analyzer.ClassInfo;

public class AbcCalculator {

    public static double totalAssignments(Map<String, ClassInfo> classes) {
        int totalA = classes.values().stream().mapToInt(c -> c.assignmentCount).sum();
        int totalB = classes.values().stream().mapToInt(c -> c.branchCount).sum();
        int totalC = classes.values().stream().mapToInt(c -> c.conditionCount).sum();

        return Math.sqrt(totalA*totalA + totalB*totalB + totalC*totalC);
    }
}
