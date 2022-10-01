package cn.how2j.trend.service;

import cn.how2j.trend.pojo.Index;
import cn.how2j.trend.util.SpringContextUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description : 使用工具类RestTemplate 来获取如下地址 http://127.0.0.1:8090/indexes/codes.json
 * @CacheConfig(cacheNames="indexes") 表示缓存的名称就是indexes
 * @Author : yzn_f
 * @Date : 2022/9/28 0:05
 * @Version : 1.0
 **/
@Service
@CacheConfig(cacheNames="indexes")
public class IndexService {
    private List<Index> indexes;
    @Autowired
    RestTemplate restTemplate;

    /**
     * @Description : 刷新数据
     * 1 先运行 fetch_indexes_from_third_part 来获取数据
     * 2 删除可能存在的数据
     * 3 保存新获取的数据
     * 调用SpringContextUtil.getBean重新获取了一次indexService，是因为Springboot有bug，无法直接在已存在的方法中调用Redis的方法，所以只能重新获取一次
     * @HystrixCommand 这表示如果fresh()获取失败了，就自动调用 third_part_not_connected 并返回
     * @Author : yzn_f
     * @Date : 2022/10/1 16:48
     * @return java.util.List<cn.how2j.trend.pojo.Index>
     */
    @HystrixCommand(fallbackMethod = "third_part_not_connected")
    public List<Index> fresh() {
        indexes =fetch_indexes_from_third_part();
        IndexService indexService = SpringContextUtil.getBean(IndexService.class);
        indexService.remove();
        return indexService.store();
    }

    //清空indexService里面的所有数据
    @CacheEvict(allEntries=true)
    public void remove(){

    }

    //往Redis里面保存数据
    //@Cacheable(key="'all_codes'") 表示保存到 redis 用的 key 就会是 all_codes
    @Cacheable(key="'all_codes'")
    public List<Index> store(){
        System.out.println(this);
        return indexes;
    }

    //从Redis中获取数据
    @Cacheable(key="'all_codes'")
    public List<Index> get(){
        return CollUtil.toList();
    }

    public List<Index> fetch_indexes_from_third_part(){
        List<Map> temp= restTemplate.getForObject("http://127.0.0.1:8090/indexes/codes.json",List.class);
        return map2Index(temp);
    }

    //third_part_not_connected 这方法就是自己创建个指数对象，然后返回集合
    public List<Index> third_part_not_connected(){
        System.out.println("third_part_not_connected()");
        Index index= new Index();
        index.setCode("000000");
        index.setName("无效指数代码");
        return CollectionUtil.toList(index);
    }

    /**
     * @Description : 获取出来的内容是Map类型，所以需要个 map2Index把 Map 转换为 Index
     * @Author : yzn_f
     * @Date : 2022/9/28 0:09
     * @param temp
     * @return java.util.List<cn.how2j.trend.pojo.Index>
     */
    private List<Index> map2Index(List<Map> temp) {
        List<Index> indexes = new ArrayList<>();
        for (Map map : temp) {
            String code = map.get("code").toString();
            String name = map.get("name").toString();
            Index index= new Index();
            index.setCode(code);
            index.setName(name);
            indexes.add(index);
        }

        return indexes;
    }
}
