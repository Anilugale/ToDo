package com.marocks.todo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        for(int i=0;i<100;i++){
            int data =(int) (Math.abs(Math.random()*100)%10);
            //
            System.out.println(data);
        }
    }
}