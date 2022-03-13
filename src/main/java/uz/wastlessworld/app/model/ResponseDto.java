/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uz.wastlessworld.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    private boolean success;
    private String reason;
    private Long recordsFiltered;
    private Integer recordsTotal;
    private T data;
    private String message;

    public ResponseDto() {
    }

    public ResponseDto(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    public ResponseDto(boolean success, String reason, T data) {
        this.success = success;
        this.reason = reason;
        this.data = data;
    }

    public static <T> ResponseDto ok(T data) {
        ResponseDto<T> responseDto = new ResponseDto<>();
        responseDto.setSuccess(true);
        responseDto.setReason(null);
        responseDto.setData(data);

        return responseDto;
    }

    public static <T> ResponseDto error(String reason, T data) {
        ResponseDto<T> responseDto = new ResponseDto<>();
        responseDto.setSuccess(false);
        responseDto.setReason(reason);
        responseDto.setData(data);

        return responseDto;
    }
}
