package com.example.sixsevenchan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ThreadResponse {

    private Long id;
    private String subject;
    private boolean isPinned;
    private boolean isClosed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PostResponse> posts;

}
