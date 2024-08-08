package com.exercise.exercisebankingapp.mapper;

import com.exercise.exercisebankingapp.dataTransferObject.UserUpdateDTO;
import com.exercise.exercisebankingapp.entity.MyUser;
import java.time.LocalDate;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-08T10:23:18+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserUpdateDTO userToUserUpdateDTO(MyUser myUser) {
        if ( myUser == null ) {
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
    public MyUser userUpdateDTOToUser(UserUpdateDTO userUpdateDTO) {
        if ( userUpdateDTO == null ) {
            return null;
        }

        MyUser myUser = new MyUser();

        handleNulls( userUpdateDTO, myUser );

        return myUser;
    }

    @Override
    public void updateUserFromDTO(UserUpdateDTO userUpdateDTO, MyUser myUser) {
        if ( userUpdateDTO == null ) {
            return;
        }

        handleNulls( userUpdateDTO, myUser );
    }
}
