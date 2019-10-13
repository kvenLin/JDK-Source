package JavaSE;

/**
 * @Author: clf
 * @Date: 19-4-14
 * @Description:
 * 测试try catch 中进行了 return 操作 finally是否会执行
 */
public class TryReturnFinallyTest {
    public static void main(String[] args) {
        test();
    }

    public static void test(){
        try {
            throw new Exception("未知异常");
        }catch (Exception e){
            System.out.println("捕获异常直接 return 返回...");
            return;
        }finally {
            System.out.println("执行 finally 模块代码...");
        }
    }
    /**
     * 运行结果:
     *
     * 捕获异常直接 return 返回...
     * 执行 finally 模块代码...
     *
     * 分析:
     * 说明不管是否在 catch 模块中进行 return 操作都会执行后续的 finally 代码块
     */
}
