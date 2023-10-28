package com.pn.filter;

import com.alibaba.fastjson.JSON;
import com.pn.pojo.Result;
import com.pn.utils.WarehouseConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义的登录限制过滤器
 */
public class LoginCheckFilter implements Filter {

    private StringRedisTemplate redisTemplate;

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //过滤器拦截到请求 执行的方法
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //白名单请求直接放行
        List<String> list = new ArrayList<>();
        list.add("/captcha/captchaImage");
        list.add("/login");

        //过滤器拦截到的当前请求的url路径
        String path = request.getServletPath();
        if (list.contains(path)){
                chain.doFilter(request,response);
                return;
            }

        //其他请求都校验是否携带Token,以及判断redis中是否存在Token的键
        String token = request.getHeader(WarehouseConstants.HEADER_TOKEN_NAME);
        System.out.println();

        //1、有 说明已登录 放行
        if (StringUtils.hasText(token)&&redisTemplate.hasKey(token)){
            chain.doFilter(request,response);
            return;
        }

        //2、没有，说明未登录或token过期，请求不放行，并给前端响应json串
        Map<String,Object> map = new HashMap<>();
        map.put("code",502);
        map.put("message","您未登录");
        Result result = Result.err(Result.CODE_ERR_UNLOGINED, "您未登录");
        String jsonString = JSON.toJSONString(result);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(jsonString);
        out.flush();
        out.close();
    }
}
