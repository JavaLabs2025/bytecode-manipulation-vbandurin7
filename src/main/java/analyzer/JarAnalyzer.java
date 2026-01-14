package analyzer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.objectweb.asm.ClassReader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import metrics.AbcCalculator;
import metrics.FieldMetricsCalculator;
import metrics.InheritanceDepthCalculator;
import metrics.InheritanceStats;
import metrics.OverrideMethodsCalculator;
import output.MetricsResult;
import visitor.MetricsClassVisitor;

public class JarAnalyzer {

    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.err.println("Expected 2 arguments: <input.jar> <output.json>");
            System.exit(1);
        }

        String jarPath = args[0];
        String jsonOutputPath = args[1];

        Map<String, ClassInfo> classes = new HashMap<>();

        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();

                if (!entry.getName().endsWith(".class")) {
                    continue;
                }

                try (InputStream is = jarFile.getInputStream(entry)) {
                    ClassReader reader = new ClassReader(is);
                    MetricsClassVisitor visitor = new MetricsClassVisitor();
                    reader.accept(visitor, ClassReader.SKIP_DEBUG);

                    ClassInfo info = visitor.getClassInfo();
                    classes.put(info.name, info);
                }
            }
        }


        InheritanceStats depthStats =
                InheritanceDepthCalculator.calculate(classes);

        int totalAssignments =
                AbcCalculator.totalAssignments(classes);

        double avgOverriddenMethods =
                OverrideMethodsCalculator.averageOverriddenMethods(classes);

        double avgFields =
                FieldMetricsCalculator.averageFields(classes);

        MetricsResult result = new MetricsResult(
                classes.size(),
                totalAssignments,
                depthStats,
                avgOverriddenMethods,
                avgFields
        );

        printToConsole(result);

        writeJson(result, jsonOutputPath);
    }

    private static void printToConsole(MetricsResult r) {
        System.out.println("====== Metrics ======");
        System.out.println("Classes analyzed: " + r.classesAnalyzed);
        System.out.println("ABC (assignments): " + r.abcAssignments);
        System.out.println("Max inheritance depth: " + r.maxInheritanceDepth);
        System.out.println("Avg inheritance depth: " + r.avgInheritanceDepth);
        System.out.println("Avg overridden methods: " + r.avgOverriddenMethods);
        System.out.println("Avg fields per class: " + r.avgFieldsPerClass);
    }

    private static void writeJson(
            MetricsResult result,
            String outputPath
    ) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        mapper.writeValue(new File(outputPath), result);
    }
}
