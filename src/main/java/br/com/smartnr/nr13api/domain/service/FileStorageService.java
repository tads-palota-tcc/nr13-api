package br.com.smartnr.nr13api.domain.service;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public interface FileStorageService {

    void store(NewFile newFile);

    void remove(String filename) throws IOException;

    InputStream retrieve(String fileName);

    default void replace(String oldFileName, NewFile newFile) throws IOException {
        this.store(newFile);
        if (!ObjectUtils.isEmpty(oldFileName)) {
            this.remove(oldFileName);
        }
    }

    default String generateFileName(String originalFileName) {
        return UUID.randomUUID() + "_" + originalFileName;
    }

    @Builder
    @Getter
    class NewFile {

        private String fileName;
        private InputStream inputStream;
    }
}
