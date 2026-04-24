package com.musicfy.adapters.in.web;

import com.musicfy.application.port.in.UserUseCase;
import com.musicfy.domain.User;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.List;

@Tag(name = "Users", description = "Operaciones CRUD de usuarios")
@Path("/api/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    private final UserUseCase useCase;

    public UserController(UserUseCase useCase) {
        this.useCase = useCase;
    }

    @Operation(summary = "Listar usuarios")
    @APIResponse(responseCode = "200", description = "Listado obtenido")
    @GET
    public List<User> findAll() {
        return useCase.findAll();
    }

    @Operation(summary = "Buscar usuario por id")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Usuario encontrado"),
            @APIResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GET
    @Path("/{id}")
    public User findById(@PathParam("id") Long id) {
        return useCase.findById(id);
    }

    @Operation(summary = "Crear usuario")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Usuario creado"),
            @APIResponse(responseCode = "400", description = "Request invalido")
    })
    @POST
    public Response create(@Valid User user, @Context UriInfo uriInfo) {
        User created = useCase.create(user);
        URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(created.getId())).build();
        return Response.created(location).entity(created).build();
    }

    @Operation(summary = "Actualizar usuario por id")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Usuario actualizado"),
            @APIResponse(responseCode = "404", description = "Usuario no encontrado"),
            @APIResponse(responseCode = "409", description = "Email duplicado")
    })
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid User user) {
        return Response.ok(useCase.update(id, user)).build();
    }

    @Operation(summary = "Eliminar usuario por id")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Usuario eliminado"),
            @APIResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        useCase.delete(id);
        return Response.noContent().build();
    }
}