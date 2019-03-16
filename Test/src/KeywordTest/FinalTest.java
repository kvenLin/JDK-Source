package KeywordTest;

/**
 * @Author: clf
 * @Date: 19-3-15
 * @Description:
 */
public class FinalTest {
    static class Son extends Parent{
        public void getName(){

        }
    }

    public static void main(String[] args) {

    }

    static class Parent{
        /**
         * 因为private修饰，子类中不能继承到此方法，因此，子类中的getName方法是重新定义的、
         * 属于子类本身的方法，编译正常
         */
        private final void getName(){
            System.out.println("I'm Parent class");
        }

        /**
         * 如果父类的final方法为public，则子类继承父类后重写会出现编译异常
         */
//        public final void getName(){
//            System.out.println("I'm Parent class");
//        }
    }
}
