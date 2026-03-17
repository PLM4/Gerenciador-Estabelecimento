package com.gereciador.estabelecimento.services;

import com.gereciador.estabelecimento.controllers.dto.request.UserRequestDTO;
import com.gereciador.estabelecimento.controllers.dto.response.UserResponseDTO;
import com.gereciador.estabelecimento.entities.User;
import com.gereciador.estabelecimento.exceptions.UserNotFoundException;
import com.gereciador.estabelecimento.mapper.UserMapper;
import com.gereciador.estabelecimento.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO save(UserRequestDTO obj) {
        User user = this.mapper.toEntity(obj);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        User userReturn = this.userRepository.save(user);
        return this.mapper.toDTO(userReturn);
    }

    public UserResponseDTO update(Long primaryKey, UserRequestDTO obj) {
        User user = this.userRepository.findById(primaryKey).orElseThrow(UserNotFoundException::new);
        if (obj.password() != null && this.passwordEncoder.matches(obj.password(), user.getPassword())) {
            user.setPassword(this.passwordEncoder.encode(obj.password()));
        }

        if (obj.username() != null) user.setUsername(obj.username());
        if (obj.role() != null) user.setRole(obj.role());
        User userSaved = this.userRepository.save(user);
        return this.mapper.toDTO(userSaved);
    }

    public void delete(Long primaryKey) {
        this.userRepository.deleteById(primaryKey);
    }

    public UserResponseDTO getById(Long primaryKey) {
        User userFind = this.userRepository.findById(primaryKey).orElseThrow(UserNotFoundException::new);
        return this.mapper.toDTO(userFind);
    }

    public List<UserResponseDTO> getAll() {
        List<User> users = this.userRepository.findAll();
        return users.stream()
                .map(this.mapper::toDTO)
                .toList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found username " + username));
    }
}
