package metrics;

import java.util.Map;

import analyzer.ClassInfo;

public class InheritanceDepthCalculator {

    public static InheritanceStats calculate(Map<String, ClassInfo> classes) {
        if (classes.isEmpty()) {
            return new InheritanceStats(0, 0.0);
        }

        int max = 0;
        int sum = 0;

        for (ClassInfo cls : classes.values()) {
            int depth = calculateDepth(cls, classes);
            sum += depth;
            max = Math.max(max, depth);
        }

        double avg = (double) sum / classes.size();

        return new InheritanceStats(max, avg);
    }

    private static int calculateDepth(
            ClassInfo cls,
            Map<String, ClassInfo> classes
    ) {
        int depth = 1;
        String current = cls.superName;

        while (current != null && classes.containsKey(current)) {
            depth++;
            current = classes.get(current).superName;
        }

        return depth;
    }
}
