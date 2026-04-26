package com.bidweather.backend_core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@NoArgsConstructor
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private final List<CategorySubcategory> categorySubcategories = new ArrayList<>();
}
