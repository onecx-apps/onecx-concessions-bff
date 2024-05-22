package org.tkit.onecx.concessions.bff.rs.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import gen.org.tkit.onecx.concessions.bff.rs.internal.model.CreateTravelOfferingDTO;
import gen.org.tkit.onecx.concessions.bff.rs.internal.model.TravelOfferingDTO;
import gen.org.tkit.onecx.concessions.bff.rs.internal.model.TravelOfferingPageResultDTO;
import gen.org.tkit.onecx.concessions.bff.rs.internal.model.TravelOfferingSearchCriteriaDTO;
import gen.org.tkit.onecx.concessions.bff.rs.internal.model.UpdateTravelOfferingDTO;
import gen.org.tkit.onecx.concessions.tool.client.model.CreateTravelOffering;
import gen.org.tkit.onecx.concessions.tool.client.model.TravelOffering;
import gen.org.tkit.onecx.concessions.tool.client.model.TravelOfferingPageResult;
import gen.org.tkit.onecx.concessions.tool.client.model.TravelOfferingSearchCriteria;
import gen.org.tkit.onecx.concessions.tool.client.model.UpdateTravelOffering;

@Mapper
public interface TravelOfferingMapper {

    TravelOfferingSearchCriteria map(TravelOfferingSearchCriteriaDTO dto);

    @Mapping(target = "removeStreamItem", ignore = true)
    TravelOfferingPageResultDTO mapPage(TravelOfferingPageResult page);

    CreateTravelOffering create(CreateTravelOfferingDTO object);

    TravelOfferingDTO map(TravelOffering travelOffering);

    TravelOfferingDTO[] map(TravelOffering[] travelOffering);

    UpdateTravelOffering mapUpdateTravelOffering(UpdateTravelOfferingDTO updateTravelOfferingDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "creationUser", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "modificationUser", ignore = true)
    @Mapping(target = "modificationCount", ignore = true)
    void update(UpdateTravelOfferingDTO travelOfferingDTO, @MappingTarget TravelOffering entity);
}
