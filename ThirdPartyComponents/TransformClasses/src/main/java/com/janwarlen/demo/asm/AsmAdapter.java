package com.janwarlen.demo.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AsmAdapter extends ClassVisitor implements Opcodes {

    public AsmAdapter(ClassVisitor cv) {
        super(ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals("testRedisLock")) {
            methodVisitor = new MethodAdapterVisitor(api, methodVisitor, access, name, desc);
        }
        return methodVisitor;
    }
}
