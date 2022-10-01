package cn.how2j.trend.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Description : 用于获取当前的端口号。 因为这个指数代码微服务会做成集群，不同的微服务使用的是不同的端口号，可以通过获取并打印出来知道当前是哪个
 * @Component 表示这个bean交给Spring管理
 * @Author : yzn_f
 * @Date : 2022/10/1 18:43
 * @Version : 1.0
 **/
@Component
public class IpConfiguration implements ApplicationListener<WebServerInitializedEvent> {
    private int serverPort;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
    }

    public int getPort() {
        return this.serverPort;
    }
}
