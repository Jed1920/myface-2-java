package uk.co.techswitch.myface;

import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;
import uk.co.techswitch.myface.models.api.comments.CommentsFilter;
import uk.co.techswitch.myface.models.database.Comment;
import uk.co.techswitch.myface.models.database.Post;
import uk.co.techswitch.myface.models.database.User;
import uk.co.techswitch.myface.services.CommentsService;
import uk.co.techswitch.myface.services.PostsService;
import uk.co.techswitch.myface.services.UsersService;

import java.util.List;

@Component
public class GraphQLDataFetchers {

    private final UsersService usersService;
    private final PostsService postsService;
    private final CommentsService commentsService;

    public GraphQLDataFetchers(UsersService usersService, PostsService postsService, CommentsService commentsService) {
        this.usersService = usersService;
        this.postsService = postsService;
        this.commentsService = commentsService;
    }

    public DataFetcher getUserByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            Long userId = Long.parseLong(dataFetchingEnvironment.getArgument("id"));
            User user = usersService.getById(userId);

            return user;
        };
    }

    public DataFetcher getPostByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            Long postId = Long.parseLong(dataFetchingEnvironment.getArgument("id"));
            Post post = postsService.getById(postId);

            return post;
        };
    }

    public DataFetcher getCommentByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            Long commentId = Long.parseLong(dataFetchingEnvironment.getArgument("id"));
            Comment comment = commentsService.getById(commentId);

            return comment;
        };
    }

    public DataFetcher getUserByPostDataFetcher() {
        return dataFetchingEnvironment -> {
            Post post = dataFetchingEnvironment.getSource();
            User user = usersService.getById(post.getSender());

            return user;
        };
    }

    public DataFetcher getUserByCommentDataFetcher() {
        return dataFetchingEnvironment ->{
            Comment comment = dataFetchingEnvironment.getSource();
            User user = usersService.getById(comment.getSender());

            return user;
        };
    }

    public DataFetcher getCommentsbyPostDataFetcher() {
        return dataFetchingEnvironment -> {
            Post post = dataFetchingEnvironment.getSource();
            CommentsFilter commentsFilter = new CommentsFilter();
            commentsFilter.setPost(post.getId());
            List<Comment> comments = commentsService.searchComments(commentsFilter);

            return comments;
        };
    }
}
