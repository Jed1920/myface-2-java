package uk.co.techswitch.myface.models.api.reactions;

import javax.validation.constraints.NotBlank;

public class UpdateReaction {
    @NotBlank
    private String reaction;

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }
}
