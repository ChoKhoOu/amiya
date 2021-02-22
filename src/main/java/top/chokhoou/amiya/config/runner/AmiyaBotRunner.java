package top.chokhoou.amiya.config.runner;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.ListenerHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ChoKhoOu
 */
@Slf4j
@Order(2)
@Component
public class AmiyaBotRunner implements ApplicationRunner {

    @Autowired
    @Qualifier("amiyaEvents")
    List<ListenerHost> events;


    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
 