package com.shyamfurniture.dtos;

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
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdateUserDto {
    private String id;
    @Size(min = 3, message = "Name must be at least 3 characters long !!")
    private String name;
    @Email(message = "Invalid email format !!")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "Email must follow a valid pattern like example@domain.com"
    )
    private String email;
    private String password;
    private String gender;
    private String about;
    @ImageNameValid
    private String imageName;
    private String createBy;
    @NotBlank(message = "updateBy cannot be empty !!")
    private String updateBy;
}
