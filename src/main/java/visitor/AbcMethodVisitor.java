package visitor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import analyzer.ClassInfo;

public class AbcMethodVisitor extends MethodVisitor {

    private final ClassInfo classInfo;

    public AbcMethodVisitor(MethodVisitor mv, ClassInfo classInfo) {
        super(Opcodes.ASM9, mv);
        this.classInfo = classInfo;
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        if (opcode >= Opcodes.ISTORE && opcode <= Opcodes.ASTORE) {
            classInfo.assignmentCount++;
        }
        super.visitVarInsn(opcode, var);
    }
}
