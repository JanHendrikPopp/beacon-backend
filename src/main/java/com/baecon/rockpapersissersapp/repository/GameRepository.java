package com.baecon.rockpapersissersapp.repository;

import com.baecon.rockpapersissersapp.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {

    List<Game> findBySecondUserIsNull();
}
