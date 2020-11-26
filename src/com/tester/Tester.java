package com.tester;

import com.controller.Cases;

public class Tester extends Thread {
    private final String name;
    private final int index;

    public Tester(String name, int index) {
        this.name = name;
        this.index = index;
    }


    public static void main(String[] args) {
        int threadCount = 1;
//        if(args[0] != null ) {
//            try {
//                threadCount = Integer.parseInt(args[0]);
//            }catch(Exception e) {
//                System.out.println(e.toString());
//            }


//        }
        for (int i = 1; i <= threadCount; i++) {
            Thread t = new Tester("Thread " + i, i);
            t.start();
        }

    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            test();
        } catch (InterruptedException e) {
            System.out.println("sleep interrupted");
        }
    }

    private void test() throws InterruptedException {
        Cases c = new Cases("firefox", true);
        try {
            c.SetWindowSize(1920, 1080);
            c.GoToUrl("https://www.templater.us/");
            String title = c.getWindowTitle();
            System.out.println("Window title = " + title);
        } catch (java.lang.RuntimeException e) {
            System.out.println(e);
            System.out.println("==== " + this.name + " Got Error in Test " + "====");
        } finally {
            System.out.println("**** " + this.name + " Finished Test " + "****");
            c.quit();
        }

    }

}
