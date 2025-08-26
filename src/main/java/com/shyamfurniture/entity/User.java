package com.shyamfurniture.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.processing.SQL;

@Entity
@Table(name = "USER_TABLE")
@SQLDelete(sql = "UPDATE user_table SET is_deleted=true WHERE id=?")
@Where(clause = "is_deleted=false")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@SuperBuilder
public class User extends AbstractBaseEntity {


    private String name;
    @Column(name = "user_email", unique = true)
    private String email;
    @Column(name = "user_password", length = 10)
    private String password;
    @Column(name = "gender")
    private String gender;
    @Column(name = "about",length = 100)
    private String about;
    @Column(name = "user_image_name")
    private String imageName;
}