package com.baecon.rockpapersissersapp.repository;

import com.baecon.rockpapersissersapp.model.Game;
import com.baecon.rockpapersissersapp.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {

    List<Game> findBySecondUserIsNull();

    List<Game> findByFirstUserAndSecondUserIsNotNull(User user);

    List<Game> findBySecondUserAndFirstUserIsNotNull(User user);

    List<Game> findByDisplayed(boolean displayed);
}
