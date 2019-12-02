package uk.co.techswitch.myface.services;

import org.springframework.stereotype.Service;
import uk.co.techswitch.myface.models.api.reactions.CreateReaction;
import uk.co.techswitch.myface.models.api.reactions.UpdateReaction;
import uk.co.techswitch.myface.models.database.Reaction;

import java.util.Date;

@Service
public class ReactionsService extends DatabaseService {

    public Reaction getReaction(long id) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                        "SELECT * FROM reactions WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Reaction.class)
                        .one()
        );
    }

    public Reaction createReaction(CreateReaction reaction) {
         long id = jdbi.withHandle(handle -> {
                     handle.createUpdate(
                             "INSERT INTO reactions " +
                                     "(user_id, post_id, reaction, timestamp) " +
                                     "VALUES " +
                                     "(:user_id, :post_id, :reaction, :timestamp)")
                             .bind("user_id", reaction.getUser_id())
                             .bind("post_id", reaction.getPost_id())
                             .bind("reaction", reaction.getReaction())
                             .bind("timestamp", new Date())
                             .execute();
                     return handle.createQuery("SELECT MAX(id) FROM reactions;")
                             .mapTo(Long.class)
                             .one();
                 }
         );
    return getReaction(id);
    }

    public Reaction updateReaction(long id, UpdateReaction reaction){
        jdbi.withHandle(handle ->
                    handle.createUpdate(
                            "UPDATE reactions SET " +
                                    "reaction = :reaction, " +
                                    "timestamp = :timestamp "+
                                    "WHERE id = :id")
                            .bind("id",id)
                            .bind("reaction", reaction.getReaction())
                            .bind("timestamp", new Date())
                            .execute()
        );
        return getReaction(id);
    }


}
