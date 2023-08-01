package com.example.java8news.streamstep2nd.flatmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

//@SpringBootTest //如果不需要启动 spring 容器，则不需要注解 @SpringBootTest
public class FlatMapCustomized {
    public static List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd");

    @Test
    void test1() {
        //Stream.map 的作用是接收一个lambda坐位参数，该方法作用于集合中的没有个元素，返回值是一个流，流中的元素类型是 所接收的lambda的返回值类型
        //list.stream().map((item) -> item.toUpperCase());
        Stream<String> stream = list.stream().map(String::toUpperCase);
        stream.forEach(System.out::println);
    }

    /**
     * 测试自定义的 myFlatMap
     */
    @Test
    void testMyFlatMap() {
        myFlatMap();
    }

    /**
     * 模拟 Stream.flatMap() 语法糖
     */
    public static void myFlatMap() {
        Stream<Stream<Character>> stream = list.stream().map(FlatMapCustomized::filterCharacter);
        stream.forEach(
                (myStream) -> {myStream.forEach(System.out::println);}
        );
    }

    /**
     * 将输入字符串中的 '字符' 提取出来放入一个流中
     * @param str
     * @return
     */
    public static Stream<Character> filterCharacter(String str) {
        List<Character> list  = new ArrayList();

        for (Character c : str.toCharArray()) {
            list.add(c);
        }

        return list.stream();
    }


    //---------------------------------------------------------------------------------------
    /**
     * 测试 Stream 原生的 flatMap
     */
    @Test
    void testFlatMap() {
        Stream<Character> stream = list.stream().flatMap(FlatMapCustomized::filterCharacter);
        stream.forEach(System.out::println);
    }

}
