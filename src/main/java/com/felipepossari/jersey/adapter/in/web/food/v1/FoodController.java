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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/foods")
public class FoodController {

    private static final Logger log = LoggerFactory.getLogger(FoodController.class);
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
        log.info("Food created. Id: {}", food.getId());
        return Response.created(builder.buildCreatedUri(food))
                .build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Food> foods = readFoodUseCase.readAll();
        return Response.ok(builder.buildResponse(foods))
                .build();
    }

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") String id) {
        Food food = readFoodUseCase.readById(id);
        return Response.ok(builder.buildResponse(food))
                .build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") String id, FoodRequest request) {
        validateRequest(request);
        Food food = builder.buildFood(request, id);
        updateFoodUseCase.update(food);
        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String id) {
        deleteFoodUseCase.delete(id);
        return Response.noContent().build();
    }

    private void validateRequest(FoodRequest request) {
        List<FoodApiErrorReason> errors = foodRequestValidator.validate(request);
        if (errors != null && !errors.isEmpty()) {
            throw new BadRequestException(errors);
        }
    }

}
