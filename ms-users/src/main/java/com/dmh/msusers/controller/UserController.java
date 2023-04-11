package com.dmh.msusers.controller;

import com.dmh.msusers.exceptions.ApiError;
import com.dmh.msusers.exceptions.InvalidFieldException;
import com.dmh.msusers.model.dto.LoginCredentialsDTO;
import com.dmh.msusers.model.dto.UserDTORequest;
import com.dmh.msusers.model.dto.UserDTOResponse;
import com.dmh.msusers.model.dto.UserPatchDTORequest;
import com.dmh.msusers.response.ResponseHandler;
import com.dmh.msusers.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ResponseHandler responseHandler;

    @Operation(summary = "Registrar um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário salvo", content = @Content(schema =
            @Schema(implementation = UserDTOResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro ao acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @PostMapping("/registration")
    public ResponseEntity<Object> create(@Parameter(description = "Usuário a ser salvo na base de dados") @Valid @RequestBody UserDTORequest userDTO,
                                         BindingResult result) {

        if (result.hasErrors())
            throw new InvalidFieldException(result.getAllErrors().get(0).getDefaultMessage());

        return userService.create(userDTO);
    }

    @Operation(summary = "Buscar um usuário por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário localizado", content = @Content(schema =
            @Schema(implementation = UserDTOResponse.class))),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não localizado na base de dados",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@Parameter(description = "Chave identificadora do usuário") @PathVariable(
            "id") String id) {
        return responseHandler.build(userService.findById(id), HttpStatus.OK, "user found.");
    }

    @Operation(summary = "Atualizar dados de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não localizado na base de dados",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateById(@Parameter(description = "Chave identificadora do usuário") @PathVariable("id") String id,
                                             @Parameter(description = "Dados do usuário a ser atualizado") @RequestBody UserPatchDTORequest userPatch) {
        userService.updateById(id, userPatch);
        return responseHandler.build(null, HttpStatus.OK, "user updated.");
    }

    @Operation(summary = "Obter um token de acesso para um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token gerado com sucesso", content = @Content(schema =
            @Schema(implementation = AccessTokenResponse.class))),
            @ApiResponse(responseCode = "400", description = "Usuário existente ou Usuário não verificado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não localizado na base de dados",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @PostMapping("/login")
    public ResponseEntity<Object> login(@Parameter(description = "Credenciais de login do usuário") @Valid @RequestBody LoginCredentialsDTO loginCredentials, BindingResult result) {
        if (result.hasErrors())
            throw new InvalidFieldException(result.getAllErrors().get(0).getDefaultMessage());

        return responseHandler.build(userService.login(loginCredentials.getEmail(), loginCredentials.getPassword()),
                HttpStatus.OK, "successfully logged.");
    }

    @Operation(summary = "Invalida tokens de acesso de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout efetuado com sucesso", content = @Content(schema =
            @Schema(implementation = AccessTokenResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não localizado na base de dados",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @PostMapping("/logout/{id}")
    public ResponseEntity<Object> logout(@Parameter(description = "Chave identificadora do usuário") @PathVariable("id") String id,
                                         HttpServletRequest request) {
        userService.logout(request, id);
        return responseHandler.build(null, HttpStatus.OK, "successfully logout.");
    }

}
