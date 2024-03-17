package com.creswave.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequestDTO {
    @NotEmpty(message = "Content cannot be empty")
    @Size(min = 10, max = 1000, message = "Content must be between 10 and 1000 characters")
    private String content;
//    Fetch the post By Id
    private Long  postId;
}
