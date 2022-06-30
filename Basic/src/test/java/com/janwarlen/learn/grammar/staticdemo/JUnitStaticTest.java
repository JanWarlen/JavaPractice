package com.janwarlen.learn.grammar.staticdemo;

import com.janwarlen.learn.grammar.staticdemo.StaticMemberDemo;
import org.junit.Test;

public class JUnitStaticTest {

    @Test
    public void testStatic() {
        StaticMemberDemo test = new StaticMemberDemo();
        test.staticImport();
        StaticMemberDemo.staticImportFunc();
    }



}
