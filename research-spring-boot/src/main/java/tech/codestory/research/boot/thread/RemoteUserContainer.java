package tech.codestory.research.boot.thread;

/**
 * 用于存储当前用户名
 *
 * @author liaojunyong
 */
public class RemoteUserContainer {
    private static ThreadLocal<String> remoteUserContainer = new ThreadLocal<>();

    public static void setRemoteUser(String remoteUser) {
        remoteUserContainer.set(remoteUser);
    }

    public static String getRemoteUser() {
        return remoteUserContainer.get();
    }
}
