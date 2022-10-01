package cn.how2j.trend;

import cn.hutool.core.util.NetUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Description : 默认端口号是 8761
 * 进行端口号判断，如果这个端口已经被占用了，就给出提示信息。
 * 使用 SpringApplicationBuilder 进行启动
 * @SpringBootApplication 表示这是一个启动类
 * @EnableEurekaServer 表示这是一个注册中心
 * @Author : yzn_f
 * @Date : 2022/9/27 23:37
 * @Version : 1.0
 **/
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        //8761 这个端口是默认的，就不要修改了，后面的子项目，都会访问这个端口。
        int port = 8761;
        if(!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("端口%d被占用了，无法启动%n", port );
            System.exit(1);
        }
        new SpringApplicationBuilder(EurekaServerApplication.class).properties("server.port=" + port).run(args);
    }
}
