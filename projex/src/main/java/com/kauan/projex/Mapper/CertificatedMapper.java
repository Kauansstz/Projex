package com.kauan.projex.Mapper;

import org.mapstruct.*;

import com.kauan.projex.dto.CertificatedRequest;
import com.kauan.projex.model.Certificated;

@Mapper(componentModel = "spring")
public interface CertificatedMapper {
    Certificated toEntity(CertificatedRequest dto);
    void updateEntityFromDto(CertificatedRequest dto, @MappingTarget Certificated entity);
}
