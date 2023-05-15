package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
