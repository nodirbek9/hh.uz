package uz.java.dto.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> implements Serializable {
    private T data;
    private boolean success;
    private String message;

    public ApiResponse(T data) {
        this.data = data;
        this.success = true;
    }
}
