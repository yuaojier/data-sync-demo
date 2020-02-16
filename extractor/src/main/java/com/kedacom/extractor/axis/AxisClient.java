package com.kedacom.extractor.axis;

import com.alibaba.fastjson.JSON;
import com.kedacom.extractor.properties.WebserviceProperties;
import org.apache.axis.client.Call;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

@Component
public class AxisClient {

    private final WebserviceProperties webserviceProperties;

    public AxisClient(WebserviceProperties webserviceProperties) {
        this.webserviceProperties = webserviceProperties;
    }

    public  <T> T queryClient(String serviceName, Object json) throws MalformedURLException, RemoteException {
        Call call = new Call(webserviceProperties.getWebServiceUrl());
        call.setOperationName(serviceName);
        return (T)call.invoke(new Object[]{webserviceProperties.getUserCode(), webserviceProperties.getPassWord(), JSON.toJSONString(json)});
    }
}
