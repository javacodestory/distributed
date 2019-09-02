package tech.codestory.java.enums;

import org.testng.annotations.Test;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;
import static org.testng.Assert.*;

/**
 * 用于测试 Enum 的一些代码
 *
 * @author javacodestory@gmail.com
 * @date 2019/9/2
 */
public class RetCodeEnumTest {
    @Test
    public void testEnumSet() {
        Class<RetCodeEnum> enumClass = RetCodeEnum.class;
        Set<RetCodeEnum> retCodeEnums = EnumSet.allOf(enumClass);

        assert retCodeEnums.size() == RetCodeEnum.values().length;

        assert RetCodeEnum.OK.equals(CodeValueEnumUtil.valueOf(RetCodeEnum.class, 0));

        assert CodeValueEnumUtil.valueOf(RetCodeEnum.class, -9999) == null;
    }
}
