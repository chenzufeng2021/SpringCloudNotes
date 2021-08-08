package com.example.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenzufeng
 * @date 2021/8/8
 * @usage PreLogFilter 一个前置过滤器，用于在请求路由到目标服务前打印请求日志
 */
@Component
@Slf4j
public class PreLogFilter extends ZuulFilter {
    /**
     * 过滤器类型，有pre、routing、post、error四种。
     * @return 过滤器类型
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器执行顺序，数值越小优先级越高。
     * @return 过滤器执行顺序
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 是否进行过滤，返回 true 会执行过滤。
     * @return 是否进行过滤
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 自定义的过滤器逻辑，当 shouldFilter() 返回 true 时会执行。
     * @return null
     * @throws ZuulException ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String remoteHost = request.getRemoteHost();
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        log.info("Remote host: {}, method: {}, uri: {}", remoteHost, method, requestURI);
        return null;
    }
}
