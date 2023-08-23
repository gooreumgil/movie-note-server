package com.oldteam.movienote.common.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResDto {

    private String message;
    private String code;

    public ErrorResDto(String message, String code) {
        this.message = message;
        this.code = code;
    }
}
