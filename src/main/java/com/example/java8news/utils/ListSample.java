package com.example.java8news.utils;

import java.util.ArrayList;
import java.util.List;

public class ListSample {
    public static List<Employee> employees = new ArrayList<>(8);

    static {
        employees.add(new Employee("John", 18, 6666.66, Status.BUSY));
        employees.add(new Employee("George", 19, 7777.77, Status.BUSY));
        employees.add(new Employee("Jonson", 36, 8888.88, Status.FREE));
        employees.add(new Employee("Mark", 37, 9999.99, Status.FREE));
        employees.add(new Employee("Rose", 37, 8888.88, Status.VOCATION));
        employees.add(new Employee("Frank", 51, 7777.77, Status.VOCATION));
    }

}