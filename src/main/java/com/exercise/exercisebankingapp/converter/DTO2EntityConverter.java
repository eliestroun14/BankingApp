//package com.exercise.exercisebankingapp.converter;
//
//import com.exercise.exercisebankingapp.dataTransferObject.UserUpdateDTO;
//import com.exercise.exercisebankingapp.entity.User;
//
//public class DTO2EntityConverter {
//
//    public static void updateEntityFromDTO(User user, UserUpdateDTO userUpdateDTO) {
//        if (userUpdateDTO.getUserName() != null) {
//            user.setName(userUpdateDTO.getUserName());
//        }
//        if (userUpdateDTO.getUserEmail() != null) {
//            user.setEmail(userUpdateDTO.getUserEmail());
//        }
//        if (userUpdateDTO.getUserPhoneNumber() != null) {
//            user.setPhoneNumber(userUpdateDTO.getUserPhoneNumber());
//        }
//        if (userUpdateDTO.getUserAddress() != null) {
//            user.setAddress(userUpdateDTO.getUserAddress());
//        }
//        if (userUpdateDTO.getUserDateOfBirth() != null) {
//            user.setDateOfBirth(userUpdateDTO.getUserDateOfBirth());
//        }
//    }
//}
