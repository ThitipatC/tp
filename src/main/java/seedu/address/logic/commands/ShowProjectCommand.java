package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameEqualsPredicate;
import seedu.address.model.person.Person;

/**
 * Deletes a project identified using it's name from the project list.
 */
public class ShowProjectCommand extends Command {

    public static final String COMMAND_WORD = "show project";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the specified project. "
            + "Parameters: PROJECT_NAME\n"
            + "Example: " + COMMAND_WORD + " CS2103";

    public static final String MESSAGE_DELETE_PROJECT_SUCCESS = "[%1$s] is now being shown.";

    private final String targetName;

    public ShowProjectCommand(String targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person targetProject = new Person(new Name(targetName));
        Person projectToDelete = null;
        for (Person person : lastShownList) {
            if (person.isSamePerson(targetProject)) {
                projectToDelete = person;
                break;
            }
        }

        if (projectToDelete == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        model.updateCurrentProject(new NameEqualsPredicate(projectToDelete.getName().fullName));
        return new CommandResult(String.format(MESSAGE_DELETE_PROJECT_SUCCESS, Messages.format(projectToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ShowProjectCommand)) {
            return false;
        }

        ShowProjectCommand otherDeleteCommand = (ShowProjectCommand) other;
        return targetName.equals(otherDeleteCommand.targetName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetName", targetName)
                .toString();
    }
}