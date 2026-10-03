package com.comzhichi.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sections")
@Getter
@Setter
@NoArgsConstructor
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "group_code", nullable = false, length = 20)
    private String groupCode;

    @Column(nullable = false, length = 100)
    private String schedule;

    @Column(length = 50)
    private String classroom;

    @Column(name = "total_vacancies", nullable = false)
    private Integer totalVacancies;

    @Column(name = "available_vacancies", nullable = false)
    private Integer availableVacancies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
}