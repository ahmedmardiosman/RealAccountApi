package com.qa.account.accountapi.service;

import com.qa.account.accountapi.persistence.domain.Account;
import com.qa.account.accountapi.persistence.repository.AccountRepository;
import com.qa.account.accountapi.util.exceptions.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository repo;

    @Override
    public List<Account> getAccounts() {
        return repo.findAll();
    }

    @Override
    public Account getAccount(Long id) {
        Optional<Account> account = repo.findById(id);
        return account.orElseThrow(() -> new AccountNotFoundException(id.toString()));
    }

    @Override
    public Account addAccount(Account account) {
        return repo.save(account);
    }

    @Override
    public ResponseEntity<Object> deleteAccount(Long id) {
        if(accountExists(id)){
            repo.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Object> updateAccount(Account account, Long id) {
        if(accountExists(id)){
            account.setId(id);
//            updateAccountAttributes(account,id);
            repo.save(account);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

//    private Account updateAccountAttributes(Account updatedAccount, Long id){
//        Account accountInDb = repo.findById(id).get();
//        boolean firstNameHasBeenUpdated = checkIfUpdated(updatedAccount.getFirstName());
//        boolean lastNameHasBeenUpdated = checkIfUpdated(updatedAccount.getLastName());
//
//        if(firstNameHasBeenUpdated){
//            accountInDb.setFirstName(updatedAccount.getFirstName());
//        }
//        if(lastNameHasBeenUpdated){
//            accountInDb.setLastName(updatedAccount.getLastName());
//        }
//        return accountInDb;
//    }
//
//    private <T> boolean checkIfUpdated(T information){
//        return information != null;
//    }


    private boolean accountExists(Long id){
        Optional<Account> accountOptional = repo.findById(id);
        return accountOptional.isPresent();
    }

}
