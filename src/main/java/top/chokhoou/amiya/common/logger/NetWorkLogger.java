package top.chokhoou.amiya.common.logger;

import net.mamoe.mirai.utils.MiraiLoggerPlatformBase;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static top.chokhoou.amiya.common.constants.CommonConst.NETWORK;

/**
 * @author ChoKhoOu
 */
public class NetWorkLogger extends MiraiLoggerPlatformBase {
    private static final Logger logger = LoggerFactory.getLogger(NETWORK);

    @Nullable
    @Override
    public String getIdentity() {
        return NETWORK;
    }

    @Override
    protected void debug0(@Nullable String s, @Nullable Throwable throwable) {
        logger.debug(s, throwable);
    }

    @Override
    protected void error0(@Nullable String s, @Nullable Throwable throwable) {
        logger.debug(s, throwable);
    }

    @Override
    protected void info0(@Nullable String s, @Nullable Throwable throwable) {
        logger.debug(s, throwable);
    }

    @Override
    protected void verbose0(@Nullable String s, @Nullable Throwable throwable) {
        logger.debug(s, throwable);
    }

    @Override
    protected void warning0(@Nullable String s, @Nullable Throwable throwable) {
        logger.debug(s, throwable);
    }
}
