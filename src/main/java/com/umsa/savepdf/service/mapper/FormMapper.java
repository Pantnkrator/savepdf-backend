package com.umsa.savepdf.service.mapper;

import com.umsa.savepdf.domain.*;
import com.umsa.savepdf.service.dto.FormDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Form} and its DTO {@link FormDTO}.
 */
@Mapper(componentModel = "spring", uses = {FileMapper.class})
public interface FormMapper extends EntityMapper<FormDTO, Form> {

    @Mapping(source = "file.id", target = "fileId")
    FormDTO toDto(Form form);

    @Mapping(source = "fileId", target = "file")
    Form toEntity(FormDTO formDTO);

    default Form fromId(Long id) {
        if (id == null) {
            return null;
        }
        Form form = new Form();
        form.setId(id);
        return form;
    }
}
