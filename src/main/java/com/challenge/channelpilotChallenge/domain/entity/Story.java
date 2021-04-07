package com.challenge.channelpilotChallenge.domain.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.List;

@Data
@Entity
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "story_seq")
    @SequenceGenerator(
            name = "story_seq",
            sequenceName = "story_id_seq",
            allocationSize = 1
    )
    private Long id;

    private Long storyId;

    private Integer descendants;
    private Integer score;
    private String title;
    private String url;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "story_id")
    private List<Comment> comments;
}
