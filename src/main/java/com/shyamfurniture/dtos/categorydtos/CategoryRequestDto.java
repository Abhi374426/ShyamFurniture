package com.shyamfurniture.dtos.categorydtos;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequestDto {

    private String title;

    private String description;

}
