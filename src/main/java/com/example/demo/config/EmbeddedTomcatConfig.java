package com.example.demo.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 * 功能描述: tomcat8.0及之后请求路径特殊符号处理 <br>
 * @Date: 2020/12/29 11:42
 **/
@Component
public class EmbeddedTomcatConfig implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "<>[]^`{|}"));
    }
}