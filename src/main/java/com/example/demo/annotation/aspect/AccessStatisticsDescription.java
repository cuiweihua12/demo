package com.example.demo.annotation.aspect;


import com.example.demo.annotation.AccessStatistics;
import com.example.demo.utils.IpUtils;
import com.example.demo.utils.RedisCache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description: 统计访问
 * @author: cuiweihua
 * @create: 2021-02-13 11:11
 */
@Aspect
@Component
public class AccessStatisticsDescription {

    private Log logger = LogFactory.getLog(getClass());

    @Resource
    private RedisCache redisCache;

    @Pointcut("@annotation(com.example.demo.annotation.AccessStatistics)")
    public void pointCut() {

    }

    @Before(value = "pointCut()")
    public void doBefore(JoinPoint joinPoint){
        handleAccessStatistics(joinPoint);
    }

    private void handleAccessStatistics(JoinPoint joinPoint){
        AccessStatistics statistics = getAnnotationLog(joinPoint);
        if (statistics == null) {
            return;
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String id = request.getParameter("id");
        Integer count = redisCache.getCacheObject("ACCESS_STATISTICS_KEY" + id);
        //获取ip
        String ipAddr = IpUtils.getIpAddr(request);
        String expriedKey = "ACCESS_STATISTICS_EXPIRED_KEY"+"_"+ipAddr+":"+id;
        String expired = redisCache.getCacheObject(expriedKey);
        try {
            if (StringUtils.isEmpty(expired)) {
                //访问次数加一
                if (count==null){
                    count = 0;
                }
                count++;
                redisCache.setCacheObject("ACCESS_STATISTICS_KEY"+id,count);
                redisCache.setCacheObject(expriedKey,id,30, TimeUnit.MINUTES);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            logger.error("统计访问次数时发生错误!");
        }
    }


    /**
     * 获取客户端请求参数中所有的信息
     * @param request
     * @return
     */
    private Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                //如果字段的值为空，判断若值为空，则删除这个字段>
                if (null == res.get(en) || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }

    /**
     * 判断是否存在注解，如果存在就获取
     */
    private AccessStatistics getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(AccessStatistics.class);
        }
        return null;
    }

}