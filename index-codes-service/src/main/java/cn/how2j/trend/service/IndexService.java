package cn.how2j.trend.service;

import cn.how2j.trend.pojo.Index;
import cn.hutool.core.collection.CollUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description : 服务类，直接从reids获取数据。 如果没有数据，则会返回 “无效指数代码”。
 * @Author : yzn_f
 * @Date : 2022/10/1 18:50
 * @Version : 1.0
 **/
@Service
@CacheConfig(cacheNames="indexes")
public class IndexService {
    private List<Index> indexes;

    @Cacheable(key="'all_codes'")
    public List<Index> get(){
        Index index = new Index();
        index.setName("无效指数代码");
        index.setCode("000000");
        return CollUtil.toList(index);
    }
}
