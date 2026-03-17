package com.gereciador.estabelecimento.mapper;

import com.gereciador.estabelecimento.controllers.dto.request.UserRequestDTO;
import com.gereciador.estabelecimento.controllers.dto.response.UserResponseDTO;
import com.gereciador.estabelecimento.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<UserResponseDTO, UserRequestDTO, User>{

    @Override
    public User toEntity(UserRequestDTO dtoRequest) {
        User user = new User();
        user.setPassword(dtoRequest.password());
        user.setUsername(dtoRequest.username());
        user.setRole(dtoRequest.role());
        return user;
    }

    @Override
    public UserResponseDTO toDTO(User entity) {
        return new UserResponseDTO(entity.getId(), entity.getUsername());
    }
}
