package com.exercise.exercisebankingapp.mapper;

import com.exercise.exercisebankingapp.dataTransferObject.UserUpdateDTO;
import com.exercise.exercisebankingapp.entity.MyUser;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserUpdateDTO userToUserUpdateDTO(MyUser myUser);

    MyUser userUpdateDTOToUser(UserUpdateDTO userUpdateDTO);

    void updateUserFromDTO(UserUpdateDTO userUpdateDTO, @MappingTarget MyUser myUser);

    @AfterMapping
    default void handleNulls(UserUpdateDTO userUpdateDTO, @MappingTarget MyUser myUser) {
        if (userUpdateDTO.getName() == null) {
            myUser.setName(myUser.getName());
        }
        if (userUpdateDTO.getEmail() == null) {
            myUser.setEmail(myUser.getEmail());
        }
        if (userUpdateDTO.getAddress() == null) {
            myUser.setAddress(myUser.getAddress());
        }
        if (userUpdateDTO.getPhoneNumber() == null) {
            myUser.setPhoneNumber(myUser.getPhoneNumber());
        }
        if (userUpdateDTO.getDateOfBirth() == null) {
            myUser.setDateOfBirth(myUser.getDateOfBirth());
        }
    }
}
