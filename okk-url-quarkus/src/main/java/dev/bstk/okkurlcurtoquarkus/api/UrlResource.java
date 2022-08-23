package dev.bstk.okkurlcurtoquarkus.api;

import dev.bstk.okkurlcurtoquarkus.api.request.UrlRequest;
import dev.bstk.okkurlcurtoquarkus.api.response.UrlResponse;
import dev.bstk.okkurlcurtoquarkus.domain.UrlService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UrlResource {

    @Inject
    protected UrlService urlService;


    @POST
    @Path("/url")
    public Response encurtar(@Valid final UrlRequest request) {
        final var urlEncurtadada = urlService.encurtar(request);
        final UrlResponse urlResponse = new UrlResponse(
            urlEncurtadada.getUrlOriginal(),
            urlEncurtadada.getUrlEncurtada(),
            urlEncurtadada.getUrlOriginalQRCode()
        );

        return Response.ok(urlResponse).build();
    }

    @GET
    @Path("/{url_token}")
    public Response redirecionar(@PathParam("url_token") final String urlToken) {
        final var urlRedirecionar = urlService.redirecionar(urlToken);
        return Response
            .status(Response.Status.MOVED_PERMANENTLY)
            .location(urlRedirecionar)
            .build();
    }
}
