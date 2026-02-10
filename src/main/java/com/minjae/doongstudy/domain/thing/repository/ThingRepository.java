package com.minjae.doongstudy.domain.thing.repository;

import com.minjae.doongstudy.domain.thing.entity.Thing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThingRepository extends JpaRepository<Thing, Long> {
}
