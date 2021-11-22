package com.kanyelings.telmah.mentormatchsb.business.mapper;

import com.kanyelings.telmah.mentormatchsb.api.dto.MentorDto;
import com.kanyelings.telmah.mentormatchsb.data.entity.MentorEntity;
import lombok.SneakyThrows;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Mapper(componentModel = "spring", implementationPackage = "<PACKAGE_NAME>.impl")
public interface MentorMapper {
    MentorDto mapMentorEntityToDto(MentorEntity mentorEntity);

    @InheritInverseConfiguration
    @Mapping(target = "mentorId", ignore = true)
    MentorEntity mapDtoToMentorEntity(MentorDto mentorDto);
}
