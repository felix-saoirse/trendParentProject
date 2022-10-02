package cn.how2j.trend.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description : TODO
 * @Author : yzn_f
 * @Date : 2022/10/2 18:24
 * @Version : 1.0
 **/
@Controller
public class ViewController {
    @GetMapping("/")
    public String view() throws Exception {
        return "view";
    }
}
