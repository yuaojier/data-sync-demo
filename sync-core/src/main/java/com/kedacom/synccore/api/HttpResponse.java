package com.kedacom.synccore.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpResponse<T> implements IHttpResponse {
    public static final String HTTP_CODE_OK = "200";

    public static final String HTTO_CODE_ERROR = "500";

    private String code;

    private String msg;

    private T data;

    public HttpResponse(String status, String msg, T data) {
        this.code = status;
        this.msg = msg;
        this.data = data;
    }

    public HttpResponse() {
        this(HTTP_CODE_OK, "SUCCESS", null);
    }

    public HttpResponse(T data) {
        this(HTTP_CODE_OK, "SUCCESS", data);
    }

    public HttpResponse(String status, String msg) {
        this(status, msg, null);
    }
}
