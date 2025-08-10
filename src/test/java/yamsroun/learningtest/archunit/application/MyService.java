package yamsroun.learningtest.archunit.application;

public class MyService {

    MyService2 myService2;
    //MyAdapter myAdapter;

    void run() {
        myService2 = new MyService2();
        System.out.println(myService2);
    }
}
