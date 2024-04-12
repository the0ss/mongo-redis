package com.theos.mongoredis.response;

import lombok.Builder;

@Builder
public class RequestApi {
    private String name;
    private String phoneNumber;
}
