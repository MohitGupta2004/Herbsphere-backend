package com.project.herbsphere.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String plantName;

    private String partUsed;

    @Column(columnDefinition = "TEXT")
    private String medicinalBenefits;

    private String imageUrl;
}
