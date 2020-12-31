package org.sd.todo.dto.payload;

public class BaseResponse {
    private final String message;

    public BaseResponse(String content) {
        this.message = content;
    }

    public String getMessage() {
        return message;
    }

}
