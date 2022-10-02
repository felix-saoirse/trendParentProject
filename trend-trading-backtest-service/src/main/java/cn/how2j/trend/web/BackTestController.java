package cn.how2j.trend.web;

import cn.how2j.trend.pojo.IndexData;
import cn.how2j.trend.service.BackTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description : TODO
 * @Author : yzn_f
 * @Date : 2022/10/2 17:34
 * @Version : 1.0
 **/
@RestController
public class BackTestController {
    @Autowired
    BackTestService backTestService;

    /**
     * @Description : 控制器，返回的数据是放在一个 Map 里的，而目前的key是 indexDatas。
     * @CrossOrigin 表示允许跨域
     * @Author : yzn_f
     * @Date : 2022/10/2 17:35
     * @param code
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @GetMapping("/simulate/{code}")
    @CrossOrigin
    public Map<String,Object> backTest(@PathVariable("code") String code) throws Exception {
        List<IndexData> allIndexDatas = backTestService.listIndexData(code);
        Map<String,Object> result = new HashMap<>();
        result.put("indexDatas", allIndexDatas);
        return result;
    }
}
