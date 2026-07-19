package com.example.sixsevenchan.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardCreateRequest {

    @NotBlank(message = "Префикс доски не может быть пустым")
    @Size(min = 1, max = 5, message = "Префикс должен быть от 1 до 5 символов")
    private String prefix;

    @NotBlank(message = "Название доски не может быть пустым")
    @Size(max = 20, message = "Название не должно превышать 20 символов")
    private String name;

}
