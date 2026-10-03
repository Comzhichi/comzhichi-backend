package com.comzhichi.service;

import com.comzhichi.dto.request.ParentRequestDTO;
import com.comzhichi.dto.response.ParentResponseDTO;
import com.comzhichi.exception.BusinessRuleException;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.mapper.ParentMapper;
import com.comzhichi.model.Parent;
import com.comzhichi.model.Role;
import com.comzhichi.model.User;
import com.comzhichi.repository.ParentRepository;
import com.comzhichi.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentService {

    private final ParentRepository parentRepository;
    private final UserRepository userRepository;
    private final ParentMapper parentMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<ParentResponseDTO> findAll() {
        return parentRepository.findAll().stream()
                .map(parentMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ParentResponseDTO findById(Long id) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Apoderado no encontrado con ID: " + id));
        return parentMapper.toResponse(parent);
    }

    @Transactional
    public ParentResponseDTO create(ParentRequestDTO dto) {
        if (parentRepository.existsById(dto.userId())) {
            throw new BusinessRuleException("El apoderado ya se encuentra registrado con ID: " + dto.userId());
        }

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + dto.userId()));

        if (user.getRole() != Role.PARENT) {
            throw new BusinessRuleException("El usuario debe tener el rol PARENT para crear su perfil de apoderado");
        }

        parentRepository.insertParent(dto.userId(), dto.phoneContact());
        entityManager.flush();
        entityManager.clear();

        Parent parent = parentRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Error al recuperar el apoderado creado con ID: " + dto.userId()));

        return parentMapper.toResponse(parent);
    }

    @Transactional
    public ParentResponseDTO update(Long id, ParentRequestDTO dto) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Apoderado no encontrado con ID: " + id));

        parent.setPhoneContact(dto.phoneContact());
        return parentMapper.toResponse(parentRepository.save(parent));
    }

    @Transactional
    public void delete(Long id) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Apoderado no encontrado con ID: " + id));
        parentRepository.delete(parent);
    }
}
