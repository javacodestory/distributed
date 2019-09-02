package tech.codestory.java.lombok;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * 用于测试 lombok 生成的代码
 * 
 * @author javacodestory@gmail.com
 * @date 2019/9/2
 */
@Slf4j
@Data
public class SimpleObject {
    @ToString.Exclude
    private int code;

    @EqualsAndHashCode.Exclude
    private String value;
}
