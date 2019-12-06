package tech.codestory.java.jvm.xss;

/**
 * 测试调用栈深度
 * 
 * @author junyongliao
 * @date 2019/11/27
 */
public class TestStackDeep {
    private static int count = 0;

    public static void recursion() {
        count++;
        try {
            System.out.println("deep of calling = " + count);
        } catch (StackOverflowError e) {
        }
        recursion();
    }

    public static void main(String[] args) {
        try {
            recursion();
        } catch (Exception e) {
        }
    }
}
