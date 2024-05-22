package org.tkit.onecx.concessions.bff.rs.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.tkit.onecx.concessions.bff.rs.mappers.ExceptionMapper;
import org.tkit.onecx.concessions.bff.rs.mappers.ProblemDetailMapper;
import org.tkit.onecx.concessions.bff.rs.mappers.TravelConcessionMapper;
import org.tkit.quarkus.log.cdi.LogService;

import gen.org.tkit.onecx.concessions.bff.rs.internal.TravelConcessionsInternalApiService;
import gen.org.tkit.onecx.concessions.bff.rs.internal.model.CreateTravelConcessionDTO;
import gen.org.tkit.onecx.concessions.bff.rs.internal.model.TravelConcessionDTO;
import gen.org.tkit.onecx.concessions.bff.rs.internal.model.TravelConcessionPageResultDTO;
import gen.org.tkit.onecx.concessions.bff.rs.internal.model.TravelConcessionSearchCriteriaDTO;
import gen.org.tkit.onecx.concessions.bff.rs.internal.model.UpdateTravelConcessionDTO;
import gen.org.tkit.onecx.concessions.tool.client.api.TravelConcessionsInternalApi;
import gen.org.tkit.onecx.concessions.tool.client.model.ProblemDetailResponse;
import gen.org.tkit.onecx.concessions.tool.client.model.TravelConcession;
import gen.org.tkit.onecx.concessions.tool.client.model.TravelConcessionPageResult;

@LogService
@ApplicationScoped
@Transactional(value = Transactional.TxType.NOT_SUPPORTED)
public class TravelConcessionsRestController implements TravelConcessionsInternalApiService {

    @Inject
    @RestClient
    TravelConcessionsInternalApi client;

    @Inject
    TravelConcessionMapper mapper;

    @Inject
    ExceptionMapper exceptionMapper;

    @Inject
    ProblemDetailMapper problemDetailMapper;

    @Override
    public Response createNewTravelConcession(@Valid @NotNull CreateTravelConcessionDTO createTravelConcessionDTO) {
        try (Response response = client.createNewTravelConcession(mapper.create(createTravelConcessionDTO))) {
            TravelConcession data = response.readEntity(TravelConcession.class);
            TravelConcessionDTO dataDTO = mapper.map(data);
            return Response.status(response.getStatus()).entity(dataDTO).build();
        }
    }

    @Override
    public Response deleteTravelConcession(String id) {
        try (Response response = client.deleteTravelConcession(id)) {
            return Response.status(response.getStatus()).build();
        }
    }

    @Override
    public Response getTravelConcessionById(String id) {
        try (Response response = client.getTravelConcessionById(id)) {
            TravelConcession data = response.readEntity(TravelConcession.class);
            TravelConcessionDTO dataDTO = mapper.map(data);
            return Response.status(response.getStatus()).entity(dataDTO).build();
        }
    }

    @Override
    public Response updateTravelConcession(String id,
            @Valid @NotNull UpdateTravelConcessionDTO updateTravelConcessionDTO) {
        try (Response response = client.updateTravelConcession(id,
                mapper.mapUpdateTravelConcession(updateTravelConcessionDTO))) {
            return Response.status(response.getStatus()).build();
        } catch (WebApplicationException ex) {
            return Response.status(ex.getResponse().getStatus())
                    .entity(problemDetailMapper.map(ex.getResponse().readEntity(ProblemDetailResponse.class))).build();
        }
    }

    @Override
    public Response searchTravelConcession(
            @Valid @NotNull TravelConcessionSearchCriteriaDTO TravelConcessionSearchCriteriaDTO) {
        var searchCriteria = mapper.map(TravelConcessionSearchCriteriaDTO);
        try (Response response = client.searchTravelConcession(searchCriteria)) {
            TravelConcessionPageResult data = response.readEntity(TravelConcessionPageResult.class);
            TravelConcessionPageResultDTO dataDTO = mapper.mapPage(data);
            return Response.status(response.getStatus()).entity(dataDTO).build();
        }
    }
}
