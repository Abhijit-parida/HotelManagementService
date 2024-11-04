package com.hsm.service;

import com.hsm.entity.State;
import com.hsm.payload.StateDto;
import com.hsm.repository.StateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StateService {

    private final StateRepository stateRepository;
    private final ModelMapper modelMapper;

    // -------------------- Constructor --------------------- //

    public StateService(StateRepository stateRepository, ModelMapper modelMapper) {
        this.stateRepository = stateRepository;
        this.modelMapper = modelMapper;
    }

    // ---------------------- Mapping ----------------------- //

    State mapToEntity(StateDto stateDto) {
        return modelMapper.map(stateDto, State.class);
    }

    StateDto mapToDto(State state) {
        return modelMapper.map(state, StateDto.class);
    }

    // ----------------------- Create ----------------------- //

    public boolean verifyState(StateDto stateDto) {
        return stateRepository.findByStateName(stateDto.getStateName()).isPresent();
    }

    public StateDto addStateName(StateDto stateDto) {
        return mapToDto(stateRepository.save(mapToEntity(stateDto)));
    }

    // ------------------------ Read ------------------------ //

    public List<StateDto> getStateName() {
        return stateRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    // ----------------------- Update ----------------------- //

    public boolean verifyStateId(Long id) {
        return stateRepository.findById(id).isPresent();
    }

    public StateDto updateStateId(Long id, String updateState) {
        State state = stateRepository.findById(id).get();
        state.setStateName(updateState);
        return mapToDto(stateRepository.save(state));
    }

    public boolean verifyStateName(String stateName) {
        return stateRepository.findByStateName(stateName).isPresent();
    }

    public StateDto updateStateName(String stateName, String updateState) {
        State state = stateRepository.findByStateName(stateName).get();
        state.setStateName(updateState);
        return mapToDto(stateRepository.save(state));
    }

    // ----------------------- Delete ----------------------- //

    public void deleteStateById(Long id) {
        if (stateRepository.findById(id).isPresent()) {
            stateRepository.deleteById(id);
        } else {
            throw new RuntimeException("State with ID " + id + " does not exist.");
        }
    }

    public void deleteStateByName(String stateName) {
        if (stateRepository.findByStateName(stateName).isPresent()) {
            stateRepository.deleteById(stateRepository.findByStateName(stateName).get().getId());
        } else {
            throw new RuntimeException("State with name ( " + stateName + " ) does not exist.");
        }
    }
}
