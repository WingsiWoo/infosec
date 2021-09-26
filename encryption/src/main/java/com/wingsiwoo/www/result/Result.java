package com.wingsiwoo.www.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import static com.wingsiwoo.www.result.ReturnMsgConstant.*;

/**
 * @author WingsiWoo
 * @date 2021/9/26
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {
    private int code;

    private String message;

    private boolean isSuccess;

    private T data;

    public Result() {
    }

    public Result(int code, String message, boolean isSuccess) {
        this.code = code;
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public Result(int code, String message, boolean isSuccess, T data) {
        this.code = code;
        this.message = message;
        this.isSuccess = isSuccess;
        this.data = data;
    }

    public static <E> Result<E> operateSuccess() {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, true);
    }

    public static <E> Result<E> operateSuccess(E e) {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, true, e);
    }

    public static <E> Result<E> operateFail() {
        return new Result<>(FAIL_CODE, FAIL_MESSAGE, false);
    }

    public static <E> Result<E> operateFail(E e) {
        return new Result<>(FAIL_CODE, FAIL_MESSAGE, false, e);
    }

    public static <E> Result<E> autoOperate(boolean isSuccess) {
        return isSuccess ? operateSuccess() : operateFail();
    }

    public static <E> Result<E> autoOperate(boolean isSuccess, E e) {
        return isSuccess ? operateSuccess(e) : operateFail(e);
    }
}
