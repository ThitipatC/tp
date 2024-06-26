package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.Planner;

/**
 * Clears the project list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear project";
    public static final String MESSAGE_SUCCESS = "Project list has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setPlanner(new Planner());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
