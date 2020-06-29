package com.ironhack.server.controller.implementation;

import com.ironhack.server.controller.interfaces.AccountInterface;
import com.ironhack.server.dto.*;
import com.ironhack.server.model.Account;
import com.ironhack.server.service.AccountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountController implements AccountInterface {
    @Autowired
    AccountService accountService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('ACCOUNTHOLDER')")
    @ApiOperation(value = "See information of an account by ID. Only accessible by admin and account holder if the account belongs.")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountGetDTO getAccountById(@PathVariable Long id){
        return accountService.findAccountById(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('ACCOUNTHOLDER')")
    @ApiOperation(value = "See information of all accounts. " +
            "You can filter by: regularChecking | studentChecking | savings | credit " +
            "If admin will show all accounts in the system." +
            "If account holder will show all account that belong to him.")
    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountGetDTO> getAccounts(@RequestParam(name="filter", required = false, defaultValue = "") String filter){
        return accountService.getAccounts(filter);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Show all accounts of one account holder.")
    @GetMapping("/userAccounts/{ownerID}")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountGetDTO> getUserAccounts(@PathVariable Long ownerID){
        return accountService.getUserAccounts(ownerID);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Available currencies: [ETB, CRC, CVE, TMT, ADP, GIP, COU, BGN, ZWL, HKD, DOP, QAR, BOB, XAU, HRK, THB, MYR, NIO, \n" +
            "SKK, BND, UAH, CLP, CSD, SZL, GBP, PYG, UZS, ZMW, USD, RUR, INR, RWF, CHW, XCD, GMD, LAK, \n" +
            "EEK, LRD, GHS, XPD, CUP, XBA, KYD, RSD, GWP, MOP, SGD, SBD, AOA, XOF, XXX, MDL, SRD, SAR, \n" +
            "IQD, KMF, JPY, MXV, GNF, VUV, VEF, VND, LYD, FIM, GRD, OMR, AED, MGA, RON, ISK, EGP, MAD, \n" +
            "MWK, WST, SCR, KPW, ZWR, LBP, ZWN, CDF, ARS, FJD, BSD, NPR, TOP, ZAR, MUR, SHP, JOD, CHF, \n" +
            "CZK, SEK, SLL, JMD, USS, BGL, SDD, BRL, ITL, AYM, NOK, BHD, LUF, BMD, BTN, YER, LSL, ZMK, \n" +
            "MGF, MZM, AUD, PEN, CNY, HNL, HTG, XPF, XFU, UYI, GEL, CYP, TND, ATS, GHC, EUR, PGK, UGX, \n" +
            "TRY, SRG, SDG, XBD, LTL, KHR, UYU, CUC, MTL, COP, LKR, CHE, AWG, BAM, XAF, IDR, NZD, XBC, \n" +
            "AZN, MZN, ANG, BYR, CAD, NLG, XFO, MKD, TPE, KWD, XBB, SIT, AMD, MMK, BWP, ALL, TTD, DKK, \n" +
            "PLN, DEM, FRF, CLF, YUM, MXN, XUA, HUF, TWD, BDT, XDR, AZM, PTE, TMM, KZT, BBD, AFN, SYP, \n" +
            "PAB, VEB, KRW, MRO, AFA, ERN, IEP, BYB, SVC, BEF, KES, SSP, XPT, TRL, XTS, ZWD, XAG, NGN, \n" +
            "DZD, MNT, IRR, ESP, TJS, NAD, LVL, XSU, MVR, STD, RUB, DJF, PKR, PHP, BIF, TZS, ROL, GYD, \n" +
            "ILS, SOS, BZD, GTQ, KGS, USN, BOV, FKP]")
    @PostMapping("/create/checkingAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createCheckingAccount(@RequestBody CheckingAccountPostDTO accountDTO){
        return accountService.createCheckingAccount(accountDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Available currencies: [ETB, CRC, CVE, TMT, ADP, GIP, COU, BGN, ZWL, HKD, DOP, QAR, BOB, XAU, HRK, THB, MYR, NIO, \n" +
            "SKK, BND, UAH, CLP, CSD, SZL, GBP, PYG, UZS, ZMW, USD, RUR, INR, RWF, CHW, XCD, GMD, LAK, \n" +
            "EEK, LRD, GHS, XPD, CUP, XBA, KYD, RSD, GWP, MOP, SGD, SBD, AOA, XOF, XXX, MDL, SRD, SAR, \n" +
            "IQD, KMF, JPY, MXV, GNF, VUV, VEF, VND, LYD, FIM, GRD, OMR, AED, MGA, RON, ISK, EGP, MAD, \n" +
            "MWK, WST, SCR, KPW, ZWR, LBP, ZWN, CDF, ARS, FJD, BSD, NPR, TOP, ZAR, MUR, SHP, JOD, CHF, \n" +
            "CZK, SEK, SLL, JMD, USS, BGL, SDD, BRL, ITL, AYM, NOK, BHD, LUF, BMD, BTN, YER, LSL, ZMK, \n" +
            "MGF, MZM, AUD, PEN, CNY, HNL, HTG, XPF, XFU, UYI, GEL, CYP, TND, ATS, GHC, EUR, PGK, UGX, \n" +
            "TRY, SRG, SDG, XBD, LTL, KHR, UYU, CUC, MTL, COP, LKR, CHE, AWG, BAM, XAF, IDR, NZD, XBC, \n" +
            "AZN, MZN, ANG, BYR, CAD, NLG, XFO, MKD, TPE, KWD, XBB, SIT, AMD, MMK, BWP, ALL, TTD, DKK, \n" +
            "PLN, DEM, FRF, CLF, YUM, MXN, XUA, HUF, TWD, BDT, XDR, AZM, PTE, TMM, KZT, BBD, AFN, SYP, \n" +
            "PAB, VEB, KRW, MRO, AFA, ERN, IEP, BYB, SVC, BEF, KES, SSP, XPT, TRL, XTS, ZWD, XAG, NGN, \n" +
            "DZD, MNT, IRR, ESP, TJS, NAD, LVL, XSU, MVR, STD, RUB, DJF, PKR, PHP, BIF, TZS, ROL, GYD, \n" +
            "ILS, SOS, BZD, GTQ, KGS, USN, BOV, FKP]")
    @PostMapping("/create/savingsAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createSavingsAccount(@RequestBody SavingsAccountPostDTO accountDTO){
        return accountService.createSavingsAccount(accountDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Available currencies: [ETB, CRC, CVE, TMT, ADP, GIP, COU, BGN, ZWL, HKD, DOP, QAR, BOB, XAU, HRK, THB, MYR, NIO, \n" +
            "SKK, BND, UAH, CLP, CSD, SZL, GBP, PYG, UZS, ZMW, USD, RUR, INR, RWF, CHW, XCD, GMD, LAK, \n" +
            "EEK, LRD, GHS, XPD, CUP, XBA, KYD, RSD, GWP, MOP, SGD, SBD, AOA, XOF, XXX, MDL, SRD, SAR, \n" +
            "IQD, KMF, JPY, MXV, GNF, VUV, VEF, VND, LYD, FIM, GRD, OMR, AED, MGA, RON, ISK, EGP, MAD, \n" +
            "MWK, WST, SCR, KPW, ZWR, LBP, ZWN, CDF, ARS, FJD, BSD, NPR, TOP, ZAR, MUR, SHP, JOD, CHF, \n" +
            "CZK, SEK, SLL, JMD, USS, BGL, SDD, BRL, ITL, AYM, NOK, BHD, LUF, BMD, BTN, YER, LSL, ZMK, \n" +
            "MGF, MZM, AUD, PEN, CNY, HNL, HTG, XPF, XFU, UYI, GEL, CYP, TND, ATS, GHC, EUR, PGK, UGX, \n" +
            "TRY, SRG, SDG, XBD, LTL, KHR, UYU, CUC, MTL, COP, LKR, CHE, AWG, BAM, XAF, IDR, NZD, XBC, \n" +
            "AZN, MZN, ANG, BYR, CAD, NLG, XFO, MKD, TPE, KWD, XBB, SIT, AMD, MMK, BWP, ALL, TTD, DKK, \n" +
            "PLN, DEM, FRF, CLF, YUM, MXN, XUA, HUF, TWD, BDT, XDR, AZM, PTE, TMM, KZT, BBD, AFN, SYP, \n" +
            "PAB, VEB, KRW, MRO, AFA, ERN, IEP, BYB, SVC, BEF, KES, SSP, XPT, TRL, XTS, ZWD, XAG, NGN, \n" +
            "DZD, MNT, IRR, ESP, TJS, NAD, LVL, XSU, MVR, STD, RUB, DJF, PKR, PHP, BIF, TZS, ROL, GYD, \n" +
            "ILS, SOS, BZD, GTQ, KGS, USN, BOV, FKP]")
    @PostMapping("/create/creditAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createCreditAccount(@RequestBody CreditCardAccountPostDTO accountDTO){
        return accountService.createCreditCard(accountDTO);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('ACCOUNTHOLDER')")
    @GetMapping("/{id}/balance")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getBalanceAccount(@PathVariable Long id){
        return accountService.getBalanceAccount(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('THIRDPARTY')")
    @PatchMapping("/{id}/credit/{amount}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void creditAccount(@PathVariable Long id, @PathVariable BigDecimal amount){
         accountService.creditAccount(id, amount);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('THIRDPARTY')")
    @PatchMapping("/{id}/debit/{amount}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void debitAccount(@PathVariable Long id, @PathVariable BigDecimal amount){
        accountService.debitAccount(id, amount);
    }

    @PreAuthorize("hasRole('ACCOUNTHOLDER')")
    @PostMapping("/transaction")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionGetDTO executeTransaction(@RequestBody TransactionPostDTO transactionDTO){
        return accountService.transaction(transactionDTO);
    }

}
