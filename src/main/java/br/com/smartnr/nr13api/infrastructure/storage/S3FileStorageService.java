package br.com.smartnr.nr13api.infrastructure.storage;

import br.com.smartnr.nr13api.core.storage.StorageProperties;
import br.com.smartnr.nr13api.domain.service.FileStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class S3FileStorageService implements FileStorageService {

    private final AmazonS3 amazonS3;
    private final StorageProperties storageProperties;

    @Override
    public void store(NewFile newFile) {
        try {
            String filePath = getFilePath(newFile.getFileName());
            var objectMetadata = new ObjectMetadata();

            var putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    filePath,
                    newFile.getInputStream(),
                    objectMetadata
            );

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível enviar arquivo para o armazenamento", e);
        }
    }

    @Override
    public void remove(String filename) throws IOException {
        try {
            amazonS3.deleteObject(storageProperties.getS3().getBucket(), getFilePath(filename));
        } catch (Exception e) {
            throw new StorageException("Não foi possível remover o arquivo do armazenamento", e);
        }
    }

    @Override
    public InputStream retrieve(String fileName) {
        try {
            S3Object file = amazonS3.getObject(storageProperties.getS3().getBucket(), getFilePath(fileName));
            return file.getObjectContent();
        } catch (Exception e) {
            throw new StorageException("Não foi possível obter o arquivo do armazenamento", e);
        }
    }

    private String getFilePath(String fileName) {
        return String.format("%s/%s", storageProperties.getS3().getDirectory(), fileName);
    }

}
