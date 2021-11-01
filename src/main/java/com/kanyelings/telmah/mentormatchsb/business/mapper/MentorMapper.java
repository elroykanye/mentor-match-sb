package com.kanyelings.telmah.mentormatchsb.business.mapper;

import com.kanyelings.telmah.mentormatchsb.api.dto.MentorDto;
import com.kanyelings.telmah.mentormatchsb.data.entity.MentorEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", implementationPackage = "<PACKAGE_NAME>.impl")
public interface MentorMapper {
    @Mapping(target = "image", ignore = true)
    MentorDto mapMentorEntityToDto(MentorEntity mentorEntity);

    @InheritInverseConfiguration
    @Mapping(target = "mentorId", ignore = true)
    @Mapping(target = "imagePath", ignore = true)
    MentorEntity mapDtoToMentorEntity(MentorDto mentorDto);
}
