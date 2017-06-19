package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * token 本地存储
 * Created by wuyong on 2017/6/19.
 */
public class TokenCache {

    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);

    public static final String TOKEN_PREFIX = "token_";

    //本地缓存
    //链式操作，LRU算法
    private static LoadingCache<String,String> loadingCache = CacheBuilder.newBuilder()
            //初始化数量，最大存储个数
            .initialCapacity(1000).maximumSize(10000)
            //数值，单位
            .expireAfterAccess(12, TimeUnit.HOURS)
            //匿名实现类
            .build(new CacheLoader<String, String>() {
                //当调用get取值时，当找不到key值时就调用这个方法
                @Override
                public String load(String s) throws Exception {
                    return "null";  //修改null为“null”,防止对比是出现空指针
                }
            });

    public static void setKey(String key,String value){
        loadingCache.put(key,value);
    }

    public static String getKey(String key){
        String value = null;
        try {
            value = loadingCache.get(key);
            if ("null".equals(value)){
                return null;
            }
            return value;
        } catch (ExecutionException e) {
            logger.error("loadingCache get error",e);
        }
        return  null;
    }
}
