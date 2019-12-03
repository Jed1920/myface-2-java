package uk.co.techswitch.myface.APIController;

import org.springframework.web.bind.annotation.*;
import uk.co.techswitch.myface.models.api.comments.CommentModel;
import uk.co.techswitch.myface.models.api.comments.CreateComment;
import uk.co.techswitch.myface.models.api.comments.UpdateComment;
import uk.co.techswitch.myface.models.api.posts.CreatePost;
import uk.co.techswitch.myface.models.api.posts.PostModel;
import uk.co.techswitch.myface.models.api.posts.UpdatePost;
import uk.co.techswitch.myface.models.database.Comment;
import uk.co.techswitch.myface.models.database.Post;
import uk.co.techswitch.myface.services.CommentsService;
import uk.co.techswitch.myface.services.PostsService;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/comments")
public class ApiCommentsController {

    private final CommentsService commentsService;

    public ApiCommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommentModel getCommentDetails(@PathVariable("id") long id) {
        Comment comment = commentsService.getById(id);
        CommentModel model = new CommentModel(comment);
        return model;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommentModel createComment(@ModelAttribute @Valid CreateComment createComment) {
        Comment comment = commentsService.createComment(createComment);
        CommentModel model = new CommentModel(comment);
        return model;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public CommentModel updateComment(@PathVariable("id") long id, @ModelAttribute @Valid UpdateComment updateComment){
        Comment comment = commentsService.updateComment(id,updateComment);
        CommentModel model = new CommentModel(comment);
        return model;
    }

    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable("id") long id){
        commentsService.deleteComment(id);
    }

}
