package tech.codestory.research.boot.thread;

/**
 * 用于存储当前用户名
 *
 * @author liaojunyong
 */
public class InheritableRemoteUserContainer {
    private static ThreadLocal<String> remoteUserContainer = new InheritableThreadLocal<>();

    public static void setRemoteUser(String remoteUser) {
        remoteUserContainer.set(remoteUser);
    }

    public static String getRemoteUser() {
        return remoteUserContainer.get();
    }
}
