package com.example.sixsevenchan.dto.response;

import com.example.sixsevenchan.entity.Thread;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BoardResponse {

    private Long id;

    private String prefix;

    private String name;

}
