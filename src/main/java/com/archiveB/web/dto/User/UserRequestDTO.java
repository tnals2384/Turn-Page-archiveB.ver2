package com.archiveB.web.dto.User;

import lombok.Getter;
import lombok.Setter;

public class UserRequestDTO {

    @Getter
    @Setter
    public static class AddUserRequest {
        private String email;
        private String password;
    }

}
