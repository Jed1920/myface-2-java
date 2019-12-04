package uk.co.techswitch.myface.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.co.techswitch.myface.models.api.password.CreatePassword;
import uk.co.techswitch.myface.models.api.password.PasswordModel;
import uk.co.techswitch.myface.models.database.Password;
import uk.co.techswitch.myface.services.PasswordService;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/password")

public class PasswordController {

    private final PasswordService passwordService;
    public PasswordController(PasswordService passwordService){ this.passwordService = passwordService;}

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public PasswordModel createPassword(@ModelAttribute @Valid CreatePassword createPassword) throws NoSuchAlgorithmException {
        Password password = passwordService.createPassword(createPassword);

        PasswordModel model = new PasswordModel(password);
        return model;
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public boolean checkPassword(@ModelAttribute @Valid CreatePassword createPassword) throws NoSuchAlgorithmException {
        boolean check = passwordService.checkPassword(createPassword);

        return check;
    }

}
