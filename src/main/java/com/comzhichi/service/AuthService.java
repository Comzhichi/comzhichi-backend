package com.comzhichi.service;

import com.comzhichi.dto.request.LoginRequestDTO;
import com.comzhichi.dto.request.UserRequestDTO;
import com.comzhichi.dto.response.AuthResponseDTO;
import com.comzhichi.dto.response.LoginResponseDTO;
import com.comzhichi.exception.BusinessRuleException;
import com.comzhichi.exception.InvalidCredentialsException;
import com.comzhichi.mapper.UserMapper;
import com.comzhichi.model.Role;
import com.comzhichi.model.Student;
import com.comzhichi.model.User;
import com.comzhichi.repository.StudentRepository;
import com.comzhichi.repository.UserRepository;
import com.comzhichi.security.JwtService;
import com.comzhichi.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final StudentRepository studentRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwt;
    private final UserMapper userMapper;

    @Transactional
    public AuthResponseDTO register(UserRequestDTO dto) {
        if (userRepo.findByEmail(dto.email()).isPresent()) {
            throw new BusinessRuleException("El correo ya está registrado");
        }

        if (userRepo.findByName(dto.name()).isPresent()) {
            throw new BusinessRuleException("El nombre ya está en uso");
        }

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(encoder.encode(dto.password()));
        user.setEnabled(true);
        user.setRole(Role.STUDENT);

        userRepo.save(user);

        Student student = new Student();
        student.setUser(user);
        student.setFullName(dto.fullName());

        studentRepo.save(student);

        String token = jwt.generate(user);

        return new AuthResponseDTO(
                token,
                "Bearer",
                userMapper.toResponse(user)
        );
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {

        String email = dto.email().trim().toLowerCase();

        User user = userRepo.findByEmail(email)
                .filter(u -> encoder.matches(
                        dto.password(),
                        u.getPassword()
                ))
                .orElseThrow(InvalidCredentialsException::new);

        String token = jwt.generate(user);

        return new LoginResponseDTO(
                token,
                "Bearer",
                jwt.expiresInSeconds(),
                user.getRole().name()
        );

    }
}