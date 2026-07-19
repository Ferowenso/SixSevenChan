package com.example.sixsevenchan.dto.request;

import com.example.sixsevenchan.entity.Thread;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostCreateRequest {

    @NotBlank(message = "Сообщение не может быть пустым")
    @Size(max = 2000, message = "Текст слишком длинный")
    private String text;


}
