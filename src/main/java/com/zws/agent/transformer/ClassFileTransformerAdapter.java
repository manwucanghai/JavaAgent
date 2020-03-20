package com.zws.agent.transformer;

import com.zws.agent.context.IAgentContext;
import com.zws.agent.visitor.ClassVisitorAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author zhengws
 * @date 2020-03-14 21:14
 */
public class ClassFileTransformerAdapter implements ClassFileTransformer {

    private IAgentContext context;

    public ClassFileTransformerAdapter(IAgentContext context) {
        this.context = context;
    }

    /**
     * The implementation of this method may transform the supplied class file and return a new replacement class file.
     *
     * @param loader
     * @param className
     * @param clazz
     * @param domain
     * @param bytes
     * @return
     * @throws IllegalClassFormatException
     */
    public byte[] transform(ClassLoader loader, String className, Class<?> clazz,
                            ProtectionDomain domain, byte[] bytes) throws IllegalClassFormatException {

        if (context.isMatch(className)) {
            try {
                ClassReader classReader = new ClassReader(bytes);
                ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                ClassVisitor classVisitor = new ClassVisitorAdapter(classWriter, className);
                classReader.accept(classVisitor, 0);
                return classWriter.toByteArray();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }
}
