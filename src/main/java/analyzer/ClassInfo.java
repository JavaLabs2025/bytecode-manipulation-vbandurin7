package analyzer;

import java.util.HashSet;
import java.util.Set;

public class ClassInfo {
    public final String name;
    public final String superName;
    public final Set<MethodSignature> methods = new HashSet<>();

    public int fieldsCount = 0;
    public int assignmentCount = 0;

    public ClassInfo(String name, String superName) {
        this.name = name;
        this.superName = superName;
    }
}
