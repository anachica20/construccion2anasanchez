package app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor

public class UserDto {
    private long id;
    private String userName;
    private String password;
    private String role;
    private PersonDto personId;
}
