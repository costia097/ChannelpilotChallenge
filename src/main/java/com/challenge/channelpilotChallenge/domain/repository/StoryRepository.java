package com.challenge.channelpilotChallenge.domain.repository;

import com.challenge.channelpilotChallenge.domain.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    @Query("SELECT story FROM Story story LEFT JOIN FETCH story.comments comments LEFT JOIN comments.kids WHERE story.storyId = :storyId")
    List<Story> findAllByStoryIdWithComments(@Param("storyId") Long storyId);
}
