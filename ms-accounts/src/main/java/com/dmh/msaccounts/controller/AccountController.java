package com.dmh.msaccounts.controller;

import com.dmh.msaccounts.exception.ApiError;
import com.dmh.msaccounts.exception.InvalidFieldException;
import com.dmh.msaccounts.model.dto.requests.AccountsDTORequest;
import com.dmh.msaccounts.model.dto.requests.CardsDTORequest;
import com.dmh.msaccounts.model.dto.responses.AccountsDTOResponse;
import com.dmh.msaccounts.response.ResponseHandler;
import com.dmh.msaccounts.service.AccountServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
@SecurityRequirement(name = "Bearer Authentication")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    ResponseHandler responseHandler;

    @Operation(summary = "Criar uma conta para um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conta criada com sucesso", content = @Content(schema =
            @Schema(implementation = AccountsDTOResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro ao acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @PostMapping()
    public ResponseEntity<Object> createAccount(@Parameter(description = "Chave identificadora da conta") @Valid @RequestBody AccountsDTORequest accountsDTORequest,
                                                @Parameter(description = "Parâmetro que indica se é a primeira conta de um usuário") @RequestParam(name = "first-account",
                                                        required = false) boolean createUserWithAccount,
                                                BindingResult bdResult) {
        if (bdResult.hasErrors()) {
            throw new InvalidFieldException(bdResult.getAllErrors().get(0).getDefaultMessage());
        }
        AccountsDTOResponse accountsDTOResponse;

        if (createUserWithAccount)
            accountsDTOResponse = accountService.createAccount(accountsDTORequest, true);
        else
            accountsDTOResponse = accountService.createAccount(accountsDTORequest, false);

        return responseHandler.build(accountsDTOResponse, HttpStatus.CREATED, "Account " +
                "created.");
    }

    @Operation(summary = "Buscar uma conta por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta localizada", content = @Content(schema =
            @Schema(implementation = AccountsDTOResponse.class))),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Conta não localizada na base de dados",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> findAccountById(@Parameter(description = "Chave identificadora da conta") @PathVariable(
            "id") Long id) {
        return responseHandler.build(accountService.findAccountById(id), HttpStatus.OK, "Account found.");
    }

    @Operation(summary = "Busca todos os cartões de uma conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cartões localizados", content = @Content(schema =
            @Schema(implementation = AccountsDTOResponse.class))),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Conta não localizada na base de dados",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @GetMapping("/{accountId}/cards")
    public ResponseEntity<Object> findCardsByAccount(@Parameter(description = "Chave identificadora da conta") @PathVariable(
            "accountId") Long accountId) {
        return responseHandler.build(accountService.findAccountById(accountId), HttpStatus.OK, "Cards found");
    }

    @Operation(summary = "Adiciona um cartão a uma conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cartão adicionado", content = @Content(schema =
            @Schema(implementation = AccountsDTOResponse.class))),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Cartão já registrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Conta não localizada na base de dados",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @PostMapping("/{accountId}/cards")
    public ResponseEntity<Object> createCardByAccount(@Parameter(description = "Chave identificadora da conta") @PathVariable("accountId") Long accountId,
                                                      @Parameter(description = "Cartão a ser adicionado à conta") @RequestBody CardsDTORequest cardsDTORequest) {
        return responseHandler.build(accountService.createCardByAccount(accountId, cardsDTORequest), HttpStatus.CREATED, "Card associated");
    }

    @Operation(summary = "Busca um cartão pertencente a uma conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cartão localizado", content = @Content(schema =
            @Schema(implementation = AccountsDTOResponse.class))),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Conta ou cartão não localizados na base de dados",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @GetMapping("/{accountId}/cards/{cardId}")
    public ResponseEntity<Object> findCardOfAccountById(@Parameter(description = "Chave identificadora da conta") @PathVariable("accountId") Long accountId,
                                                        @Parameter(description = "Chave identificadora do cartão") @PathVariable("cardId") Long cardId) {
        return responseHandler.build(accountService.findAccountCardsById(accountId, cardId), HttpStatus.OK, "Card found");
    }

    @Operation(summary = "Remove um cartão de uma conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cartão removido com sucesso", content = @Content(schema =
            @Schema(implementation = AccountsDTOResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro ao acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Conta ou cartão não localizados na base de dados",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @DeleteMapping("/{accountId}/cards/{cardId}")
    public ResponseEntity<Object> deleteCardOfAccountById(@Parameter(description = "Chave identificadora da conta") @PathVariable("accountId") Long accountId,
                                                          @Parameter(description = "Chave identificadora do cartão") @PathVariable("cardId") Long cardId) {
        return responseHandler.build(accountService.deleteCardOfAccountById(accountId, cardId), HttpStatus.OK, "Card deleted");
    }

}


