package com.kedacom.synccore.api;

public interface IHttpResponse {
    String getCode();

    String getMsg();

    Object getData();
}
