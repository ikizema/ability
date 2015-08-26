package com.ability.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

/**
 * Created by ikizema on 15-08-21.
 */

@Provider
public class ExceptionResponseMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOG = Logger.getLogger(ExceptionResponseMapper.class.getName());

    @Override
    public Response toResponse(Throwable  exception) {
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;

        if (exception instanceof NotFound) {
            status = Response.Status.NOT_FOUND;
        }

        LOG.info("toResponse -> " + status + " : " + exception.getMessage());
        return Response.status(status).entity(exception.getMessage()).build();
    }

    public static class NotFound extends Exception {};
}
