package cn.how2j.trend.service;

import cn.how2j.trend.pojo.IndexData;
import cn.hutool.core.collection.CollUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description : 从redis获取数据
 * @Author : yzn_f
 * @Date : 2022/10/1 19:29
 * @Version : 1.0
 **/
@Service
@CacheConfig(cacheNames="index_datas")
public class IndexDataService {
    @Cacheable(key="'indexData-code-'+ #p0")
    public List<IndexData> get(String code){
        return CollUtil.toList();
    }
}
