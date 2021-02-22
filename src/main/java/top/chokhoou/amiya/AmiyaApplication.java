package top.chokhoou.amiya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


/**
 * @author ChoKhoOu
 */
@SpringBootApplication
@EnableConfigurationProperties
public class AmiyaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmiyaApplication.class, args);
    }

}
