package tech.codestory.java.enums;

/**
 * @author javacodestory@gmail.com
 * @date 2019/9/2
 */
public class CodeValueEnumUtil {
    /**
     * 根据Code获取枚举
     * 
     * @param clazz
     * @param val
     * @param <E>
     * @return
     */
    public static <E extends CodeValueBaseEnum> E valueOf(Class<E> clazz, int val) {
        for (E e :  clazz.getEnumConstants()) {
            if (e.getCode() == val) {
                return e;
            }
        }
        return null;
    }
}
