package com.csv.parser.mapper;

import com.csv.parser.model.OpportunityDto;
import com.csv.parser.persistence.entity.Opportunity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    componentModel = "spring"
)
public interface CsvDataMapper {

  CsvDataMapper INSTANCE = Mappers.getMapper(CsvDataMapper.class);

  OpportunityDto toDto(Opportunity opportunity);
}
