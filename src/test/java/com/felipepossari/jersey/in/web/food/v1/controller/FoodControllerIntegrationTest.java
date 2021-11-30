package com.felipepossari.jersey.in.web.food.v1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipepossari.jersey.adapter.in.web.food.v1.FoodController;
import com.felipepossari.jersey.adapter.in.web.food.v1.exception.FoodApiErrorReason;
import com.felipepossari.jersey.adapter.in.web.food.v1.exception.model.ErrorResponse;
import com.felipepossari.jersey.adapter.in.web.food.v1.request.FoodRequest;
import com.felipepossari.jersey.adapter.in.web.food.v1.response.FoodResponse;
import com.felipepossari.jersey.application.domain.FoodType;
import com.felipepossari.jersey.base.request.FoodRequestTestBuilder;
import com.felipepossari.jersey.configuration.AutoScanFeatureConfig;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.felipepossari.jersey.Main.BASE_PACKAGE;
import static com.felipepossari.jersey.adapter.AdapterConstans.ENDPOINT_FOOD;
import static com.felipepossari.jersey.adapter.AdapterConstans.QUERY_PARAM_FOOD_TYPE;
import static com.felipepossari.jersey.adapter.in.web.food.v1.exception.FoodApiErrorReason.*;
import static com.felipepossari.jersey.application.domain.FoodType.FRUITS;
import static com.felipepossari.jersey.base.DefaultConstants.*;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.junit.jupiter.api.Assertions.*;

class FoodControllerIntegrationTest extends JerseyTest {

    public static final String HEADER_LOCATION = "Location";
    public static final String SLASH = "/";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final List<FoodRequest> foodRequests = Arrays.asList(
            FoodRequestTestBuilder.aFoodRequest().name("Apple").type(FRUITS.name()).build(),
            FoodRequestTestBuilder.aFoodRequest().name("Grape").type(FRUITS.name()).build(),
            FoodRequestTestBuilder.aFoodRequest().name("Ham").type(FoodType.MEAT.name()).build(),
            FoodRequestTestBuilder.aFoodRequest().name("Fish").type(FoodType.FISH.name()).build()
    );

    @Override
    protected Application configure() {
        ResourceConfig rc = new ResourceConfig(FoodController.class);
        rc.packages(BASE_PACKAGE);
        rc.register(AutoScanFeatureConfig.class);
        rc.register(LoggingFeature.class);
        return rc;
    }

    void createFoodList() {
        for (FoodRequest request : foodRequests) {
            Response response = target(ENDPOINT_FOOD).request(APPLICATION_JSON)
                    .post(Entity.entity(request, APPLICATION_JSON));
            if (Response.Status.CREATED.getStatusCode() != response.getStatus()) {
                throw new BadRequestException("Post test request failed.");
            }
        }
    }

    String createSingleFood() {
        FoodRequest request = FoodRequestTestBuilder.aFoodRequest().build();
        Response response = target(ENDPOINT_FOOD).request(APPLICATION_JSON)
                .post(Entity.entity(request, APPLICATION_JSON));
        if (Response.Status.CREATED.getStatusCode() != response.getStatus()) {
            throw new BadRequestException("Post test request failed.");
        } else {
            return extractIdFromLocationHeader(response);
        }
    }

    void deleteEntitiesCreated() throws JsonProcessingException {
        Response response = target(ENDPOINT_FOOD)
                .request(APPLICATION_JSON)
                .get();

        List<FoodResponse> foods = parseFoodsResponse(response.readEntity(String.class));

        for (FoodResponse request : foods) {
            Response responseDelete = target(ENDPOINT_FOOD)
                    .path(request.getId())
                    .request(APPLICATION_JSON)
                    .delete();
            if (Response.Status.NO_CONTENT.getStatusCode() != responseDelete.getStatus()) {
                throw new BadRequestException("Delete test request failed.");
            }
        }
    }

    @Test
    void postFoodShouldCreateFoodIfRequestIsValid() {
        FoodRequest request = FoodRequestTestBuilder.aFoodRequest().build();

        Response response = target(ENDPOINT_FOOD)
                .request(APPLICATION_JSON)
                .post(Entity.entity(request, APPLICATION_JSON));

        String id = extractIdFromLocationHeader(response);

        FoodResponse foodResponse = target(ENDPOINT_FOOD)
                .path(id)
                .request().get(FoodResponse.class);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(request.getName(), foodResponse.getName());
        assertEquals(request.getType(), foodResponse.getType());
        assertEquals(request.getWeight(), foodResponse.getWeight());
    }

    @ParameterizedTest
    @MethodSource("invalidFoodRequests")
    void postFoodShouldNotCreateFoodIfRequestIsInvalid(FoodRequest request,
                                                       FoodApiErrorReason errorReason) throws JsonProcessingException {
        Response response = target(ENDPOINT_FOOD)
                .request(APPLICATION_JSON)
                .post(Entity.entity(request, APPLICATION_JSON));

        List<ErrorResponse> errors = parseErrors(response.readEntity(String.class));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertFalse(errors.isEmpty());
        assertEquals(errorReason.getCode(), errors.get(0).getCode());
    }

    @Test
    void getAllShouldReturnAListOfFoodsUnfilteredIfNoTypeIsPassed() throws JsonProcessingException {
        createFoodList();

        Response response = target(ENDPOINT_FOOD)
                .request(APPLICATION_JSON)
                .get();

        List<FoodResponse> foods = parseFoodsResponse(response.readEntity(String.class));
        assertEquals(foodRequests.size(), foods.size());

        deleteEntitiesCreated();
    }

    @Test
    void getAllShouldReturnAListOfFoodsUnfilteredIfInvalidTypeIsPassed() throws JsonProcessingException {
        createFoodList();

        Response response = target(ENDPOINT_FOOD)
                .queryParam(QUERY_PARAM_FOOD_TYPE, INVALID_FOOD_TYPE)
                .request(APPLICATION_JSON)
                .get();

        List<FoodResponse> foods = parseFoodsResponse(response.readEntity(String.class));
        assertEquals(foodRequests.size(), foods.size());

        deleteEntitiesCreated();
    }

    @Test
    void getAllShouldReturnAListOfFruitTypeFoodsIfNoTypeIsPassed() throws JsonProcessingException {
        createFoodList();

        Response response = target(ENDPOINT_FOOD)
                .queryParam(QUERY_PARAM_FOOD_TYPE, FRUITS.name())
                .request(APPLICATION_JSON)
                .get();

        List<FoodResponse> foods = parseFoodsResponse(response.readEntity(String.class));
        assertEquals(foodRequests.stream().filter(f -> FRUITS.name().equalsIgnoreCase(f.getType())).count(),
                foods.size());

        deleteEntitiesCreated();
    }

    @Test
    void getByIdShouldReturnFoodIfItExists() throws JsonProcessingException {
        String id = createSingleFood();

        Response response = target(ENDPOINT_FOOD)
                .path(id)
                .request(APPLICATION_JSON)
                .get();

        FoodResponse foodResponse = response.readEntity(FoodResponse.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(foodResponse);
        assertEquals(id, foodResponse.getId());

        deleteEntitiesCreated();
    }

    @Test
    void getByIdShouldReturnNotFoundIfFoodWasNotFound() {
        Response response = target(ENDPOINT_FOOD)
                .path(FOOD_ID)
                .request(APPLICATION_JSON)
                .get();

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void deleteShouldDeleteFoodIfItIsExists() {
        String id = createSingleFood();

        Response response = target(ENDPOINT_FOOD)
                .path(id)
                .request(APPLICATION_JSON)
                .delete();

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    void deleteShouldReturnNotFoundIfFoodWasNotFound() {
        Response response = target(ENDPOINT_FOOD)
                .path(FOOD_ID)
                .request(APPLICATION_JSON)
                .delete();

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void updateShouldUpdateFoodIfRequestIsValid() {
        String id = createSingleFood();

        FoodRequest foodUpdatedRequest = FoodRequestTestBuilder.aFoodRequest()
                .name("Beef")
                .weight(5.0)
                .type(FoodType.MEAT.name())
                .build();

        Response response = target(ENDPOINT_FOOD)
                .path(id)
                .request(APPLICATION_JSON)
                .put(Entity.entity(foodUpdatedRequest, APPLICATION_JSON));

        FoodResponse updateResponse = target(ENDPOINT_FOOD)
                .path(id)
                .request(APPLICATION_JSON)
                .get(FoodResponse.class);

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        assertEquals(foodUpdatedRequest.getName(), updateResponse.getName());
        assertEquals(foodUpdatedRequest.getWeight(), updateResponse.getWeight());
        assertEquals(foodUpdatedRequest.getType(), updateResponse.getType());
    }

    @ParameterizedTest
    @MethodSource("invalidFoodRequests")
    void putFoodShouldNotUpdateFoodIfRequestIsInvalid(FoodRequest request,
                                                      FoodApiErrorReason errorReason) throws JsonProcessingException {
        String id = createSingleFood();
        Response response = target(ENDPOINT_FOOD)
                .path(id)
                .request(APPLICATION_JSON)
                .put(Entity.entity(request, APPLICATION_JSON));

        List<ErrorResponse> errors = parseErrors(response.readEntity(String.class));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertFalse(errors.isEmpty());
        assertEquals(errorReason.getCode(), errors.get(0).getCode());
    }

    @Test
    void putFoodShouldReturnNotFoundIfFoodWasNotFound() {
        Response response = target(ENDPOINT_FOOD)
                .path(FOOD_ID)
                .request(APPLICATION_JSON)
                .put(Entity.entity(FoodRequestTestBuilder.aFoodRequest().build(), APPLICATION_JSON));

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    private String extractIdFromLocationHeader(Response response) {
        String[] location = response.getHeaderString(HEADER_LOCATION).split(SLASH);
        return location[location.length - 1];
    }

    private static Stream<Arguments> invalidFoodRequests() {
        return Stream.of(
                Arguments.arguments(FoodRequestTestBuilder.aFoodRequest().name("").build(), FOOD_NAME_EMPTY),
                Arguments.arguments(FoodRequestTestBuilder.aFoodRequest().name(null).build(), FOOD_NAME_EMPTY),
                Arguments.arguments(FoodRequestTestBuilder.aFoodRequest()
                        .name(INVALID_FOOD_NAME).build(), FOOD_NAME_INVALID_LENGTH),
                Arguments.arguments(FoodRequestTestBuilder.aFoodRequest().weight(0).build(), FOOD_WEIGHT_INVALID),
                Arguments.arguments(FoodRequestTestBuilder.aFoodRequest().weight(-1).build(), FOOD_WEIGHT_INVALID),
                Arguments.arguments(FoodRequestTestBuilder.aFoodRequest()
                        .type(INVALID_FOOD_TYPE).build(), FOOD_TYPE_INVALID),
                Arguments.arguments(FoodRequestTestBuilder.aFoodRequest().type(null).build(), FOOD_TYPE_INVALID),
                Arguments.arguments(FoodRequestTestBuilder.aFoodRequest().type("").build(), FOOD_TYPE_INVALID)
        );
    }

    private List<FoodResponse> parseFoodsResponse(String foods) throws JsonProcessingException {
        return objectMapper.readValue(foods, new TypeReference<List<FoodResponse>>() {
        });
    }

    private List<ErrorResponse> parseErrors(String errors) throws JsonProcessingException {
        return objectMapper.readValue(errors, new TypeReference<List<ErrorResponse>>() {
        });
    }
}
