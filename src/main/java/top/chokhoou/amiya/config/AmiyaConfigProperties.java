package top.chokhoou.amiya.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author ChoKhoOu
 */
@Data
@Component
@ConfigurationProperties("amiya")
public class AmiyaConfigProperties {
    private String configType = "nacos";

    private String nacosAddr = "127.0.0.1";
}
