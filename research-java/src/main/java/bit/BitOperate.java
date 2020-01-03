package bit;

/**
 * 位操作的例子
 *
 * @author liaojunyong
 */
public class BitOperate {
    public static void left() {
        // 运行结果是20
        System.out.println(5 << 2);
    }

    public static void right() {
        // 运行结果是1
        System.out.println(5 >> 2);
    }

    public static void unsignedRight() {
        // 结果是0
        System.out.println(5 >> 3);

        // 结果是-1
        System.out.println(-5 >> 3);

        // 结果是536870911
        System.out.println(-5 >>> 3);
    }

    public static void and() {
        //结果为1
        System.out.println(5 & 3);
    }

    public static void or() {
        //结果为7
        System.out.println(5 | 3);
    }

    public static void xor() {
        //结果为6
        System.out.println(5 ^ 3);
    }

    public static void not() {
        //结果为-6
        System.out.println(~5);
    }

    public static void main(String[] args) {
        left();
        right();
        unsignedRight();
        and();
        or();
        xor();
        not();
    }
}
