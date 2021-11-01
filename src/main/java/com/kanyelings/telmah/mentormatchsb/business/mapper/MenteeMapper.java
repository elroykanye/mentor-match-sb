package com.kanyelings.telmah.mentormatchsb.business.mapper;

import com.kanyelings.telmah.mentormatchsb.api.dto.MenteeDto;
import com.kanyelings.telmah.mentormatchsb.data.entity.MenteeEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Mapper(componentModel = "spring", implementationPackage = "<PACKAGE_NAME>.impl")
public interface MenteeMapper {
    @Mapping(target = "image", expression = "java(mapBytesArrayToImageFile(menteeEntity.getImage()))")
    MenteeDto mapMenteeEntityToDto(MenteeEntity menteeEntity);

    @InheritInverseConfiguration
    @Mapping(target = "menteeId", ignore = true)
    @Mapping(target = "image", ignore = true)
    MenteeEntity mapDtoToMentorEntity(MenteeDto menteeDto);

}
