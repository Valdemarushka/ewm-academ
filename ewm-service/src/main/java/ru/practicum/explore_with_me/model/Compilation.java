package ru.practicum.explore_with_me.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "compilations")
@DynamicUpdate
@Getter
@Setter
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "is_pinned", nullable = false)
    private boolean isPinned;
    @Column(nullable = false)
    private String title;
    @Transient
    List<Event> events;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "events_compilations", joinColumns = @JoinColumn(name = "compilation_id"))
    @Column(name = "event_id")
    private Set<Integer> eventsIds;
}
