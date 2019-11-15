package lambda.applycation;

/**
 * @Author: clf
 * @Date: 19-11-15
 * @Description:
 */
public class Person {
    private String username;
    private int age;

    public Person(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
