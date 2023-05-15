package br.com.smartnr.nr13api.infrastructure.storage;

import br.com.smartnr.nr13api.domain.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalFileStorageService implements FileStorageService {

    @Value("${smartnr.storage.local.path}")
    private Path fileDirectory;

    @Override
    public void store(NewFile newFile) {
        try {
            Path filePath = getFilePath(newFile.getFileName());
            FileCopyUtils.copy(newFile.getInputStream(), Files.newOutputStream(filePath));
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar o arquivo", e);
        }
    }

    @Override
    public void remove(String filename) {
        try {
            Path filePath = getFilePath(filename);
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir o arquivo", e);
        }
    }

    @Override
    public InputStream retrieve(String fileName) {
        try {
            Path filePath = getFilePath(fileName);
            return Files.newInputStream(filePath);
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar o arquivo", e);
        }
    }

    private Path getFilePath(String fileName) {
        return fileDirectory.resolve(Path.of(fileName));
    }
}
