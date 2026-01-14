package metrics;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import analyzer.ClassInfo;
import analyzer.MethodSignature;

public class OverrideMethodsCalculator {

    public static double averageOverriddenMethods(Map<String, ClassInfo> classes) {
        int totalOverrides = 0;
        int classCount = 0;

        for (ClassInfo cls : classes.values()) {
            if (cls.superName == null) continue;

            ClassInfo superCls = classes.get(cls.superName);
            if (superCls == null) continue;

            int overridden = countOverrides(cls, classes);
            totalOverrides += overridden;
            classCount = overridden > 0 ? classCount + 1 : classCount;
        }

        if (classCount == 0) return 0.0;

        return (double) totalOverrides / classCount;
    }

    public static int countOverrides(ClassInfo cls, Map<String, ClassInfo> classes) {
        int count = 0;
        Set<MethodSignature> seen = new HashSet<>();

        String superName = cls.superName;

        while (superName != null && classes.containsKey(superName)) {
            ClassInfo superCls = classes.get(superName);

            for (MethodSignature m : cls.methods) {
                if (!seen.contains(m) && (isObjectMethod(m) || superCls.methods.contains(m))) {
                    count++;
                    seen.add(m);
                }
            }

            superName = superCls.superName;
        }

        return count;
    }

    private static boolean isObjectMethod(MethodSignature method) {
        String methodSignature = method.name() + method.descriptor();
        return methodSignature.equals("toString()Ljava/lang/String;") ||
                methodSignature.equals("equals(Ljava/lang/Object;)Z") ||
                methodSignature.equals("hashCode()I") ||
                methodSignature.equals("clone()Ljava/lang/Object;") ||
                methodSignature.equals("finalize()V");
    }
}
