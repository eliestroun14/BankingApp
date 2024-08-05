package com.exercise.exercisebankingapp.mapper;

import com.exercise.exercisebankingapp.dataTransferObject.MoneyRequest;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-05T11:59:25+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
public class MoneyRequestMapperImpl implements MoneyRequestMapper {

    @Override
    public MoneyRequest moneyRequestToMoneyRequestDTO(MoneyRequest moneyRequest) {
        if ( moneyRequest == null ) {
            return null;
        }

        MoneyRequest moneyRequest1 = new MoneyRequest();

        moneyRequest1.setMoney( moneyRequest.getMoney() );

        handleNulls( moneyRequest, moneyRequest1 );

        return moneyRequest1;
    }

    @Override
    public MoneyRequest moneyRequestDTOToMoneyRequest(MoneyRequest moneyRequestDTO) {
        if ( moneyRequestDTO == null ) {
            return null;
        }

        MoneyRequest moneyRequest = new MoneyRequest();

        moneyRequest.setMoney( moneyRequestDTO.getMoney() );

        handleNulls( moneyRequestDTO, moneyRequest );

        return moneyRequest;
    }

    @Override
    public void updateMoneyRequestFromDTO(MoneyRequest moneyRequestDTO, MoneyRequest moneyRequest) {
        if ( moneyRequestDTO == null ) {
            return;
        }

        moneyRequest.setMoney( moneyRequestDTO.getMoney() );

        handleNulls( moneyRequestDTO, moneyRequest );
    }
}
