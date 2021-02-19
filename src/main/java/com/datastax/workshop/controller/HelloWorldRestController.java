package com.datastax.workshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springdoc.core.Constants.SWAGGER_UI_PATH;
import static org.springframework.util.AntPathMatcher.DEFAULT_PATH_SEPARATOR;
import static org.springframework.web.servlet.view.UrlBasedViewResolver.REDIRECT_URL_PREFIX;


@RestController
public class HelloWorldRestController {


    @Value(SWAGGER_UI_PATH)
    private String swaggerUiPath;

    @GetMapping(DEFAULT_PATH_SEPARATOR)
    @Operation(summary = "Does it work?",
            description = "Mike-testing, hello hello....123",
            tags = {"check"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "All is well",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Some warning from somewhere"),
            @ApiResponse(responseCode = "500", description = "An error occured")})
    public String index() {
        return REDIRECT_URL_PREFIX + swaggerUiPath;
    }
}
