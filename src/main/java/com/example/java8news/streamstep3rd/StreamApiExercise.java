package com.example.java8news.streamstep3rd;

import com.example.java8news.utils.Employee;
import com.example.java8news.utils.ListSample;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamApiExercise {

    /**
     * 选出薪资大于 7000 的员工，并按年龄排序
     */
    @Test
    void testCollect1_1() {
        Stream<Employee> streamEmployees = ListSample.employees.stream();
        System.out.println("一共有" + streamEmployees.count() + "个员工");

        // 不能再重复使用 streamEmployees，因为已经关闭了，需要重新创建一个同样的 Stream流
        Stream<Employee> streamEmployees_1 = ListSample.employees.stream();
        List<Employee> listEmployees = streamEmployees_1
                .filter((e) -> e.getSalary() > 7000)
                .sorted((e1, e2) -> {return e1.getAge() - e2.getAge();})
                .collect(Collectors.toList());
        listEmployees.forEach(System.out::println);


    }
}
