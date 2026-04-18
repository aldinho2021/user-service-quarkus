package com.musicfy.adapters.in.web;

import com.musicfy.application.port.in.UserUseCase;
import com.musicfy.domain.User;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

@Path("/api/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    private final UserUseCase useCase;

    public UserController(UserUseCase useCase) {
        this.useCase = useCase;
    }

    @GET
    public List<User> findAll() {
        return useCase.findAll();
    }

    @GET
    @Path("/{id}")
    public User findById(@PathParam("id") Long id) {
        return useCase.findById(id);
    }

    @POST
    public Response create(@Valid User user, @Context UriInfo uriInfo) {
        User created = useCase.create(user);
        URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(created.getId())).build();
        return Response.created(location).entity(created).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        useCase.delete(id);
        return Response.noContent().build();
    }
}