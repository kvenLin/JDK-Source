package Synchronized;

/**
 * @Author: clf
 * @Date: 19-3-17
 * @Description:
 */
public class Test {
    public void method(){
        synchronized (this){
            System.out.println("test keyword synchronized...");
        }
    }
}
