package com.bidweather.backend_core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subcategory")
@NoArgsConstructor
@Getter
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "subcategory")
    private List<CategorySubcategory> categorySubcategories = new ArrayList<>();

    @Column(name = "subcategory_name")
    private String subcategoryName;
}
