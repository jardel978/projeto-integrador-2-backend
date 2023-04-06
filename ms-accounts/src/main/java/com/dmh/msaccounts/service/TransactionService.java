package com.dmh.msaccounts.service;

import com.dmh.msaccounts.exception.DataNotFoundException;
import com.dmh.msaccounts.exception.GenerateDocumentException;
import com.dmh.msaccounts.exception.InsufficientFundsException;
import com.dmh.msaccounts.model.*;
import com.dmh.msaccounts.model.dto.DepositDTO;
import com.dmh.msaccounts.model.dto.TransactionDTO;
import com.dmh.msaccounts.model.dto.TransferenceDTO;
import com.dmh.msaccounts.model.dto.requests.DepositDTORequest;
import com.dmh.msaccounts.model.dto.requests.TransferenceDTORequest;
import com.dmh.msaccounts.model.dto.responses.AccountTransferenceDTOResponse;
import com.dmh.msaccounts.model.dto.responses.TransferenceDTOResponse;
import com.dmh.msaccounts.repository.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

@Slf4j
@Service
public class TransactionService {

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private IAccountsRepository accountsRepository;

    @Autowired
    private ICardsRepository cardsRepository;

    @Autowired
    private ITransactionRepository transactionRepository;

    @Autowired
    private FeignUserRepository feignUserRepository;

    @Autowired
    private ModelMapper mapper;

    public TransactionDTO depositingValue(Long accountId, DepositDTORequest depositDTORequest) throws DataNotFoundException {

        Accounts account = accountsRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException(
                "Account not found my son!"));

        Cards card = cardsRepository.findById(depositDTORequest.getCardId()).orElseThrow(() -> {
            throw new DataNotFoundException("Card not found.");
        });


        BigDecimal initialAmmount = account.getAmmount();
        BigDecimal transValue = depositDTORequest.getValue();

        if (transValue.doubleValue() < 0.0) {
            throw new IllegalArgumentException("Not a valid transaction value");
        }

        BigDecimal newAmmount = initialAmmount.add(transValue);
        account.setAmmount(newAmmount);

        Deposit deposit = Deposit.builder()
                .cardType(card.getCardType())
                .value(transValue)
                .dateTransaction(new Date())
                .transactionType("cash deposit")
                .description(depositDTORequest.getDescription())
                .accountOrigin(account)
                .card(card).build();

//      accounts.getTransactions().add(transaction);
//      gravar novo saldo no account

        accountsRepository.save(account);

        return mapper.map(transactionRepository.saveAndFlush(deposit), DepositDTO.class);
    }

    public List<TransactionDTO> findAll(Pageable pageable, Long accountOriginId) {
        accountsRepository.findById(accountOriginId).orElseThrow(() -> {
            throw new DataNotFoundException("Account not found.");
        });
        Page<Transactions> transactionsPage = transactionRepository.findAllByAccountOriginId(pageable, accountOriginId);
        return
                transactionsPage.stream
                        ().map(transactions -> {
                    TransactionDTO transactionDTO = null;
                    if (transactions instanceof Deposit)
                        transactionDTO = mapper.map(transactions, DepositDTO.class);
                    if (transactions instanceof Transferences)
                        transactionDTO = mapper.map(transactions, TransferenceDTOResponse.class);

                    return transactionDTO;
                }).collect(Collectors.toList());
    }

    public TransactionDTO findByAccountOriginIdAndTransactionId(Long accountOriginId, Long transactionId) {
        Transactions transactionsModel = transactionRepository.findByAccountOriginIdAndId(accountOriginId,
                transactionId).orElseThrow(() -> {
            throw new DataNotFoundException("Transaction not found.");
        });
        if (transactionsModel instanceof Deposit)
            return mapper.map(transactionsModel, DepositDTO.class);
        if (transactionsModel instanceof Transferences)
            return mapper.map(transactionsModel, TransferenceDTOResponse.class);

        return null;
    }

    public TransactionDTO transferringValue(Long accountOriginId, TransferenceDTORequest transferenceDTORequest) throws DataNotFoundException {
        if (accountOriginId.equals(transferenceDTORequest.getAccountDestinyId()))
            throw new IllegalArgumentException("The source account must be different from the destination account.");

        Accounts accountOrigin =
                accountsRepository.findById(accountOriginId).orElseThrow(() -> new DataNotFoundException(
                        "Origin account not found."));

        Accounts accountDestination =
                accountsRepository.findById(transferenceDTORequest.getAccountDestinyId()).orElseThrow(() -> new DataNotFoundException(
                        "Account of destiny not found, my consagrated"));

        BigDecimal initialAmmountOrigin = accountOrigin.getAmmount();
        BigDecimal initialAmmountDestiny = accountDestination.getAmmount();
        BigDecimal transferenceValue = transferenceDTORequest.getValue();

        if (transferenceValue.doubleValue() < 0.0) {
            throw new IllegalArgumentException("Not a valid Transference value!");
        }

        if (accountOrigin.getAmmount().compareTo(transferenceValue) < 0)
            throw new InsufficientFundsException("Insufficient balance for transfer.");

        BigDecimal newAmmountOrigin = initialAmmountOrigin.subtract(transferenceValue);
        accountOrigin.setAmmount(newAmmountOrigin);
        BigDecimal newAmmountDestiny = initialAmmountDestiny.add(transferenceValue);
        accountDestination.setAmmount(newAmmountDestiny);

        Transferences transference = Transferences.builder()
                .accountsDestiny(accountDestination)
                .value(transferenceValue)
                .dateTransaction(new Date())
                .transactionType("bank transfer")
                .description(transferenceDTORequest.getDescription())
                .accountOrigin(accountOrigin)
                .accountsDestiny(accountDestination)
                .build();

        List<Accounts> accountsList = new ArrayList<>();
        accountsList.add(accountOrigin);
        accountsList.add(accountDestination);
        accountsRepository.saveAll(accountsList);

        return mapper.map(transactionRepository.saveAndFlush(transference), TransferenceDTO.class);
    }

    public List<TransactionDTO> getLast5Transactions(Long accountId) {
        List<Transactions> transactions =
                transactionRepository.findTop5ByAccountOriginIdOrderByDateTransactionDesc(accountId);

        return transactions.stream().map(transaction -> {
            if (transaction.getClass().equals(Deposit.class))
                return mapper.map(transaction, DepositDTO.class);
            else
                return mapper.map(transaction, TransferenceDTOResponse.class);

        }).collect(Collectors.toList());
    }

    public List<AccountTransferenceDTOResponse> getLast5AccountsDetiny(Long accountId) {
        List<IAccountsTransference> accountsDestinyList =
                transactionRepository.findTop5DistinctAccountsDestinyByAccountOriginId(accountId);


        return accountsDestinyList.stream().map(transference -> {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            User user =
                    objectMapper.convertValue(feignUserRepository.findByUserId(transference.getAccountsDestinyUserId()).getBody().get(
                            "data"), User.class);
            Transferences lastTransference = transactionRepository.findFirstByAccountDestinyId(transference.getId());
            AccountTransferenceDTOResponse transferenceDTOResponse = AccountTransferenceDTOResponse.builder()
                    .accountDestiny(transference.getAccountsDestinyAccount())
                    .recipient(user.getName() + " " + user.getLastName())
                    .dateTransaction(lastTransference.getDateTransaction()).build();
            return transferenceDTOResponse;
        }).collect(Collectors.toList());
    }

    public List<TransactionDTO> getLast10Transferences(Long accountId) {
        List<Transactions> transferences =
                transactionRepository.findByAccountOriginIdOrderByDateTransactionDescLimitedTo(accountId, 10);

        return transferences.stream().map(transference ->
                mapper.map(transference, TransferenceDTOResponse.class)).collect(Collectors.toList());
    }

    public void getVoucher(Long transferenceId, HttpServletRequest request, HttpServletResponse response) {
        Transferences transference = (Transferences) transactionRepository.findById(transferenceId).orElseThrow(() -> new DataNotFoundException("Transference not found."));
        Accounts accountDestination = accountsRepository.findById(transference.getAccountsDestiny().getId()).orElseThrow(() -> new DataNotFoundException("Account of destiny not found, my consagrated"));
        Accounts accountOrigin = accountsRepository.findById(transference.getAccountOrigin().getId()).orElseThrow(() -> new DataNotFoundException("Origin account not found."));
        User userOrigem = findUser(accountOrigin.getUserId());
        User userDestiny = findUser(accountDestination.getUserId());

        Document documentPDF = new Document(); //criando um documento vazio
        Locale locale = new Locale("pt", "BR");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        String dataAtual = dateFormat.format(new Date());
        String fileName =
                "Comprovante-" + accountOrigin.getId().toString() + dataAtual + ".pdf";
        response.setContentType(APPLICATION_PDF_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        try {
            String currDir = System.getProperty("user.dir");
            PdfWriter.getInstance(documentPDF, response.getOutputStream());
            documentPDF.open(); //abrindo documento
            documentPDF.setPageSize(PageSize.A4); //setando o tamanho do documento

//            String pathImage = currDir + "/ms-accounts/src/main/java/com/dmh/msaccounts/utils/DMH-extrato.png";
//            log.info("pathImage: " + pathImage);
//            Image img = Image.getInstance(new URL(pathImage));
//            img.setWidthPercentage(.3f);
//            img.setAlignment(Element.ALIGN_JUSTIFIED);
//            documentPDF.add(img);

            Font font = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);
            Chunk chunk = new Chunk("Comprovante de Transferência", font);
            chunk.setHorizontalScaling(Paragraph.ALIGN_CENTER);
            documentPDF.add(chunk);
            documentPDF.add(new Paragraph(" ")); // pula uma linha

            Paragraph accountOriginParagraph = new Paragraph("Conta de origem: " + accountOrigin.getAccount(), font);
            accountOriginParagraph.setAlignment(Element.ALIGN_MIDDLE);
            Paragraph owner =
                    new Paragraph("Titular da Conta: " + userOrigem.getName() + " " + userOrigem.getLastName(), font);
            owner.setAlignment(Element.ALIGN_MIDDLE);

            Paragraph accountDestinyParagraph = new Paragraph("Conta de destino: " + accountDestination.getAccount(),
                    font);
            accountDestinyParagraph.setAlignment(Element.ALIGN_MIDDLE);
            Paragraph recipient =
                    new Paragraph("Titular de destino: " + userDestiny.getName() + " " + userOrigem.getLastName(),
                            font);
            recipient.setAlignment(Element.ALIGN_MIDDLE);

            Paragraph dateTransaction =
                    new Paragraph("Data da transferência: " + dateFormat.format(transference.getDateTransaction()),
                            font);
            dateTransaction.setAlignment(Element.ALIGN_MIDDLE);
            Paragraph ammount = new Paragraph("Valor: " + accountDestination.getAmmount(), font);
            ammount.setAlignment(Element.ALIGN_MIDDLE);
            Paragraph key =
                    new Paragraph("Chave da Transação: " + accountOrigin.getAccount() + accountDestination.getAccount() + transference.getDateTransaction().toInstant().getEpochSecond(), font);
            key.setAlignment(Element.ALIGN_MIDDLE);

            documentPDF.add(accountOriginParagraph);
            documentPDF.add(owner);
            documentPDF.add(new Paragraph(" "));
            documentPDF.add(accountDestinyParagraph);
            documentPDF.add(recipient);
            documentPDF.add(new Paragraph(" "));
            documentPDF.add(dateTransaction);
            documentPDF.add(new Paragraph(" "));
            documentPDF.add(ammount);
            documentPDF.add(new Paragraph(" "));
            documentPDF.add(key);

        } catch (Exception e) {
            throw new GenerateDocumentException("Error generating voucher: " + e.getMessage());
        } finally {
            documentPDF.close();
        }
    }

    private User findUser(String userId) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        User user = objectMapper.convertValue(feignUserRepository.findByUserId(userId).getBody().get(
                "data"), User.class);

        return user;
    }

}