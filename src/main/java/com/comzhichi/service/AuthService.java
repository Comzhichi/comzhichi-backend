package com.comzhichi.service;

import com.comzhichi.dto.request.ChangePasswordRequestDTO;
import com.comzhichi.dto.request.LoginRequestDTO;
import com.comzhichi.dto.request.UserRequestDTO;
import com.comzhichi.dto.response.AuthResponseDTO;
import com.comzhichi.exception.BusinessRuleException;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.mapper.UserMapper;
import com.comzhichi.model.*;
import com.comzhichi.repository.*;
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
    private final TeacherRepository teacherRepo;
    private final CoordinatorRepository coordinatorRepo;
    private final ParentRepository parentRepo;
    private final StudentParentRepository studentParentRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwt;
    private final UserMapper userMapper;

    @Transactional
    public AuthResponseDTO register(UserRequestDTO dto) {
        if (userRepo.findByEmail(dto.email()).isPresent())
            throw new BusinessRuleException("El correo ya está registrado");

        User user = new User();
        user.setFullName(dto.fullName());
        user.setEmail(dto.email());
        user.setPassword(encoder.encode(dto.password()));
        user.setEnabled(true);
        user.setRole(dto.role());
        userRepo.save(user);

        switch (dto.role()) {
            case STUDENT -> {
                Student s = new Student();
                s.setUser(user);
                studentRepo.save(s);
            }
            case TEACHER -> {
                Teacher t = new Teacher();
                t.setUser(user);
                t.setSpecialty(dto.specialty());
                teacherRepo.save(t);
            }
            case COORDINATOR -> {
                Coordinator c = new Coordinator();
                c.setUser(user);
                c.setDepartment(dto.department());
                coordinatorRepo.save(c);
            }
            case PARENT -> {
                Parent p = new Parent();
                p.setUser(user);
                p.setPhoneContact(dto.phoneContact());
                parentRepo.save(p);

                // Vinculación opcional con un estudiante (por email del estudiante)
                if (dto.studentEmail() != null && !dto.studentEmail().isBlank()) {
                    User studentUser = userRepo.findByEmail(dto.studentEmail().trim())
                            .orElseThrow(() -> new BusinessRuleException("No se encontró un estudiante con ese correo"));
                    Student student = studentRepo.findByUser_Id(studentUser.getId())
                            .orElseThrow(() -> new BusinessRuleException("El usuario indicado no es un estudiante"));
                    if (studentParentRepo.existsByStudent_IdAndParent_Id(student.getId(), p.getId())) {
                        throw new BusinessRuleException("El familiar ya se encuentra registrado para este estudiante");
                    }
                    StudentParent link = new StudentParent();
                    link.setStudent(student);
                    link.setParent(p);
                    link.setRelationship("Familiar");
                    studentParentRepo.save(link);
                }
            }
        }

        String token = jwt.generateToken(new UserPrincipal(user));
        return new AuthResponseDTO(token, "Bearer", userMapper.toResponse(user), profileIdOf(user));
    }

    @Transactional
    public void changePassword(UserPrincipal principal, ChangePasswordRequestDTO dto) {
        User user = userRepo.findById(principal.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (!encoder.matches(dto.currentPassword(), user.getPassword())) {
            throw new BusinessRuleException("La contraseña actual es incorrecta");
        }

        user.setPassword(encoder.encode(dto.newPassword()));
        userRepo.save(user);
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        String token = jwt.generateToken(principal);
        return new AuthResponseDTO(token, "Bearer", userMapper.toResponse(principal.getUser()), profileIdOf(principal.getUser()));
    }

    private Long profileIdOf(User user) {
        return switch (user.getRole()) {
            case STUDENT -> studentRepo.findByUser_Id(user.getId()).map(Student::getId).orElse(null);
            case TEACHER -> teacherRepo.findByUser_Id(user.getId()).map(Teacher::getId).orElse(null);
            case COORDINATOR -> coordinatorRepo.findByUser_Id(user.getId()).map(Coordinator::getId).orElse(null);
            case PARENT -> parentRepo.findByUser_Id(user.getId()).map(Parent::getId).orElse(null);
        };
    }
}