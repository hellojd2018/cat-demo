package com.example.demo.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Configuration
public class HttpClientConfigure {
    @Value("${http.maxTotal}")
    int maxTotal;
    /**
     * 设置每个主机的并发数
     */
    @Value("${http.defaultMaxPerRoute}")
    int defaultMaxPerRoute;


    @Value("${http.connectTimeout}")
    int connectTimeout;
    @Value("${http.connectionRequestTimeout}")
    int connectionRequestTimeout;

    @Value("${http.connectionRequestTimeout}")
    int socketTimeout;

    @Bean
    public PoolingHttpClientConnectionManager newConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxTotal);
        connectionManager.setValidateAfterInactivity(5000);
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        return connectionManager;
    }

    public HttpClientBuilder newHttpClientBuilder() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(newConnectionManager());
        return httpClientBuilder;
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public CloseableHttpClient newCloseableHttpClient() {
        return newHttpClientBuilder().build();
    }

    @Bean
    public RequestConfig newRequestConfig() {
        return RequestConfig.custom().setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout).build();
    }

    @Bean
    public IdleConnectionEvictor newIdleConnectionEvictor() {
        return new IdleConnectionEvictor(newConnectionManager());
    }

    public class IdleConnectionEvictor extends Thread {

        private final HttpClientConnectionManager connMgr;

        private volatile boolean shutdown;

        public IdleConnectionEvictor(HttpClientConnectionManager connMgr) {
            this.connMgr = connMgr;
            // 自启动
            this.start();
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(5000);
                        // 关闭失效的连接
                        connMgr.closeExpiredConnections();
                    }
                }
            } catch (InterruptedException ex) {
                // 结束
            }
        }

        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }
    }
}
