package com.kedacom.extractor.cxf;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsClientFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import javax.xml.namespace.QName;


public class CXFClient {
    public static <T> T queryClient(String serviceName,Object[] objects) throws Exception {
        JaxWsClientFactoryBean jaxWsClientFactoryBean = new JaxWsClientFactoryBean();
        jaxWsClientFactoryBean.setAddress("URL");
        jaxWsClientFactoryBean.setServiceName(new QName(""));
        Client client = jaxWsClientFactoryBean.create();
        return (T) client.invoke(serviceName, objects);
    }
}
