package com.exercise.exercisebankingapp.mapper;

import com.exercise.exercisebankingapp.dataTransferObject.UserUpdateDTO;
import com.exercise.exercisebankingapp.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserUpdateDTO userToUserUpdateDTO(User user);

    User userUpdateDTOToUser(UserUpdateDTO userUpdateDTO);

    void updateUserFromDTO(UserUpdateDTO userUpdateDTO, @MappingTarget User user);

    @AfterMapping
    default void handleNulls(UserUpdateDTO userUpdateDTO, @MappingTarget User user) {
        if (userUpdateDTO.getName() == null) {
            user.setName(user.getName());
        }
        if (userUpdateDTO.getEmail() == null) {
            user.setEmail(user.getEmail());
        }
        if (userUpdateDTO.getAddress() == null) {
            user.setAddress(user.getAddress());
        }
        if (userUpdateDTO.getPhoneNumber() == null) {
            user.setPhoneNumber(user.getPhoneNumber());
        }
        if (userUpdateDTO.getDateOfBirth() == null) {
            user.setDateOfBirth(user.getDateOfBirth());
        }
    }
}
