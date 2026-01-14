package output;

import metrics.InheritanceStats;

public class MetricsResult {

    public int classesAnalyzed;
    public int abcAssignments;

    public int maxInheritanceDepth;
    public double avgInheritanceDepth;

    public double avgOverriddenMethods;
    public double avgFieldsPerClass;

    public MetricsResult(
            int classesAnalyzed,
            int abcAssignments,
            InheritanceStats inheritanceStats,
            double avgOverriddenMethods,
            double avgFieldsPerClass
    ) {
        this.classesAnalyzed = classesAnalyzed;
        this.abcAssignments = abcAssignments;
        this.maxInheritanceDepth = inheritanceStats.maxDepth();
        this.avgInheritanceDepth = inheritanceStats.avgDepth();
        this.avgOverriddenMethods = avgOverriddenMethods;
        this.avgFieldsPerClass = avgFieldsPerClass;
    }
}
