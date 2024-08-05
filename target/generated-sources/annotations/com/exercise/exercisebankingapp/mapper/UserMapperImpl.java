package com.exercise.exercisebankingapp.mapper;

import com.exercise.exercisebankingapp.dataTransferObject.UserUpdateDTO;
import com.exercise.exercisebankingapp.entity.User;
import java.time.LocalDate;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-05T12:07:20+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserUpdateDTO userToUserUpdateDTO(User user) {
        if ( user == null ) {
            return null;
        }

        String name = null;
        String email = null;
        LocalDate dateOfBirth = null;
        String address = null;
        String phoneNumber = null;

        UserUpdateDTO userUpdateDTO = new UserUpdateDTO( name, email, dateOfBirth, address, phoneNumber );

        return userUpdateDTO;
    }

    @Override
    public User userUpdateDTOToUser(UserUpdateDTO userUpdateDTO) {
        if ( userUpdateDTO == null ) {
            return null;
        }

        User user = new User();

        handleNulls( userUpdateDTO, user );

        return user;
    }

    @Override
    public void updateUserFromDTO(UserUpdateDTO userUpdateDTO, User user) {
        if ( userUpdateDTO == null ) {
            return;
        }

        handleNulls( userUpdateDTO, user );
    }
}
