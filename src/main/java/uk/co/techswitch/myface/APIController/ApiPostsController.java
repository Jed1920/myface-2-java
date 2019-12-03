package uk.co.techswitch.myface.APIController;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.co.techswitch.myface.models.api.ResultsPage;
import uk.co.techswitch.myface.models.api.ResultsPageBuilder;
import uk.co.techswitch.myface.models.api.posts.CreatePost;
import uk.co.techswitch.myface.models.api.posts.PostModel;
import uk.co.techswitch.myface.models.api.posts.PostsFilter;
import uk.co.techswitch.myface.models.api.posts.UpdatePost;
import uk.co.techswitch.myface.models.database.Post;
import uk.co.techswitch.myface.services.PostsService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/posts")
public class ApiPostsController {

    private final PostsService postsService;

    public ApiPostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResultsPage searchPosts(PostsFilter filter) {
        List<Post> posts = postsService.searchPosts(filter);
        int numberMatchingSearch = postsService.countPosts(filter);

        return new ResultsPageBuilder<PostModel, PostsFilter>()
                .withItems(posts.stream().map(PostModel::new).collect(Collectors.toList()))
                .withFilter(filter)
                .withNumberMatchingSearch(numberMatchingSearch)
                .withBaseUrl("/posts")
                .build();
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
