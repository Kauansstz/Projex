package com.kauan.projex.Mapper;

import org.mapstruct.*;
import org.springframework.web.multipart.MultipartFile;

import com.kauan.projex.dto.CertificatedRequest;
import com.kauan.projex.model.Certificated;

@Mapper(componentModel = "spring")
public interface CertificatedMapper {
    Certificated toEntity(CertificatedRequest dto);

    @Mapping(target = "anexo", ignore = true)
    void updateEntityFromDto(CertificatedRequest dto, @MappingTarget Certificated entity);
    default String mapMultipartFileToString(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        return file.getOriginalFilename();
    }
}
