package com.zws.agent.visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhengws
 * @date 2020-03-14 22:24
 */
public class ClassVisitorAdapter extends ClassVisitor {

    private String className;

    private final List<String> filterMethods = Arrays.asList("<clinit>", "<init>");

    public ClassVisitorAdapter(ClassVisitor classVisitor, String className) {
        super(Opcodes.ASM7, classVisitor);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, String methodName, String descriptor,
                                     String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, methodName, descriptor, signature, exceptions);
        if (!filterMethods.contains(methodName)) {
            return new MethodVisitorAdapter(methodVisitor, className, methodName);
        }
        return methodVisitor;
    }
}
