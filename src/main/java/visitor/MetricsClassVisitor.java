package visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import analyzer.ClassInfo;
import analyzer.MethodSignature;

public class MetricsClassVisitor extends ClassVisitor {

    private ClassInfo classInfo;

    public MetricsClassVisitor() {
        super(Opcodes.ASM9);
    }

    @Override
    public void visit(
            int version,
            int access,
            String name,
            String signature,
            String superName,
            String[] interfaces
    ) {
        this.classInfo = new ClassInfo(name, superName);
    }

    @Override
    public FieldVisitor visitField(
            int access,
            String name,
            String descriptor,
            String signature,
            Object value
    ) {
        classInfo.fieldsCount++;
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(
            int access,
            String name,
            String descriptor,
            String signature,
            String[] exceptions
    ) {
        if (isEligibleForOverride(access, name)) {
            classInfo.methods.add(new MethodSignature(name, descriptor));
        }

        return new AbcMethodVisitor(
                super.visitMethod(access, name, descriptor, signature, exceptions),
                classInfo
        );
    }

    private boolean isEligibleForOverride(int access, String name) {
        if (name.equals("<init>") || name.equals("<clinit>")) {
            return false;
        }
        if ((access & Opcodes.ACC_PRIVATE) != 0) {
            return false;
        }
        if ((access & Opcodes.ACC_STATIC) != 0) {
            return false;
        }
        return true;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }
}
