package br.com.smartnr.nr13api.core.modelmapper;

import br.com.smartnr.nr13api.api.dto.request.InspectionCreationRequest;
import br.com.smartnr.nr13api.api.dto.response.*;
import br.com.smartnr.nr13api.domain.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setSkipNullEnabled(true);

        applicableTestsMappingConfig(modelMapper);
        inspectionSummaryResponseConfig(modelMapper);
        inspectionCreationConfig(modelMapper);
        pendencyReferenceResponseConfig(modelMapper);
        equipmentSituationResponseConfig(modelMapper);

        return modelMapper;
    }

    private void applicableTestsMappingConfig(ModelMapper modelMapper) {
        modelMapper.typeMap(ApplicableTest.class, ApplicableTestResponse.class)
                .addMapping(at -> (at.getId().getTest().getName()), ApplicableTestResponse::setName)
                .addMapping(at -> (at.getId().getTest().getId()), ApplicableTestResponse::setTestId);
    }

    private void inspectionSummaryResponseConfig(ModelMapper modelMapper) {
        modelMapper.typeMap(Inspection.class, InspectionSummaryResponse.class)
                .addMapping(i -> (i.getApplicableTest().getId().getEquipment()), (dest, v) -> dest.setEquipment((EquipmentSummaryResponse) v))
                .addMapping(i -> (i.getApplicableTest().getId().getTest()), InspectionSummaryResponse::setTest);
    }

    private void inspectionCreationConfig(ModelMapper modelMapper) {
        modelMapper.typeMap(InspectionCreationRequest.class, Inspection.class)
                .addMapping(InspectionCreationRequest::getEquipment, (dest, v) -> dest.getApplicableTest().getId().setEquipment((Equipment) v))
                .addMapping(InspectionCreationRequest::getTest, (dest, v) -> dest.getApplicableTest().getId().setTest((Test) v));
    }

    private void pendencyReferenceResponseConfig(ModelMapper modelMapper) {
        modelMapper.typeMap(Pendency.class, PendencyDetailResponse.class)
                .addMapping(p -> (p.getInspection().getApplicableTest().getId().getEquipment()), (dest, v) -> dest.getInspection().getApplicableTest().setEquipment((EquipmentSummaryResponse) v))
                .addMapping(p -> (p.getInspection().getApplicableTest().getId().getTest()), (dest, v) -> dest.getInspection().getApplicableTest().setTest((TestSummaryResponse) v));
    }

    private void equipmentSituationResponseConfig(ModelMapper modelMapper) {
        modelMapper.typeMap(Equipment.class, EquipmentSituationResponse.class)
                .addMapping(Equipment::getTag, EquipmentSituationResponse::setTag)
                .addMapping(e -> e.getArea().getPlant().getCode(), EquipmentSituationResponse::setPlant);
    }
}
