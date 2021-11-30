package com.felipepossari.jersey.adapter.in.web.food.v1;

import com.felipepossari.jersey.adapter.in.web.food.v1.exception.BadRequestException;
import com.felipepossari.jersey.adapter.in.web.food.v1.exception.FoodApiErrorReason;
import com.felipepossari.jersey.adapter.in.web.food.v1.request.FoodRequest;
import com.felipepossari.jersey.application.domain.Food;
import com.felipepossari.jersey.application.port.in.CreateFoodUseCase;
import com.felipepossari.jersey.application.port.in.DeleteFoodUseCase;
import com.felipepossari.jersey.application.port.in.ReadFoodUseCase;
import com.felipepossari.jersey.application.port.in.UpdateFoodUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.logging.Logger;

import static com.felipepossari.jersey.adapter.AdapterConstans.*;

@Path(ENDPOINT_FOOD)
public class FoodController {

    private static final Logger log = Logger.getLogger(FoodController.class.getName());
    private final FoodBuilder builder;
    private final CreateFoodUseCase createFoodUseCase;
    private final ReadFoodUseCase readFoodUseCase;
    private final UpdateFoodUseCase updateFoodUseCase;
    private final DeleteFoodUseCase deleteFoodUseCase;
    private final FoodRequestValidator foodRequestValidator;

    @Inject
    public FoodController(FoodBuilder builder,
                          CreateFoodUseCase createFoodUseCase,
                          ReadFoodUseCase readFoodUseCase,
                          UpdateFoodUseCase updateFoodUseCase,
                          DeleteFoodUseCase deleteFoodUseCase,
                          FoodRequestValidator foodRequestValidator) {
        this.builder = builder;
        this.createFoodUseCase = createFoodUseCase;
        this.readFoodUseCase = readFoodUseCase;
        this.updateFoodUseCase = updateFoodUseCase;
        this.deleteFoodUseCase = deleteFoodUseCase;
        this.foodRequestValidator = foodRequestValidator;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postFood(FoodRequest request) {
        validateRequest(request);
        log.info("Creating food");
        Food food = builder.buildFood(request);
        food = createFoodUseCase.create(food);
        log.info("Food created. Id: " + food.getId());
        return Response.created(builder.buildCreatedUri(food))
                .build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam(QUERY_PARAM_FOOD_TYPE) String type) {
        log.info("Getting foods. Filter: " + type);
        List<Food> foods = readFoodUseCase.readAll(type);
        return Response.ok(builder.buildResponse(foods))
                .build();
    }

    @GET
    @Path(ENDPOINT_PATH_PARAM_ID)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam(PATH_PARAM_ID) String id) {
        log.info("Getting food by id. Id: " + id);
        Food food = readFoodUseCase.readById(id);
        return Response.ok(builder.buildResponse(food))
                .build();
    }

    @PUT
    @Path(ENDPOINT_PATH_PARAM_ID)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam(PATH_PARAM_ID) String id, FoodRequest request) {
        log.info("Updating food. Id: " + id);
        validateRequest(request);
        Food food = builder.buildFood(request, id);
        updateFoodUseCase.update(food);
        return Response.noContent().build();
    }

    @DELETE
    @Path(ENDPOINT_PATH_PARAM_ID)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam(PATH_PARAM_ID) String id) {
        log.info("Deleting food. Id: " + id);
        deleteFoodUseCase.delete(id);
        return Response.noContent().build();
    }

    private void validateRequest(FoodRequest request) {
        List<FoodApiErrorReason> errors = foodRequestValidator.validate(request);
        if (errors != null && !errors.isEmpty()) {
            log.warning("Request validation fail");
            throw new BadRequestException(errors);
        }
    }

}
