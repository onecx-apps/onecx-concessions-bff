package org.tkit.onecx.concessions.bff.rs.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gen.org.tkit.onecx.concessions.bff.rs.internal.model.CreateTravelConcessionDTO;
import gen.org.tkit.onecx.concessions.bff.rs.internal.model.TravelConcessionDTO;
import gen.org.tkit.onecx.concessions.bff.rs.internal.model.TravelConcessionPageResultDTO;
import gen.org.tkit.onecx.concessions.bff.rs.internal.model.TravelConcessionSearchCriteriaDTO;
import gen.org.tkit.onecx.concessions.bff.rs.internal.model.UpdateTravelConcessionDTO;
import gen.org.tkit.onecx.concessions.tool.client.model.CreateTravelConcession;
import gen.org.tkit.onecx.concessions.tool.client.model.TravelConcession;
import gen.org.tkit.onecx.concessions.tool.client.model.TravelConcessionPageResult;
import gen.org.tkit.onecx.concessions.tool.client.model.TravelConcessionSearchCriteria;
import gen.org.tkit.onecx.concessions.tool.client.model.UpdateTravelConcession;

@Mapper
public interface TravelConcessionMapper {

    TravelConcessionSearchCriteria map(TravelConcessionSearchCriteriaDTO dto);

    @Mapping(target = "removeStreamItem", ignore = true)
    TravelConcessionPageResultDTO mapPage(TravelConcessionPageResult page);

    CreateTravelConcession create(CreateTravelConcessionDTO object);

    TravelConcessionDTO map(TravelConcession TravelConcession);

    TravelConcessionDTO[] map(TravelConcession[] TravelConcession);

    UpdateTravelConcession mapUpdateTravelConcession(UpdateTravelConcessionDTO updateTravelConcessionDTO);

}
