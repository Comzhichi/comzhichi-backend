package com.comzhichi.service;

import com.comzhichi.dto.request.CoordinatorRequestDTO;
import com.comzhichi.dto.response.CoordinatorResponseDTO;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.mapper.CoordinatorMapper;
import com.comzhichi.model.Coordinator;
import com.comzhichi.model.User;
import com.comzhichi.repository.CoordinatorRepository;
import com.comzhichi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoordinatorService {

    private final CoordinatorRepository coordinatorRepository;
    private final UserRepository userRepository;
    private final CoordinatorMapper coordinatorMapper;

    @Transactional(readOnly = true)
    public List<CoordinatorResponseDTO> findAll(String department) {
        List<Coordinator> coordinators = (department == null || department.isBlank())
                ? coordinatorRepository.findAll()
                : coordinatorRepository.findByDepartmentContainingIgnoreCase(department);

        return coordinators.stream()
                .map(coordinatorMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CoordinatorResponseDTO findById(Long id) {
        Coordinator coordinator = coordinatorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coordinador no encontrado con ID: " + id));
        return coordinatorMapper.toResponse(coordinator);
    }

    @Transactional
    public CoordinatorResponseDTO create(CoordinatorRequestDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + dto.userId()));

        Coordinator coordinator = coordinatorMapper.toEntity(dto);
        coordinator.setUser(user);

        return coordinatorMapper.toResponse(coordinatorRepository.save(coordinator));
    }

    @Transactional
    public CoordinatorResponseDTO update(Long id, CoordinatorRequestDTO dto) {
        Coordinator coordinator = coordinatorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coordinador no encontrado con ID: " + id));

        coordinator.setDepartment(dto.department());
        return coordinatorMapper.toResponse(coordinatorRepository.save(coordinator));
    }

    @Transactional
    public void delete(Long id) {
        Coordinator coordinator = coordinatorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coordinador no encontrado con ID: " + id));
        coordinatorRepository.delete(coordinator);
    }
}