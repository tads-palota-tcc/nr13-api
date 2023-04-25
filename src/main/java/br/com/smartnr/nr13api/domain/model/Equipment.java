package br.com.smartnr.nr13api.domain.model;

import br.com.smartnr.nr13api.domain.exception.BusinessException;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "tb_equipments")
public class Equipment extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;

    private String name;

    private Double hydrostaticTestPressure;

    private Double maxOperationPressure;

    private Double maxAllowedWorkPressure;

    private String manufacturer;

    private String model;

    private String serialNumber;

    private String manufactureYear;

    private String projectCode;

    private String projectCodeEditionYear;

    private Double diameter;

    private Double volume;

    @Enumerated(EnumType.STRING)
    private FluidClass fluidClass;

    @Enumerated(EnumType.STRING)
    private Category category;

    private boolean hasTwoWideExits = false;

    private boolean hasVentilation = false;

    private boolean hasMaintenanceAccess = false;

    private boolean hasLocalLighting = false;

    private boolean hasEmergencyLighting = false;

    private boolean hasIdentificationTag = false;

    private boolean hasCategoryTag = false;

    private boolean hasDatabook = false;

    private boolean hasSafetyJournal = false;

    private boolean hasInstallationProject = false;

    @ManyToOne
    @JoinColumn(name = "databook_file_id")
    private File databookFile;

    @ManyToOne
    @JoinColumn(name = "safety_journal_file_id")
    private File safetyJournalFile;

    @ManyToOne
    @JoinColumn(name = "installation_project_file_id")
    private File installationProjectFile;

    @OneToMany(mappedBy = "equipment")
    private Set<PressureSafetyValve> pressureSafetyValves = new HashSet<>();

    @OneToMany(mappedBy = "equipment")
    private Set<PressureIndicator> pressureIndicators = new HashSet<>();

    public void addPressureSafetyValve(PressureSafetyValve pressureSafetyValve) {
        if (pressureSafetyValve.getEquipment() == null) {
            pressureSafetyValve.setEquipment(this);
            this.pressureSafetyValves.add(pressureSafetyValve);
        } else if (!pressureSafetyValve.getEquipment().equals(this)) {
            throw new BusinessException("Válvula de segurança pertence a outro equipamento");
        } else {
            throw new BusinessException("Válvula de segurança já pertence a este equipamento");
        }
    }

    public void removePressureSafetyValve(PressureSafetyValve pressureSafetyValve) {
        if (!this.pressureSafetyValves.contains(pressureSafetyValve)) {
            throw new BusinessException("Válvula de segurança não pertence a este equipamento");
        }
        this.pressureSafetyValves.remove(pressureSafetyValve);
        pressureSafetyValve.setEquipment(null);
    }

    public void addPressureIndicator(PressureIndicator pressureIndicator) {
        if (pressureIndicator.getEquipment() == null) {
            pressureIndicator.setEquipment(this);
            this.pressureIndicators.add(pressureIndicator);
        } else if (!pressureIndicator.getEquipment().equals(this)) {
            throw new BusinessException("Indicador de pressão pertence a outro equipamento");
        } else {
            throw new BusinessException("Indicador de pressão já pertence a este equipamento");
        }
    }

    public void removePressureIndicator(PressureIndicator pressureIndicator) {
        if (!this.pressureIndicators.contains(pressureIndicator)) {
            throw new BusinessException("Indicador de pressão não pertence a este equipamento");
        }
        this.pressureIndicators.remove(pressureIndicator);
        pressureIndicator.setEquipment(null);
    }

    public List<PressureSafetyValve> getPressureSafetyValves() {
        return List.copyOf(this.pressureSafetyValves);
    }

    public List<PressureIndicator> getPressureIndicators() {
        return List.copyOf(this.pressureIndicators);
    }

    public Category getCategory() {
        if (!ObjectUtils.isEmpty(category)) return category;
        var potentialRiskGroup = getPotentialRiskGroup();
        switch (fluidClass) {
            case A -> {
                switch (potentialRiskGroup) {
                    case GROUP_1, GROUP_2 -> category = Category.I;
                    case GROUP_3 -> category = Category.II;
                    default -> category = Category.III;
                }
            }
            case B -> {
                switch (potentialRiskGroup) {
                    case GROUP_1 -> category = Category.I;
                    case GROUP_2 -> category = Category.II;
                    case GROUP_3 -> category = Category.III;
                    case GROUP_4, GROUP_5 -> category = Category.IV;
                }
            }
            case C -> {
                switch (potentialRiskGroup) {
                    case GROUP_1 -> category = Category.I;
                    case GROUP_2 -> category = Category.II;
                    case GROUP_3 -> category = Category.III;
                    case GROUP_4 -> category = Category.IV;
                    case GROUP_5 -> category = Category.V;
                }
            }
            default -> {
                switch (potentialRiskGroup) {
                    case GROUP_1 -> category = Category.II;
                    case GROUP_2 -> category = Category.III;
                    case GROUP_3 -> category = Category.IV;
                    case GROUP_4, GROUP_5 -> category = Category.V;
                }
            }
        }
        return category;
    }

    public boolean getIsNr13Equipment() {
        if (diameter < 150.0) return false;
        if (FluidClass.A.equals(fluidClass)) return true;
        return (maxOperationPressure * volume) > 8.0;
    }

    private PotentialRiskGroup getPotentialRiskGroup() {
        var pv = (maxOperationPressure * volume) / 1000;
        if (pv >= 100.0) return PotentialRiskGroup.GROUP_1;
        if (pv < 100.0 && pv >= 30.0) return PotentialRiskGroup.GROUP_2;
        if (pv < 30.0 && pv >= 2.5) return PotentialRiskGroup.GROUP_3;
        if (pv < 2.5 && pv >= 1.0) return PotentialRiskGroup.GROUP_4;
        return PotentialRiskGroup.GROUP_5;
    }

}
