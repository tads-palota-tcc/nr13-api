package br.com.smartnr.nr13api.core.storage;

import com.amazonaws.regions.Regions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@ConfigurationProperties("smartnr.storage")
@Getter
@Setter
public class StorageProperties {

    private Local local = new Local();
    private S3 s3 = new S3();

    @Getter
    @Setter
    public class Local {
        private Path path;
    }

    @Getter
    @Setter
    public class S3 {
        private String accessKeyId;
        private String accessKeySecret;
        private String bucket;
        private Regions region;
        private String directory;
    }
}
