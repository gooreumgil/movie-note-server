package com.oldteam.movienote.api.domain.movie.asyncevent;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MovieSaveEvent {

    private String name;
    private String code;

}
