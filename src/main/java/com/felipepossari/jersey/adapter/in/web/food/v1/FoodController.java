package com.felipepossari.jersey.adapter.in.web.food.v1;

import com.felipepossari.jersey.adapter.in.web.food.v1.request.FoodRequest;
import com.felipepossari.jersey.application.domain.Food;
import com.felipepossari.jersey.application.port.in.CreateFoodUseCase;
import com.felipepossari.jersey.application.port.in.ReadFoodUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
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

    @Inject
    public FoodController(FoodBuilder builder,
                          CreateFoodUseCase createFoodUseCase,
                          ReadFoodUseCase readFoodUseCase) {
        this.builder = builder;
        this.createFoodUseCase = createFoodUseCase;
        this.readFoodUseCase = readFoodUseCase;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postFood(FoodRequest foodRequest) {
        log.info("Creating food");
        Food food = builder.buildFood(foodRequest);
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
}
