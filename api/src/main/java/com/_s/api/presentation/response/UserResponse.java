package com._s.api.presentation.response;

import com._s.api.domain.shared.Address;
import com._s.api.domain.user.User;
import com._s.api.domain.valueobject.Cpf;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private String id;
    private String name;
    private String lastName;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Address address;
    private String socialName;
    private String instagram;
    private String cpf;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.address = user.getAddress();
        this.socialName = user.getSocialName();
        this.instagram = user.getInstagram();
        this.cpf = user.getCpf().getFormatted();
    }
}
