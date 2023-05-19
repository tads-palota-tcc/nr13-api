package br.com.smartnr.nr13api.api.dto.request;

import br.com.smartnr.nr13api.core.validation.FileContentType;
import br.com.smartnr.nr13api.core.validation.FileSize;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class DocumentRequest {

    @NotNull
    @FileSize(max = "2MB")
    @FileContentType(allowed = {MediaType.APPLICATION_PDF_VALUE})
    private MultipartFile file;
}
