package com.song.base;

import com.song.constants.Constants;
import org.springframework.stereotype.Component;

@Component
public class BaseApiController implements Constants {

    // 通用封装
    public ResponseBase setResult(Integer code, String message, Object data) {
        return new ResponseBase(code, message, data);
    }

    public ResponseBase setResultSuccess() {
        return setResult(HTTP_RES_CODE_200, HTTP_RES_CODE_200_VALUE, null);
    }

    public ResponseBase setResultSuccess(Object data) {
        return setResult(HTTP_RES_CODE_200, HTTP_RES_CODE_200_VALUE, data);
    }

    public ResponseBase setResultError() {
        return setResult(HTTP_RES_CODE_500, HTTP_RES_CODE_500_VALUE, null);
    }

    public ResponseBase setResultError(Object data) {
        return setResult(HTTP_RES_CODE_500, HTTP_RES_CODE_500_VALUE, data);
    }
}
