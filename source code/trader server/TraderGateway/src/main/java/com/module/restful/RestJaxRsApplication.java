package com.module.restful;

import com.module.bean.Transaction;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

/**
 * Created by siqi.lou on 2017/6/2.
 */
public class RestJaxRsApplication extends ResourceConfig {
    public RestJaxRsApplication() {

        // register application resources
        this.register(TraderRest.class);
        this.register(ProductRest.class);
        this.register(OrderBookRest.class);
        this.register(OrderRest.class);
        this.register(BrokerRest.class);
        this.register(TransactionRest.class);

        // register filters
        register(RequestContextFilter.class);
        //register(LoggingResponseFilter.class);
        //register(CORSResponseFilter.class);
        register(JacksonJsonProvider.class);

        // register features
        register(JacksonFeature.class);
        //register(MultiPartFeature.class);
    }
}
