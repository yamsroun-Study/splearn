package yamsroun.learningtest.archunit.adapter;

import yamsroun.learningtest.archunit.application.MyService;

public class MyAdapter {

    MyService myService;

    void run() {
        myService = new MyService();
        System.out.println(myService);
    }
}
