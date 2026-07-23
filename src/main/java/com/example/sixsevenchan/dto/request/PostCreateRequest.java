package com.example.sixsevenchan.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PostCreateRequest {

    @NotBlank(message = "Сообщение не может быть пустым")
    @Size(max = 2000, message = "Текст слишком длинный")
    private String text;

    private boolean isSage = false;

}
