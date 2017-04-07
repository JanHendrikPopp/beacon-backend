package com.baecon.rockpapersissersapp.repository;

import com.baecon.rockpapersissersapp.model.Stats;
import com.baecon.rockpapersissersapp.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends CrudRepository<Stats, Long> {

    Stats findByUser(User user);
}
