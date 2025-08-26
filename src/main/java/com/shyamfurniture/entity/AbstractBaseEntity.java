package com.shyamfurniture.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@MappedSuperclass
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AbstractBaseEntity {
    @Id
   @Column(name = "ID")
    private String id;
    @Column(name = "CREATE_BY")
    private String createBy;
    @Column(name = "UPDATE_BY")
    private String updateBy;
   @Column(name = "CREATE_AT")
    private LocalDateTime createAt;
   @Column(name = "UPDATE_AT")
   private LocalDateTime updateAt;
   @Column(name = "IS_DELETED")
   private boolean isDeleted;


}
