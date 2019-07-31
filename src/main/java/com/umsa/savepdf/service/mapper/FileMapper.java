package com.umsa.savepdf.service.mapper;

import com.umsa.savepdf.domain.*;
import com.umsa.savepdf.service.dto.FileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link File} and its DTO {@link FileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FileMapper extends EntityMapper<FileDTO, File> {



    default File fromId(Long id) {
        if (id == null) {
            return null;
        }
        File file = new File();
        file.setId(id);
        return file;
    }
}
