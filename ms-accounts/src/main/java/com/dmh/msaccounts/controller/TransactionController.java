package com.dmh.msaccounts.controller;

import com.dmh.msaccounts.exception.ApiError;
import com.dmh.msaccounts.model.dto.DepositDTO;
import com.dmh.msaccounts.model.dto.TransactionDTO;
import com.dmh.msaccounts.model.dto.requests.DepositDTORequest;
import com.dmh.msaccounts.model.dto.requests.TransferenceDTORequest;
import com.dmh.msaccounts.model.dto.responses.TransferenceDTOResponse;
import com.dmh.msaccounts.response.ResponseHandler;
import com.dmh.msaccounts.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@SecurityRequirement(name = "Bearer Authentication")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    ResponseHandler responseHandler;

    @Operation(summary = "Efetuar um depósito em uma conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Depósito efetuado com sucesso", content = @Content(schema =
            @Schema(implementation = DepositDTO.class))),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Erros de validação e fundos",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Conta não localizada na base de dados",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @PostMapping("/{id}/transactions")
    public ResponseEntity<Object> depositingValue(@Parameter(description = "Chave identificadora da conta") @PathVariable("id") Long accountId,
                                                  @Parameter(description = "Dados para realizar depósito de valores") @Valid @RequestBody DepositDTORequest depositDTORequest,
                                                  BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new Exception(String.valueOf(bindingResult.getAllErrors().get(0)));
        }
        return responseHandler.build(transactionService.depositingValue(accountId, depositDTORequest), HttpStatus.OK,
                "Successfully transferred");
    }

    //    /{id}/activity?page=0&size=10&sort=dateTransaction,asc
    @Operation(summary = "Busca todas as transações de uma conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transações localizadas", content = @Content(schema =
            @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Conta não localizada na base de dados",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @GetMapping("/{id}/activity")
    public ResponseEntity<Object> findAll(Pageable pageable,
                                          @Parameter(description = "Chave identificadora da conta de origem da operação") @PathVariable("id") Long accountOriginId) {
        String message = "Found transactions.";
        List<TransactionDTO> transactionDTOList = transactionService.findAll(pageable, accountOriginId);
        if (transactionDTOList.isEmpty()) {
            message = "No transaction found.";
        }
        return responseHandler.build(transactionDTOList, HttpStatus.OK, message);
    }

    @Operation(summary = "Busca todas as transações de uma conta onde o valor esteja entre o intervalo requerido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transações localizadas", content = @Content(schema =
            @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Conta não localizada na base de dados",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @GetMapping("/{id}/transactions-range")
    public ResponseEntity<Object> findAllTransferencesByRangeValue(@Parameter(description = "Chave identificadora da conta de origem da operação") @PathVariable("id") Long accountOriginId,
                                                                   @Parameter(description = "Valor mínimo (inclusivo)") @RequestParam("start") BigDecimal startValue,
                                                                   @Parameter(description = "Valor máximo (inclusivo)") @RequestParam("end") BigDecimal endValue) {
        String message = "Found transactions.";
        List<TransactionDTO> transactions = transactionService.findAllTransferenceByValueRange(accountOriginId,
                startValue, endValue);
        if (transactions.isEmpty()) {
            message = "No transactions found";
        }
        return responseHandler.build(transactions, HttpStatus.OK, message);
    }

    @Operation(summary = "Busca uma transação de uma conta específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação localizada", content = @Content(schema =
            @Schema(implementation = TransactionDTO.class))),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Conta não localizada na base de dados",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @GetMapping("/{id}/activity/{transactId}")
    public ResponseEntity<Object> findByAccountOriginIdAndTransactionId(@Parameter(description = "Chave identificadora da conta de origem da operação") @PathVariable("id") Long accountOriginId,
                                                                        @Parameter(description = "Chave identificadora da transação") @PathVariable("transactId") Long transactionId) {
        return responseHandler.build(transactionService.findByAccountOriginIdAndTransactionId(accountOriginId,
                transactionId), HttpStatus.OK, "Transaction found.");
    }

    @Operation(summary = "Efetuar uma transferência de uma conta para outra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transferência efetuada com sucesso", content =
            @Content(schema = @Schema(implementation = TransferenceDTOResponse.class))),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Erros de validação e fundos",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Conta não localizada na base de dados",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @PostMapping("/{id}/transferences")
    public ResponseEntity<Object> transferences(@Parameter(description = "Chave identificadora da conta") @PathVariable("id") Long accountId,
                                                @Parameter(description = "Dados para realizar transferência de valores") @Valid @RequestBody TransferenceDTORequest transferenceDTORequest,
                                                BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new Exception(String.valueOf(bindingResult.getAllErrors().get(0)));
        }
        return responseHandler.build(transactionService.transferringValue(accountId, transferenceDTORequest), HttpStatus.OK,
                "Successfully transferred transaction");

    }

    @Operation(summary = "Busca as 5 transações mais recentes de uma conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transações localizadas", content = @Content(schema =
            @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Conta não localizada na base de dados",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @GetMapping("/{id}/transactions")
    public ResponseEntity<Object> getLastTransactions(@Parameter(description = "Chave identificadora da conta") @PathVariable("id") Long accountId) {
        return responseHandler.build(transactionService.getLast5Transactions(accountId), HttpStatus.OK, "Transactions" +
                " found.");
    }

    @Operation(summary = "Busca as 10 transferências mais recentes de uma conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transferências localizadas", content = @Content(schema =
            @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Conta não localizada na base de dados",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @GetMapping("/{id}/transferences")
    public ResponseEntity<Object> getLast10Trasferences(@Parameter(description = "Chave identificadora da conta") @PathVariable("id") Long accountId) {
        return responseHandler.build(transactionService.getLast10Transferences(accountId), HttpStatus.OK,
                "Transferences found.");
    }

    @Operation(summary = "Busca as 5 contas de destino mais recentes que uma conta de origen transferiu valores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contas de destino localizadas", content =
            @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Conta não localizada na base de dados",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @GetMapping("/{id}/transferences/accounts")
    public ResponseEntity<Object> getLastAccountsDestiny(@Parameter(description = "Chave identificadora da conta") @PathVariable("id") Long accountId) {
        return responseHandler.build(transactionService.getLast5AccountsDestiny(accountId), HttpStatus.OK, "Last 5 " +
                "accounts destiny.");
    }

    @Operation(summary = "Gerar um documento pdf de uma transferência realizada por uma conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transações localizadas", content = @Content(schema =
            @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Conta não localizada na base de dados",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @GetMapping("/{id}/transferences/document")
    public void getVoucher(@Parameter(description = "Chave identificadora da transferência") @PathVariable("id") Long tranferenceId, HttpServletRequest request,
                           HttpServletResponse response) {
        transactionService.getVoucher(tranferenceId, request, response);
    }

}
