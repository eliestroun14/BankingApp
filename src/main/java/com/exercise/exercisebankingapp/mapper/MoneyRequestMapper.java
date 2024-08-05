package com.exercise.exercisebankingapp.mapper;

import com.exercise.exercisebankingapp.dataTransferObject.MoneyRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MoneyRequestMapper {

    MoneyRequestMapper INSTANCE = Mappers.getMapper(MoneyRequestMapper.class);

    MoneyRequest moneyRequestToMoneyRequestDTO(MoneyRequest moneyRequest);

    MoneyRequest moneyRequestDTOToMoneyRequest(MoneyRequest moneyRequestDTO);

    void updateMoneyRequestFromDTO(MoneyRequest moneyRequestDTO, @MappingTarget MoneyRequest moneyRequest);

    @AfterMapping
    default void handleNulls(MoneyRequest moneyRequestDTO, @MappingTarget MoneyRequest moneyRequest) {
        if (moneyRequestDTO.getMoney() == 0) {
            moneyRequest.setMoney(moneyRequest.getMoney());
        }
    }
}
