package com.janwarlen.learn.grammar.finaldemo;

import com.janwarlen.learn.grammar.finaldemo.FinalDemo;
import org.junit.Test;

public class JUnitFinalTest {

    @Test
    public void testFinalDemo() {
        FinalDemo finalDemo = new FinalDemo();
        finalDemo.setPerson("1");
        finalDemo.setPerson("2");

        FinalDemo.add(1,2);
    }

}
