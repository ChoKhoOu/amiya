package top.chokhoou.amiya.utils.amiya.config;

import cn.hutool.core.thread.ExecutorBuilder;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import top.chokhoou.amiya.common.constants.CommonConst;
import top.chokhoou.amiya.utils.CustomThreadFactory;

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
public class NacosUtil implements AmiyaConfig{
    /*************** group id start **************/
    private static final String NACOS_GROUP_AMIYA = "amiya";
    /*************** group id end **************/

    private final ThreadPoolExecutor CONFIG_REFRESH_EXECUTOR = ExecutorBuilder.create()
            .setCorePoolSize(2)
            .setMaxPoolSize(2)
            .setWorkQueue(new LinkedBlockingQueue<>())
            .setThreadFactory(new CustomThreadFactory("nacos-config-refresh-"))
            .build();

    private static final Map<String, ConfigHandler> CONFIG_HANDLER_MAP = new ConcurrentHashMap<>();
    private static final Map<String, String> CONFIG_CACHE = new ConcurrentHashMap<>();

    private final ConfigService instance;

    @Override
    public ConfigHandler amiya() {
        return CONFIG_HANDLER_MAP.computeIfAbsent(NACOS_GROUP_AMIYA, k -> new Group(NACOS_GROUP_AMIYA));
    }


    private String buildCacheKey(String groupId, String dataId) {
        return groupId + CommonConst.COLON + dataId;
    }


    private String getConfig(String groupId, String dataId) {
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

    public NacosUtil(String ipAddress) {
        Properties properties = new Properties();
        properties.put("serverAddr", ipAddress);
        ConfigService instance = null;
        try {
            instance = NacosFactory.createConfigService(properties);
        } catch (NacosException e) {
            log.error("Create config service failed: reason={}", e.getErrMsg());
        }
        this.instance = instance;
    }


    private ConfigService getInstance() {

        return instance;
    }


    /************* group ****************/

    private class Group extends AbstractGroup {
        private String groupId;

        Group(String groupId) {
            this.groupId = groupId;
        }

        @Override
        public String getConfigString(String dataId) {
            return getConfig(groupId, dataId);
        }
    }

}
