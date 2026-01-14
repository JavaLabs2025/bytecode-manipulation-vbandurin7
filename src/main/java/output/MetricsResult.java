package output;

import metrics.InheritanceStats;

public class MetricsResult {

    public int classesAnalyzed;
    public double abc;

    public int maxInheritanceDepth;
    public double avgInheritanceDepth;

    public double avgOverriddenMethods;
    public double avgFieldsPerClass;

    public MetricsResult(
            int classesAnalyzed,
            double abc,
            InheritanceStats inheritanceStats,
            double avgOverriddenMethods,
            double avgFieldsPerClass
    ) {
        this.classesAnalyzed = classesAnalyzed;
        this.abc = abc;
        this.maxInheritanceDepth = inheritanceStats.maxDepth();
        this.avgInheritanceDepth = inheritanceStats.avgDepth();
        this.avgOverriddenMethods = avgOverriddenMethods;
        this.avgFieldsPerClass = avgFieldsPerClass;
    }
}
