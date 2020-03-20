package com.zws.agent.visitor;

import com.zws.agent.inject.MethodInject;
import com.zws.agent.inject.method.IMethodMapper;
import com.zws.agent.inject.method.MethodInjectMapper;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.INVOKESTATIC;

/**
 * @author zhengws
 * @date 2020-03-14 22:27
 */
public class MethodVisitorAdapter extends MethodVisitor {

    private final String injectClass = MethodInject.class.getName().replace(".", "/");

    private IMethodMapper methodMapper = new MethodInjectMapper();

    private long methodId;

    public MethodVisitorAdapter(MethodVisitor methodVisitor, String className, String methodName) {
        super(Opcodes.ASM7, methodVisitor);
        this.methodMapper.setMethodInfo(methodName, className);
        this.methodId = this.methodMapper.getMethodId();
    }

    @Override
    public void visitCode() {
//        System.out.println("###########visitCode#############");
        this.methodMapper.submitMethod();
        this.visitLdcInsn(this.methodId);
        this.visitMethodInsn(INVOKESTATIC, injectClass, "start", "(J)V", false);
        super.visitCode();
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        this.methodMapper.updateLineNumber(line);
        super.visitLineNumber(line, start);
    }

    @Override
    public void visitInsn(int opcode) {
//        System.out.println("###########visitInsn#############");
        switch (opcode) {
            case Opcodes.ARETURN:
            case Opcodes.DRETURN:
            case Opcodes.FRETURN:
            case Opcodes.IRETURN:
            case Opcodes.LRETURN:
            case Opcodes.RETURN:
            case Opcodes.ATHROW:
                this.visitLdcInsn(this.methodId);
                this.visitMethodInsn(INVOKESTATIC, injectClass, "end", "(J)V", false);
                break;
            default:
                break;
        }
        super.visitInsn(opcode);

    }
}
