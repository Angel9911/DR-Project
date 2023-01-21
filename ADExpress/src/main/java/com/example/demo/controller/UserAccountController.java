package com.example.demo.controller;

import com.example.demo.model.User_account;
import com.example.demo.services.Impl.UserAccountServiceImp;
import com.example.demo.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/account")
public class UserAccountController {
   // @Autowired
    UserAccountService userAccountService;
   // @Autowired
    UserAccountServiceImp userAccountServiceImp;

    @GetMapping(produces = "application/json")
    public ResponseEntity<Boolean> findUsername(@RequestParam(value = "username") String username) {
        if (username != null) {
            User_account result = userAccountService.IsUsernameExist(username);
            if (result != null) {
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
   /* @RequestMapping(value="/create",method = RequestMethod.POST,produces = "application/json")
    public ResponseEntity<Example> insertCustomer(@RequestBody User_account user_account){
        userAccountService.insertAccount(user_account);
        return new ResponseEntity<>(user_account, HttpStatus.OK);
    }
}
    */
}
