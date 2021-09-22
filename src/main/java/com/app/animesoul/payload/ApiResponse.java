package com.app.animesoul.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ApiResponse<T> {
    private int code;
    private T data;
    private String message;

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> ApiResponse<T> v(T data) {
        return new ApiResponse<>(200, data, null);
    }

    public static <T> ApiResponse<T> x(Integer code, String message) {
        return new ApiResponse<>(code, null, message);
    }
}
