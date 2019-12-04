package uk.co.techswitch.myface.APIController;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.co.techswitch.myface.models.api.ResultsPage;
import uk.co.techswitch.myface.models.api.ResultsPageBuilder;
import uk.co.techswitch.myface.models.api.password.CreatePassword;
import uk.co.techswitch.myface.models.api.password.PasswordUserModel;
import uk.co.techswitch.myface.models.api.posts.CreatePost;
import uk.co.techswitch.myface.models.api.posts.PostModel;
import uk.co.techswitch.myface.models.api.posts.UpdatePost;
import uk.co.techswitch.myface.models.api.users.CreateUser;
import uk.co.techswitch.myface.models.api.users.UpdateUser;
import uk.co.techswitch.myface.models.api.users.UserModel;
import uk.co.techswitch.myface.models.api.users.UsersFilter;
import uk.co.techswitch.myface.models.database.Password;
import uk.co.techswitch.myface.models.database.Post;
import uk.co.techswitch.myface.models.database.User;
import uk.co.techswitch.myface.services.PasswordService;
import uk.co.techswitch.myface.services.PostsService;
import uk.co.techswitch.myface.services.UsersService;

import javax.validation.Valid;
import javax.xml.transform.Result;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class ApiUsersController {

    private final UsersService usersService;
    private final PasswordService passwordService;

    public ApiUsersController(UsersService usersService, PasswordService passwordService) {
        this.usersService = usersService;
        this.passwordService = passwordService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResultsPage searchUsers(UsersFilter filter) {
        List<User> users = usersService.searchUsers(filter);
        int numberMatchingSearch = usersService.countUsers(filter);

        return new ResultsPageBuilder<UserModel, UsersFilter>()
                .withItems(users.stream().map(UserModel::new).collect(Collectors.toList()))
                .withFilter(filter)
                .withNumberMatchingSearch(numberMatchingSearch)
                .withBaseUrl("/posts")
                .build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserModel getUserDetails(@PathVariable("id") long id) {
        User user = usersService.getById(id);
        UserModel model = new UserModel(user);
        return model;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public PasswordUserModel createUser(@ModelAttribute @Valid CreateUser createUser,@ModelAttribute @Valid String passwordStr) throws NoSuchAlgorithmException {
        User user = usersService.createUser(createUser);
        CreatePassword createPassword = new CreatePassword();
        createPassword.setPassword(passwordStr);
        createPassword.setUserId(user.getId());
        Password password = passwordService.createPassword(createPassword);

        PasswordUserModel model = new PasswordUserModel(password,user);
        return model;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public UserModel updatePost(@PathVariable("id") long id, @ModelAttribute @Valid UpdateUser updateUser){
        User user = usersService.updateUser(id,updateUser);
        UserModel model = new UserModel(user);
        return model;
    }

    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") long id){
        usersService.deleteUser(id);
    }

}
