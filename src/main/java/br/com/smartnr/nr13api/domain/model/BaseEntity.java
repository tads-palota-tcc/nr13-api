package br.com.smartnr.nr13api.domain.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseEntity<ID> {

    private Boolean active = Boolean.TRUE;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    public abstract ID getId();

}
