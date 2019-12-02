package uk.co.techswitch.myface.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;
import uk.co.techswitch.myface.models.api.reactions.CreateReaction;
import uk.co.techswitch.myface.models.api.reactions.UpdateReaction;
import uk.co.techswitch.myface.models.database.Reaction;
import uk.co.techswitch.myface.services.ReactionsService;

import javax.validation.Valid;


@Controller
@RequestMapping("/reactions")
public class ReactionsController {
    private final ReactionsService reactionsService;

    public ReactionsController(ReactionsService reactionsService) {
        this.reactionsService = reactionsService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public RedirectView createReaction(@ModelAttribute @Valid CreateReaction createReaction) {
        Reaction reaction = reactionsService.createReaction(createReaction);
        return new RedirectView("/posts");

    }
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public RedirectView updateReaction(@PathVariable("id") long id, @ModelAttribute @Valid UpdateReaction updateReaction) {
        Reaction reaction = reactionsService.updateReaction(id,updateReaction);
        return new RedirectView("/posts");
    }

}