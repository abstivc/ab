package com.song.base;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
public class ResponseBase {

    public ResponseBase() {
    }
    public ResponseBase(Integer respCode, String respDesc, Object data) {
        this.respCode = respCode;
        this.respDesc = respDesc;
        this.data = data;
    }

    private Integer respCode;
    private String respDesc;
    private Object data;
}
