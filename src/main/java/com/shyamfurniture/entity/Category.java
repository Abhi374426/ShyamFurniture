package com.shyamfurniture.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@Entity
@Table(name = "CATEGORY_TABLE")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Category{
    @Id
    @Column(name = "category_id")
    private String categoryId;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "cover_image")
    private String coverImage;


}
