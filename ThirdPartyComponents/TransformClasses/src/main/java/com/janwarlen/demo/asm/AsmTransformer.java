package com.janwarlen.demo.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class AsmTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!className.equals(javaNameToJvmName("com.janwarlen.schedule.LockSchedule"))) {
            return classfileBuffer;
        }
        System.out.println("AsmTransformer: " + className);
        {
            ClassReader classReader = new ClassReader(classfileBuffer);
            ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
            classReader.accept(new AsmAdapter(classWriter), ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
            return classWriter.toByteArray();
        }
    }

    public static String javaNameToJvmName(String javaName) {
        if (javaName == null) {
            throw new NullPointerException("javaName must not be null");
        }
        return javaName.replace('.', '/');
    }
}
