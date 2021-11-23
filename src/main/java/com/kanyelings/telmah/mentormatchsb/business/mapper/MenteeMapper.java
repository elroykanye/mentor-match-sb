package com.kanyelings.telmah.mentormatchsb.business.mapper;

import com.kanyelings.telmah.mentormatchsb.api.dto.MenteeDto;
import com.kanyelings.telmah.mentormatchsb.data.entity.MenteeEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", implementationPackage = "<PACKAGE_NAME>.impl")
public interface MenteeMapper {
    MenteeDto mapMenteeEntityToDto(MenteeEntity menteeEntity);

    @InheritInverseConfiguration
    @Mapping(target = "menteeId", ignore = true)
    MenteeEntity mapDtoToMentorEntity(MenteeDto menteeDto);
}
