package com.comzhichi.service;

import com.comzhichi.dto.request.ParentRequestDTO;
import com.comzhichi.dto.response.ParentResponseDTO;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.mapper.ParentMapper;
import com.comzhichi.model.Parent;
import com.comzhichi.model.User;
import com.comzhichi.repository.ParentRepository;
import com.comzhichi.repository.UserRepository;
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
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + dto.userId()));

        Parent parent = parentMapper.toEntity(dto);
        parent.setUser(user);

        return parentMapper.toResponse(parentRepository.save(parent));
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