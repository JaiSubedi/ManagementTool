package com.test.toolmanagement.exception;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {
    private String message;
    private int errorCode;
}
