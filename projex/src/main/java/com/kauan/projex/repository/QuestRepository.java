package com.kauan.projex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kauan.projex.model.Quest;

public interface QuestRepository extends JpaRepository<Quest, Long> {
}
