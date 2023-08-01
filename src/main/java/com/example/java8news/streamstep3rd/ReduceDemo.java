package com.example.java8news.streamstep3rd;

import com.example.java8news.utils.Employee;
import com.example.java8news.utils.ListSample;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

/**
 *
 */
public class ReduceDemo {

    @Test
    void testReduce1() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Optional<Integer> result = list.stream().reduce((a, b) -> {return a + b;});
        System.out.println(result.get());

        //匿名内部类实现
        Optional<Integer> result2 = list.stream().reduce(new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer + integer2;
            }
        });
    }

    @Test
    void testReduce2() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Integer result = list.stream().reduce(-1, (a, b) -> {return a + b;});
        System.out.println(result);

        //匿名内部类实现
        Integer result2 = list.stream().reduce(-1, new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer + integer2;
            }
        });
    }

    @Test
    void testMapReduce() {
        //求公司里所有人的薪资综合，即公司的月薪水总支出
        Stream<Employee> streamEmployees = ListSample.employees.stream();
        streamEmployees.map(Employee::getSalary) //map操作获取员工的薪水
                .reduce(0D, (a, b) -> {return a + b;}); //reduce操作加总薪水，//联想如果Employee是存储在数据库中，那么对应的 SQL 怎么写
    }

    /**
     * TODO: 不知道怎么使用
     */
    @Test
    void testReduce3() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Integer result = list.stream().reduce(-1, (a, b) -> {return a + b;}, (c, d) -> {return c + d;});
        System.out.println(result);

    }


}
