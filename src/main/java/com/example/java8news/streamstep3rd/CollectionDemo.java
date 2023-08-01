package com.example.java8news.streamstep3rd;

import com.example.java8news.utils.Employee;
import com.example.java8news.utils.ListSample;
import com.example.java8news.utils.Status;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * collect方法,
 * 将流转换为其他形式。接收一个 Collector 接口的实现为参数，作为给 Stream 中的元素作汇总的方法；
 * 这里的 Collector 接口的实现 一般使用 Collector 接口的工具类（工厂类） Collectors 来提供。
 */
public class CollectionDemo {
    //收集/////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 收集成 Collectors 工厂类所创建的集合中，例如 List
     */
    @Test
    void testCollect1_1() {
        Stream<Employee> streamEmployees = ListSample.employees.stream();
        List<String> employeeNameList = streamEmployees
                .map(Employee::getName)
                .collect(Collectors.toList());
        employeeNameList.forEach(System.out::println);

    }

    /**
     * 手动实现 Collector 接口
     */
//    void testCollect1_2() {
//        Stream<Employee> streamEmployees = ListSample.employees.stream();
//        List<String> employeeNameList = streamEmployees
//                                           .map(Employee::getName)
//                                           .collect(new Collector<Employee, Object, List<String>>() {
//
//            @Override
//            public Supplier<Object> supplier() {
//                return null;
//            }
//
//            @Override
//            public BiConsumer<Object, Employee> accumulator() {
//                return null;
//            }
//
//            @Override
//            public BinaryOperator<Object> combiner() {
//                return null;
//            }
//
//            @Override
//            public Function<Object, List<Employee>> finisher() {
//                return null;
//            }
//
//            @Override
//            public Set<Characteristics> characteristics() {
//                return null;
//            }
//        });
//
//        employeeNameList.forEach(System.out::println);
//
//    }


    //通用收集到任何集合Collectors.toCollection/////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 收集到任类型的集合中,如收集到 LinkedHashSet 中
     * 相当于 SQL： select distinct name from Employee order by name;
     */
    @Test
    void testCollect2_1() {
        Stream<Employee> streamEmployees = ListSample.employees.stream();
        Set<String> employeeNameList = streamEmployees
                .map(Employee::getName)
                .collect(Collectors.toCollection(new Supplier<Set<String>>() {

            @Override
            public LinkedHashSet<String> get() {
                return new LinkedHashSet<>();
            }
        }));
        employeeNameList.forEach(System.out::println);

    }

    @Test
    void testCollect2_2() {
        Stream<Employee> streamEmployees = ListSample.employees.stream();
        Set<String> employeeNameList = streamEmployees
                .map(Employee::getName)
                .collect(Collectors.toCollection(() -> {return new LinkedHashSet<>();}));
        employeeNameList.forEach(System.out::println);
    }

    @Test
    void testCollect2_3() {
        Stream<Employee> streamEmployees = ListSample.employees.stream();
        Set<Employee> employeeNameList = streamEmployees.collect(Collectors.toCollection(LinkedHashSet<Employee>::new));
        employeeNameList.forEach(System.out::println);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 收集集合中元素的个数
     * 相当于 SQL: select count(name) from Employee;
     */
    @Test
    void testCollect3_1() {
        Stream<Employee> streamEmployees = ListSample.employees.stream();
        streamEmployees.collect(Collectors.counting());
    }

    /**
     * 求某一列最大值
     * 相当于 SQL: select MAX(salary) from Employee;
     */
    @Test
    void testCollect3_2() {
        Stream<Employee> streamEmployees = ListSample.employees.stream();
        Optional<Double> OptionalResult = streamEmployees
                .map(Employee::getSalary)
                .collect(Collectors.maxBy(Double::compare));
        Double result = OptionalResult.orElseGet(() -> {return 0D;});
        System.out.println(result);
    }

    /**
     * 求某一列最小值
     * 相当于 SQL: select MIN(salary) from Employee;
     */
    @Test
    void testCollect3_3() {
        Stream<Employee> streamEmployees = ListSample.employees.stream();
        Optional<Double> OptionalResult = streamEmployees
                .map(Employee::getSalary)
                .collect(Collectors.minBy(Double::compare));
        Double result = OptionalResult.orElseGet(() -> {return 0D;});
        System.out.println(result);
    }

    /**
     * 收集集合某一列的和
     * 相当于 SQL: select SUM(salary) from Employee;
     */
    @Test
    void testCollect3_4() {
        Stream<Employee> streamEmployees = ListSample.employees.stream();
        Double result = streamEmployees
                    .map(Employee::getSalary)
                    .collect(Collectors.summingDouble( (item) -> {return item;}));
        System.out.println(result);
    }

    //分组Collectors.groupingBy/////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 分组，相当于  SQL 中的 group by
     * 相当于  SQL: select SUM(salary) from Employee;
     */
    @Test
    void testCollect4_1() {
        Stream<Employee> streamEmployees = ListSample.employees.stream();
        Map<Status, List<Employee>> statusListMap = streamEmployees.collect(Collectors.groupingBy(Employee::getStatus));
        statusListMap.forEach((a, b) -> {
            System.out.println(a + ":" + b);
        });

    }


    /**
     * 多级分组，相当于  SQL 中的 group by
     * 相当于  SQL: select SUM(salary) from Employee;
     */
    @Test
    void testCollect4_2() {
        Stream<Employee> streamEmployees = ListSample.employees.stream();
         streamEmployees.collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy(Employee::getSalary)));

    }

    /**
     * 多级分组，相当于  SQL 中的 group by
     * 相当于  SQL: select SUM(salary) from Employee;
     */
    @Test
    void testCollect4_3() {
        Stream<Employee> streamEmployees = ListSample.employees.stream();
        Map<Status, Map<String, List<Employee>>> statusListMap = streamEmployees.collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
            if (((Employee) e).getAge() <= 35) {
                return "青年";
            } else if (((Employee) e).getAge() <= 50 ) {
                return "中年";
            } else {
                return "老年";
            }
        })));

        statusListMap.forEach((status, employeeMap) -> {
            employeeMap.forEach((ageCategory, employeesList) -> {
                employeesList.forEach((employee) -> {System.out.println(status + ":" + ageCategory + ":" + employee);});
                    }
            );
        });

    }

    //分区Collectors.partitioningBy/////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 分区 partitioningBy
     * 使用 Controller 将 集合分为满足条件的区和不满足条件的区
     */
    @Test
    void testCollect4_4() {
        Stream<Employee> streamEmployees = ListSample.employees.stream();
        Map<Boolean, List<Employee>> statusListMap = streamEmployees.collect(Collectors.partitioningBy((e) -> e.getSalary() > 10000));
        statusListMap.forEach((status, employeesList) -> {
            System.out.println(status + ":" + employeesList);
        });
    }

    //统计Collectors.summarizingXXX/////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 统计
     */
    @Test
    void testCollect5_1() {
        Stream<Employee> streamEmployees = ListSample.employees.stream();
        DoubleSummaryStatistics dss = streamEmployees.collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(dss.getMax());
        System.out.println(dss.getMin());
        System.out.println(dss.getSum());
        System.out.println(dss.getAverage());
    }


    //拼接/////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 拼接
     */
    @Test
    void testCollect6_1() {
        Stream<Employee> streamEmployees = ListSample.employees.stream();
        String result = streamEmployees
                .map(Employee::getName)
                        .collect(Collectors.joining(",", "~~", "~~")).concat("===");
        System.out.println(result);
    }
}
