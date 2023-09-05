package com.oldteam.movienote.core.domain.movie.repository;

import com.oldteam.movienote.core.domain.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByName(String name);

    @Query("select movie.id from Movie movie where movie.name = :name and movie.code = :code")
    Long findIdByNameAndCode(String name, String code);

    boolean existsByName(String name);
    boolean existsByNameAndCode(String name, String code);
}
