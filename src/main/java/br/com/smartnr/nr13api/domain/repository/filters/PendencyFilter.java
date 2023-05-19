package br.com.smartnr.nr13api.domain.repository.filters;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PendencyFilter {

    private Long id;
    private String authorName;
    private String responsibleName;
    private LocalDate openedAt;
    private LocalDate deadLine;
    private String plantCode;

}
