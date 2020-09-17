package tech.codestory.research.boot.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.slf4j.helpers.MessageFormatter;
import tech.codestory.research.boot.model.UserInfo;

/**
 * 重载的数据转换
 *
 * @author liaojunyong
 */
public class SecurityConverter extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        StringBuilder info = new StringBuilder();
        String message = iLoggingEvent.getMessage();
        Object[] objects = iLoggingEvent.getArgumentArray();
        if (message.contains("{}") && objects != null && objects.length > 0) {
            String formatMessage = MessageFormatter.arrayFormat(message, objects).getMessage();
            info.append(formatMessage);
        } else {
            info.append(message);
            if (objects != null) {
                int paramIndex = 1;
                for (Object object : objects) {
                    info.append("\r\n\tparam ").append(paramIndex++).append(" : ");
                    if (object instanceof UserInfo) {
                        UserInfo userInfo = (UserInfo) object;
                        info.append("userInfo[account=").append(userInfo.getAccount()).append(",name=").append(userInfo.getName()).append("]");
                    } else {
                        info.append(object);
                    }
                }
            }
        }
        return info.toString();
    }
}
