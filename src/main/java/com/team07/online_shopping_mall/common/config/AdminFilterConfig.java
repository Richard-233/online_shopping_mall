package com.team07.online_shopping_mall.common.config;

import com.team07.online_shopping_mall.filter.AdminFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * admin过滤器配置
 */
@Configuration
public class AdminFilterConfig {
    @Bean
    public AdminFilter adminFilter() {
        return new AdminFilter();
    }

    @Bean(name = "adminFilterConf")
    public FilterRegistrationBean adminFilterConfig() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(adminFilter());
        filterRegistrationBean.addUrlPatterns("/api/catalog/admin/*");
//        filterRegistrationBean.addUrlPatterns("/api/product/*");
//        filterRegistrationBean.addUrlPatterns("/api/order/*");
        filterRegistrationBean.setName("adminFillerConfig");
        return filterRegistrationBean;
    }
}
