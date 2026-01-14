package visitor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import analyzer.ClassInfo;

public class AbcMethodVisitor extends MethodVisitor {

    private final ClassInfo classInfo;

    private static final Set<Integer> CONDITIONAL_JUMPS = new HashSet<>(Arrays.asList(
            Opcodes.IFEQ, Opcodes.IFNE, Opcodes.IFLT, Opcodes.IFGE, Opcodes.IFGT, Opcodes.IFLE,
            Opcodes.IF_ICMPEQ, Opcodes.IF_ICMPNE, Opcodes.IF_ICMPLT, Opcodes.IF_ICMPGE,
            Opcodes.IF_ICMPGT, Opcodes.IF_ICMPLE, Opcodes.IF_ACMPEQ, Opcodes.IF_ACMPNE,
            Opcodes.IFNULL, Opcodes.IFNONNULL
    ));


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

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        classInfo.branchCount++;

        if (CONDITIONAL_JUMPS.contains(opcode)) {
            classInfo.conditionCount++;
        }

        super.visitJumpInsn(opcode, label);
    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        classInfo.branchCount++;
        classInfo.conditionCount += labels.length + 1;
        super.visitLookupSwitchInsn(dflt, keys, labels);
    }

    @Override
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        classInfo.branchCount++;
        classInfo.conditionCount += labels.length + 1;
        super.visitTableSwitchInsn(min, max, dflt, labels);
    }
}
