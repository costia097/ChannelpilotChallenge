package com.challenge.channelpilotChallenge.domain.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
    @SequenceGenerator(
            name = "comment_seq",
            sequenceName = "comment_id_seq",
            allocationSize = 1
    )
    private Long id;

    private Long commentId;

    private LocalDateTime time;

    private String text;
    private String by;

    @Column(name="parent_id", insertable=false, updatable=false)
    private Long parentId;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private List<Comment> kids;
}
