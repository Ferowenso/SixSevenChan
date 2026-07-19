package com.example.sixsevenchan.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ThreadCreateRequest {

    @Size(max = 100, message = "Текст слишком длинный")
    private String subject;

    @NotBlank(message = "Сообщение не может быть пустым")
    private String text;

}
