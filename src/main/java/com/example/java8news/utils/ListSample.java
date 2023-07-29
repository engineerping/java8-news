package com.example.java8news.streamstep2nd.flatmapcustomized;

import com.example.java8news.utils.Employee;

import java.util.ArrayList;
import java.util.List;

public class ListSample {
    public static List<Employee> employees = new ArrayList<>(8);

    static {
        employees.add(new Employee("John", 18, 6666.66));
        employees.add(new Employee("George", 19, 7777.77));
        employees.add(new Employee("Jonson", 36, 8888.88));
        employees.add(new Employee("Mark", 37, 9999.99));
        employees.add(new Employee("Rose", 37, 8888.88));
        employees.add(new Employee("Frank", 37, 7777.77));
    }

}