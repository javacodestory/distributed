package tech.codestory.java.gc;

import com.google.common.collect.Lists;
import lombok.NonNull;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

/**
 * 将会不断创建对象，引发GC
 *
 * @author liaojunyong
 */
public class FullGcGenerator {
    private static final String content = "<script th:src=@{/static/html5shiv/html5shiv.min.js} src=/static/html5shiv/html5shiv.min.js></script>";

    public static void main(String[] args) {
        Random random = new SecureRandom();
        List<String> contentList = Lists.newLinkedList();
        while (true) {
            String newString = String.valueOf(random.nextLong());
            contentList.add(newString);
        }
    }
}
