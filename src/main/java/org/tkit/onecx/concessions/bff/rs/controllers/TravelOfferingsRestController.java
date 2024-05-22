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
import org.tkit.onecx.concessions.bff.rs.mappers.TravelOfferingMapper;
import org.tkit.quarkus.log.cdi.LogService;

import gen.org.tkit.onecx.concessions.bff.rs.internal.TravelOfferingsInternalApiService;
import gen.org.tkit.onecx.concessions.bff.rs.internal.model.CreateTravelOfferingDTO;
import gen.org.tkit.onecx.concessions.bff.rs.internal.model.TravelOfferingDTO;
import gen.org.tkit.onecx.concessions.bff.rs.internal.model.TravelOfferingPageResultDTO;
import gen.org.tkit.onecx.concessions.bff.rs.internal.model.TravelOfferingSearchCriteriaDTO;
import gen.org.tkit.onecx.concessions.bff.rs.internal.model.UpdateTravelOfferingDTO;
import gen.org.tkit.onecx.concessions.tool.client.api.TravelOfferingsInternalApi;
import gen.org.tkit.onecx.concessions.tool.client.model.ProblemDetailResponse;
import gen.org.tkit.onecx.concessions.tool.client.model.TravelOffering;
import gen.org.tkit.onecx.concessions.tool.client.model.TravelOfferingPageResult;

@LogService
@ApplicationScoped
@Transactional(value = Transactional.TxType.NOT_SUPPORTED)
public class TravelOfferingsRestController implements TravelOfferingsInternalApiService {

    @Inject
    @RestClient
    TravelOfferingsInternalApi client;

    @Inject
    TravelOfferingMapper mapper;

    @Inject
    ExceptionMapper exceptionMapper;

    @Inject
    ProblemDetailMapper problemDetailMapper;

    @Override
    public Response createNewTravelOffering(@Valid @NotNull CreateTravelOfferingDTO createTravelOfferingDTO) {
        try (Response response = client.createNewTravelOffering(mapper.create(createTravelOfferingDTO))) {
            TravelOffering data = response.readEntity(TravelOffering.class);
            TravelOfferingDTO dataDTO = mapper.map(data);
            return Response.status(response.getStatus()).entity(dataDTO).build();
        }
    }

    @Override
    public Response deleteTravelOffering(String id) {
        try (Response response = client.deleteTravelOffering(id)) {
            return Response.status(response.getStatus()).build();
        }
    }

    @Override
    public Response getTravelOfferingById(String id) {
        try (Response response = client.getTravelOfferingById(id)) {
            TravelOffering data = response.readEntity(TravelOffering.class);
            TravelOfferingDTO dataDTO = mapper.map(data);
            return Response.status(response.getStatus()).entity(dataDTO).build();
        }
    }

    @Override
    public Response updateTravelOffering(String id, @Valid @NotNull UpdateTravelOfferingDTO updateTravelOfferingDTO) {
        try (Response response = client.updateTravelOffering(id,
                mapper.mapUpdateTravelOffering(updateTravelOfferingDTO))) {
            return Response.status(response.getStatus()).build();
        } catch (WebApplicationException ex) {
            return Response.status(ex.getResponse().getStatus())
                    .entity(problemDetailMapper.map(ex.getResponse().readEntity(ProblemDetailResponse.class))).build();
        }
    }

    @Override
    public Response getAllTravelOfferings() {
        try (Response response = client.getAllTravelOfferings()) {
            TravelOffering[] data = response.readEntity(TravelOffering[].class);
            TravelOfferingDTO[] dataDTO = mapper.map(data);
            return Response.status(response.getStatus()).entity(dataDTO).build();
        }
    }

    @Override
    public Response searchTravelOfferings(
            @Valid @NotNull TravelOfferingSearchCriteriaDTO travelOfferingSearchCriteriaDTO) {
        var searchCriteria = mapper.map(travelOfferingSearchCriteriaDTO);
        try (Response response = client.searchTravelOfferings(searchCriteria)) {
            TravelOfferingPageResult data = response.readEntity(TravelOfferingPageResult.class);
            TravelOfferingPageResultDTO dataDTO = mapper.mapPage(data);
            return Response.status(response.getStatus()).entity(dataDTO).build();
        }
    }
}
