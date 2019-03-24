package entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private boolean flag;//是否成功
    private Integer code;//返回状态码
    private String message;//返回信息
    private Object data;//返回数据

    public Result() {
    }

    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }
}
