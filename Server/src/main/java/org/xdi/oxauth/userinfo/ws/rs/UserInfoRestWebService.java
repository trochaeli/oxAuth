package org.xdi.oxauth.userinfo.ws.rs;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * Provides interface for User Info REST web services
 *
 * @author Javier Rojas Blum Date: 11.29.2011
 */
@Path("/oxauth")
@Api(value = "/oxauth", description = "The UserInfo Endpoint is an OAuth 2.0 Protected Resource that returns Claims about the authenticated End-User. To obtain the requested Claims about the End-User, the Client makes a request to the UserInfo Endpoint using an Access Token obtained through OpenID Connect Authentication. These Claims are normally represented by a JSON object that contains a collection of name and value pairs for the Claims. ")
public interface UserInfoRestWebService {

    @GET
    @Path("/userinfo")
    @ApiOperation(
            value = "Returns Claims about the authenticated End-User.",
            notes = "The Access Token obtained from an OpenID Connect Authentication Request is sent as a Bearer Token.",
            response = Response.class,
            responseContainer = "JSON"
    )
    Response requestUserInfoGet(
            @QueryParam("access_token")
            @ApiParam(value = "OAuth 2.0 Access Token.", required = true)
            String accessToken,
            @HeaderParam("Authorization") String authorization,
            @Context SecurityContext securityContext);

    @POST
    @Path("/userinfo")
    @ApiOperation(
            value = "Returns Claims about the authenticated End-User.",
            notes = "The Access Token obtained from an OpenID Connect Authentication Request is sent as a Bearer Token.",
            response = Response.class,
            responseContainer = "JSON"
    )
    Response requestUserInfoPost(
            @FormParam("access_token")
            @ApiParam(value = "OAuth 2.0 Access Token.", required = true)
            String accessToken,
            @HeaderParam("Authorization") String authorization,
            @Context SecurityContext securityContext);
}