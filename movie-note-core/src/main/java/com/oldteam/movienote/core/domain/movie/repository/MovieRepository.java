package com.oldteam.movienote.core.domain.movie.repository;

import com.oldteam.movienote.core.domain.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
