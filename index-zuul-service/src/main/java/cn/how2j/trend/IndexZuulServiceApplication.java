package cn.how2j.trend;

import cn.hutool.core.util.NetUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Description : 启动类，端口号8031
 * @EnableZuulProxy 表示这是一个网关
 * @EnableDiscoveryClient 和@EnableEurekaClient 的相同点：都是能够让注册中心能够发现，扫描到该服务。
 * 不同点：@EnableEurekaClient只适用于Eureka作为注册；@EnableDiscoveryClient 可以是其他注册中心。
 * @Author : yzn_f
 * @Date : 2022/10/2 16:41
 * @Version : 1.0
 **/
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableDiscoveryClient
public class IndexZuulServiceApplication {
        //  http://127.0.0.1:8031/api-codes/codes
        public static void main(String[] args) {
            int port = 8031;
            if(!NetUtil.isUsableLocalPort(port)) {
                System.err.printf("端口%d被占用了，无法启动%n", port );
                System.exit(1);
            }
            new SpringApplicationBuilder(IndexZuulServiceApplication.class).properties("server.port=" + port).run(args);

        }
}
