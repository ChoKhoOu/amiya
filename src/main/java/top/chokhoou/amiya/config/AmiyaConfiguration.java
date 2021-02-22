package top.chokhoou.amiya.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.chokhoou.amiya.utils.amiya.config.AmiyaConfig;
import top.chokhoou.amiya.utils.amiya.config.JsonUtil;
import top.chokhoou.amiya.utils.amiya.config.NacosUtil;

/**
 * @author ChoKhoOu
 */
@Configuration
public class AmiyaConfiguration {
    private static final String NACOS = "nacos";
    @Autowired
    AmiyaConfigProperties config;

    @Bean(name = "AmiyaConfig")
    public AmiyaConfig getNacosUtil() {
        if (NACOS.equals(config.getConfigType())) {
            return new NacosUtil(config.getNacosAddr());
        } else {
            return new JsonUtil();
        }

    }
}
