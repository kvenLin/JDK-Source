package lambda.applycation;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: clf
 * @Date: 19-11-15
 * @Description:
 */
public class PersonTest {

    public static void main(String[] args) {
        Person person1 = new Person("zhangsan", 20);
        Person person2 = new Person("lisi", 30);
        Person person3 = new Person("wangwu", 40);
        List<Person> persons = Arrays.asList(person1, person2, person3);

        PersonTest test = new PersonTest();

        List<Person> personList = test.getPersonByUsername("zhangsan", persons);
        personList.forEach(person -> System.out.println(person.getUsername()));


    }

    public List<Person> getPersonByUsername(String username, List<Person> persons) {
        return persons.stream()
                .filter(person -> person.getUsername().equals(username))
                .collect(Collectors.toList());
    }

    public List<Person> getPersonByAge(int age, List<Person> persons) {
        BiFunction<Integer, List<Person>, List<Person>> biFunction = (integer, people) -> {
            return people.stream().filter(person -> person.getAge() < age).collect(Collectors.toList());
        };
        return biFunction.apply(age, persons);
    }
}
