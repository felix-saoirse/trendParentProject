package cn.how2j.trend.web;

import cn.how2j.trend.pojo.IndexData;
import cn.how2j.trend.pojo.Profit;
import cn.how2j.trend.pojo.Trade;
import cn.how2j.trend.service.BackTestService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
    @GetMapping("/simulate/{code}/{startDate}/{endDate}")
    @CrossOrigin
    public Map<String,Object> backTest(
            @PathVariable("code") String code
            //参数可以接受开始日期和结束日期
            ,@PathVariable("startDate") String strStartDate
            ,@PathVariable("endDate") String strEndDate
    ) throws Exception {
        //计算出开始日期和结束日期并返回
        List<IndexData> allIndexDatas = backTestService.listIndexData(code);

        String indexStartDate = allIndexDatas.get(0).getDate();
        String indexEndDate = allIndexDatas.get(allIndexDatas.size()-1).getDate();

        //根据开始日期和结束日期获取对应日期范围的数据
        allIndexDatas = filterByDateRange(allIndexDatas,strStartDate, strEndDate);

        int ma = 20;
        float sellRate = 0.95f;
        float buyRate = 1.05f;
        float serviceCharge = 0f;
        Map<String,?> simulateResult= backTestService.simulate(ma,sellRate, buyRate,serviceCharge, allIndexDatas);
        List<Profit> profits = (List<Profit>) simulateResult.get("profits");
        List<Trade> trades = (List<Trade>) simulateResult.get("trades");

        Map<String,Object> result = new HashMap<>();
        result.put("indexDatas", allIndexDatas);
        result.put("indexStartDate", indexStartDate);
        result.put("indexEndDate", indexEndDate);
        result.put("profits", profits);
        result.put("trades", trades);
        return result;
    }

    private List<IndexData> filterByDateRange(List<IndexData> allIndexDatas, String strStartDate, String strEndDate) {
        if(StrUtil.isBlankOrUndefined(strStartDate) || StrUtil.isBlankOrUndefined(strEndDate) ){
            return allIndexDatas;
        }

        List<IndexData> result = new ArrayList<>();
        Date startDate = DateUtil.parse(strStartDate);
        Date endDate = DateUtil.parse(strEndDate);

        for (IndexData indexData : allIndexDatas) {
            Date date =DateUtil.parse(indexData.getDate());
            if(
                    date.getTime()>=startDate.getTime() &&
                            date.getTime()<=endDate.getTime()
            ) {
                result.add(indexData);
            }
        }
        return result;
    }
}
