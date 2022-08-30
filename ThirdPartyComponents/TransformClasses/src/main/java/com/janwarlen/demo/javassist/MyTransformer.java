package com.janwarlen.demo.javassist;

import javassist.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class MyTransformer implements ClassFileTransformer {

    public MyTransformer() {
        System.out.println("new MyTransformer");
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!className.equals(javaNameToJvmName("com.janwarlen.schedule.LockSchedule"))) {
            return classfileBuffer;
        }
        System.out.println(className);
        {
            ClassPool classPool = ClassPool.getDefault();
            try {
                CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
                CtMethod testRedisLock = ctClass.getDeclaredMethod("testRedisLock");
                testRedisLock.insertBefore("{ System.out.println(\"agent before\"); }");
                testRedisLock.insertAfter("{ System.out.println(\"agent after\"); }");
                return ctClass.toBytecode();
            } catch (NotFoundException | CannotCompileException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String javaNameToJvmName(String javaName) {
        if (javaName == null) {
            throw new NullPointerException("javaName must not be null");
        }
        return javaName.replace('.', '/');
    }
}
