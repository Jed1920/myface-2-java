package uk.co.techswitch.myface.APIController;

import org.springframework.web.bind.annotation.*;
import uk.co.techswitch.myface.models.api.posts.CreatePost;
import uk.co.techswitch.myface.models.api.posts.PostModel;
import uk.co.techswitch.myface.models.api.posts.UpdatePost;
import uk.co.techswitch.myface.models.database.Post;
import uk.co.techswitch.myface.services.PostsService;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/posts")
public class ApiPostsController {

    private final PostsService postsService;

    public ApiPostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PostModel getPostDetails(@PathVariable("id") long id) {
        Post post = postsService.getById(id);
        PostModel model = new PostModel(post);
        return model;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public PostModel createPost(@ModelAttribute @Valid CreatePost createPost) {
        Post post = postsService.createPost(createPost);
        PostModel model = new PostModel(post);
        return model;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public PostModel updatePost(@PathVariable("id") long id, @ModelAttribute @Valid UpdatePost updatePost){
        Post post = postsService.updatePost(id,updatePost);
        PostModel model = new PostModel(post);
        return model;
    }

    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public void deletePost(@PathVariable("id") long id){
        postsService.deletePost(id);
    }

}
