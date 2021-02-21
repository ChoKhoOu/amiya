package top.chokhoou.amiya.utils;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import top.chokhoou.amiya.common.constants.CommonConst;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ChoKhoOu
 */
@Slf4j
public class NacosUtil {
    private static final String NACOS_SERVER_ADDR = "172.17.0.1";

    /*************** group id start **************/
    private static final String NACOS_GROUP_HEALTH_CARD = "health_card";
    /*************** group id end **************/

    private static final ThreadPoolExecutor CONFIG_REFRESH_EXECUTOR = ExecutorBuilder.create()
            .setCorePoolSize(2)
            .setMaxPoolSize(2)
            .setWorkQueue(new LinkedBlockingQueue<>())
            .setThreadFactory(new CustomThreadFactory("nacos-config-refresh-"))
            .build();

    private static final Map<String, String> CONFIG_CACHE = new ConcurrentHashMap<>();

    private static volatile ConfigService INSTANCE = getInstance();

    public static ConfigHandler healthCard() {
        return Group.HEALTH_CARD;
    }

    private static String buildCacheKey(String groupId, String dataId) {
        return groupId + CommonConst.COLON + dataId;
    }

    private static String getConfig(String groupId, String dataId) {
        final String key = buildCacheKey(groupId, dataId);
        return CONFIG_CACHE.computeIfAbsent(key, (k) -> {
            try {
                return getInstance().getConfigAndSignListener(dataId, groupId, 5000, new Listener() {
                    @Override
                    public Executor getExecutor() {
                        return CONFIG_REFRESH_EXECUTOR;
                    }

                    @Override
                    public void receiveConfigInfo(String configInfo) {
                        log.info("Refresh config: groupId={}, dataId={}", groupId,
                                dataId);
                        CONFIG_CACHE.put(key, configInfo);
                    }
                });
            } catch (NacosException e) {
                log.error("Nacos get config time out: groupId={}, dataId={}", groupId, dataId);
            }
            return null;
        });
    }

    private static ConfigService getInstance() {
        if (INSTANCE == null) {
            synchronized (NacosUtil.class) {
                if (INSTANCE == null) {
                    Properties properties = new Properties();
                    properties.put("serverAddr", NACOS_SERVER_ADDR);
                    ConfigService instance = null;
                    try {
                        instance = NacosFactory.createConfigService(properties);
                    } catch (NacosException e) {
                        log.error("Create config service failed: reason={}", e.getErrMsg());
                    }
                    INSTANCE = instance;
                }
            }
        }
        return INSTANCE;
    }


    /************* group ****************/

    public interface ConfigHandler {

        /**
         * 获取配置
         *
         * @param dataId key
         * @return string
         */
        String getConfigString(String dataId);

        /**
         * 获取配置,将其转换为object
         *
         * @param dataId key
         * @return <T>
         */
        default <T> T getConfigObject(String dataId, Class<T> clazz) {
            return JSONUtil.toBean(getConfigString(dataId), clazz);
        }

        /**
         * 获取配置,将其转换为List
         *
         * @param dataId key
         * @return List<T>
         */
        default <T> List<T> getConfigObjectList(String dataId, Class<T> clazz) {
            return JSONUtil.toList(getConfigString(dataId), clazz);
        }
    }

    private enum Group implements ConfigHandler {
        /**
         * HEALTH_CARD
         */
        HEALTH_CARD {
            @Override
            public String getConfigString(String dataId) {
                return NacosUtil.getConfig(NACOS_GROUP_HEALTH_CARD, dataId);
            }
        };
    }
}
