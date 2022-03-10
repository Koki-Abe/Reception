package jp.reception.soarest.config;

import java.util.Collections;

import javax.servlet.SessionTrackingMode;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * 共通設定クラス
 * 
 * @author k.abe
 * @version 1.0
 */
@Configuration
public class CommonConfig {
    @Bean
    public ServletContextInitializer servletContextInitializer() {
        // 初回アクセス時に、URLにSessionIDが付与されるのを防ぐ
        // https://blog.ik.am/entries/353
        ServletContextInitializer initializer = servletContext -> {
            servletContext.setSessionTrackingModes(
                Collections.singleton(SessionTrackingMode.COOKIE)
            );
        };
        return initializer;
    }
}
