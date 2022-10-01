package cn.how2j.trend.web;

import cn.how2j.trend.pojo.Index;
import cn.how2j.trend.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description : TODO
 * @Author : yzn_f
 * @Date : 2022/9/28 0:10
 * @Version : 1.0
 **/
@RestController
public class IndexController {
    @Autowired
    IndexService indexService;

    //  http://127.0.0.1:8001/freshCodes
    //  http://127.0.0.1:8001/getCodes
    //  http://127.0.0.1:8001/removeCodes

    @GetMapping("/freshCodes")
    public List<Index> fresh() throws Exception {
        return indexService.fresh();
    }
    @GetMapping("/getCodes")
    public List<Index> get() throws Exception {
        return indexService.get();
    }
    @GetMapping("/removeCodes")
    public String remove() throws Exception {
        indexService.remove();
        return "remove codes successfully";
    }
}
