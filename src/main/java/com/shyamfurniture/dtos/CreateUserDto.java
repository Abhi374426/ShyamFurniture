package com.shyamfurniture.dtos;

import com.shyamfurniture.exception.BadRequestException;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
import com.shyamfurniture.validate.ImageNameValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {
    private String id;
    @NotBlank(message = "Name cannot be empty or null !!")
    @Size(min = 3, message = "Name must be at least 3 characters long !!")
    private String name;
    @NotBlank(message = "Email cannot be empty or null !!")
    @Email(message = "Invalid email format !!")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "Email must follow a valid pattern like example@domain.com"
    )
    private String email;
    @NotBlank(message = "Password cannot be empty or null !!")
    private String password;
    private String gender;
    private String about;
    @ImageNameValid
    private String imageName;
    @NotBlank(message = "createBy cannot be empty or nul !!")
    private String createBy;
    private String updateBy;


}
