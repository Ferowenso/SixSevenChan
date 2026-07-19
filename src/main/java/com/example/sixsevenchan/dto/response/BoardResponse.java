package com.example.sixsevenchan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class BoardResponse {

    private Long id;

    private String prefix;

    private String name;

}
