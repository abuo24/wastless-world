package uz.wastlessworld.app.model;// Author - Orifjon Yunusjonov
// t.me/coderr24

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result {
    private boolean success;
    private String message;
    private Object Data;
    private String exception;

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Result(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        Data = data;
    }

    public Result(boolean success, String message, String exception) {
        this.success = success;
        this.message = message;
        this.exception = exception;
    }

    public Result success(Object object){
        return new Result(true, "success",object);
    }
    public Result delete(){
        return new Result(true, "delete success");
    }
    public Result error(Exception e){
        return new Result(false, "error",e);
    }
}
