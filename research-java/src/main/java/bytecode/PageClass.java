package bytecode;

/**
 * 这是一个简单的Java类
 *
 * @author liaojunyong
 */
public class PageClass {
    /**
     * 定义常量
     */
    private static final int size = 10;

    /**
     * 当前第几页
     */
    private int page;

    /**
     * 构造函数
     *
     * @param page
     * @param useless
     */
    public PageClass(int page, int useless) {
        this.page = page;
    }

    /**
     * 计算偏移量
     *
     * @return
     */
    public int calculateOffset() {
        return page * size;
    }

    /**
     * 程序入口
     *
     * @param args
     */
    public static void main(String[] args) {
        PageClass page = new PageClass(3, 4);
        System.out.println(page.calculateOffset());
    }

    /**
     * 用于测试参数类型
     *
     * @param a
     * @param b
     * @param c
     * @param d
     * @param e
     * @param f
     * @param g
     * @param h
     * @param i
     * @param j
     * @return
     */
//    public static PageClass of(long a, float b, double c, int d, short e, char f, byte g, boolean h, String i, EmptyClass j) {
//        return new PageClass(0);
//    }
}
