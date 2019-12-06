package tech.codestory.java.jvm.heap;

/**
 * @author junyongliao
 * @date 2019/12/3
 * @since 1.0.0
 */
public class SurvivorRatioDemo {
    public static void main(String[] args) {
        byte[] bytes = null;
        for (int i = 0; i < 9; i++) {
            bytes = new byte[1 * 1024 * 1024];

            System.out.print("maxMemory=");
            System.out.println(Runtime.getRuntime().maxMemory() + " bytes");
            System.out.print("free mem=");
            System.out.println(Runtime.getRuntime().freeMemory() + " bytes");
            System.out.print("total mem=");
            System.out.println(Runtime.getRuntime().totalMemory() + " bytes");
            System.out.println();
        }
    }
}
