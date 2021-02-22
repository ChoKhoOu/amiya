package top.chokhoou.amiya.bot;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.event.ListenerHost;
import net.mamoe.mirai.utils.BotConfiguration;
import top.chokhoou.amiya.common.logger.AmiyaLogger;
import top.chokhoou.amiya.common.logger.NetWorkLogger;
import top.chokhoou.amiya.utils.exception.ServiceException;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ChoKhoOu
 */
public class AmiyaBot {
    private static volatile boolean IS_STARTUP_COMPLETED = false;

    private static volatile Bot INSTANCE;

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();

    public static void startUp(Long account, String pwd, String deviceInfo, List<ListenerHost> events) {
        if (IS_STARTUP_COMPLETED) {
            throw new ServiceException("Amiya bot is already startup.");
        }
        // config
        BotConfiguration config = new BotConfiguration();
        config.fileBasedDeviceInfo(deviceInfo);
        config.loadDeviceInfoJson(deviceInfo);
        config.setBotLoggerSupplier(bot -> new AmiyaLogger());
        config.setNetworkLoggerSupplier(bot -> new NetWorkLogger());

        // instantiate the bot
        INSTANCE = BotFactory.INSTANCE.newBot(account, pwd, config);
        INSTANCE.login();

        // register the event
        for (ListenerHost event : events) {
        }

        EXECUTOR_SERVICE.submit(() -> INSTANCE.join());
        IS_STARTUP_COMPLETED = true;


    }

    public static Bot getBot() {
        return INSTANCE;
    }
}
