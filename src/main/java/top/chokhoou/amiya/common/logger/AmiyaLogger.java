package top.chokhoou.amiya.common.logger;

import net.mamoe.mirai.utils.MiraiLoggerPlatformBase;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static top.chokhoou.amiya.common.constants.CommonConst.*;

/**
 * @author ChoKhoOu
 */
public class AmiyaLogger extends MiraiLoggerPlatformBase {
    private static final Logger logger = LoggerFactory.getLogger(AMIYA);

    @Nullable
    @Override
    public String getIdentity() {
        return AMIYA;
    }

    @Override
    protected void debug0(@Nullable String s, @Nullable Throwable throwable) {
        logger.debug(s, throwable);
    }

    @Override
    protected void error0(@Nullable String s, @Nullable Throwable throwable) {
        logger.error(s, throwable);
    }

    @Override
    protected void info0(@Nullable String s, @Nullable Throwable throwable) {
        logger.info(s, throwable);
    }

    @Override
    protected void verbose0(@Nullable String s, @Nullable Throwable throwable) {
        logger.trace(s, throwable);
    }

    @Override
    protected void warning0(@Nullable String s, @Nullable Throwable throwable) {
        logger.warn(s, throwable);
    }
}
