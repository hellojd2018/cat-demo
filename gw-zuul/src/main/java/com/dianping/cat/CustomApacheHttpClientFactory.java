package com.dianping.cat;

import com.dianping.cat.context.CatConstantsExt;
import com.dianping.cat.context.CatContextImpl;
import com.dianping.cat.message.Transaction;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.RequestLine;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.springframework.cloud.commons.httpclient.ApacheHttpClientFactory;
import org.springframework.cloud.commons.httpclient.DefaultApacheHttpClientFactory;
import java.io.IOException;

/**
 * 专门处理url映射
 */
public class CustomApacheHttpClientFactory extends DefaultApacheHttpClientFactory implements ApacheHttpClientFactory {
    public HttpClientBuilder createBuilder() {
        HttpClientBuilder builder = super.createBuilder();
        builder.addInterceptorFirst(new HttpRequestInterceptor() {
            @Override
            public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {

                RequestLine requestLine = httpRequest.getRequestLine();
                com.dianping.cat.context.CatContextImpl catContext = new CatContextImpl();
                Cat.logRemoteCallClient(catContext,Cat.getManager().getDomain());
                httpRequest.addHeader(CatConstantsExt.CAT_HTTP_HEADER_ROOT_MESSAGE_ID,catContext.getProperty(Cat.Context.ROOT));
                httpRequest.addHeader(CatConstantsExt.CAT_HTTP_HEADER_PARENT_MESSAGE_ID,catContext.getProperty(Cat.Context.PARENT));
                httpRequest.addHeader(CatConstantsExt.CAT_HTTP_HEADER_CHILD_MESSAGE_ID,catContext.getProperty(Cat.Context.CHILD));

                String url = requestLine.getUri();
                Transaction t = Cat.newTransaction(CatConstants.TYPE_REMOTE_CALL, url);
                try {
                    Cat.logEvent(CatConstants.TYPE_REMOTE_CALL+".method",requestLine.getMethod());
                    t.setSuccessStatus();
                    t.complete();
                } catch (Throwable e) {
                    t.setStatus(e);
                    Cat.logError(e);
                } finally {
                    t.complete();
                }
            }
        });
        return builder;
    }
}
