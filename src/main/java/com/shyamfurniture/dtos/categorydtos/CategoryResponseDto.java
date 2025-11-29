package com.shyamfurniture.dtos.categorydtos;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDto {

    private String categoryId;

    private String title;

    private String description;

    private String coverImage;
}
