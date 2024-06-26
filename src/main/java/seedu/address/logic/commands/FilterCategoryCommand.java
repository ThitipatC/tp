package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.CategoryEqualsPredicate;

/**
 * Finds and lists all projects in DevPlanPro whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterCategoryCommand extends Command {

    public static final String COMMAND_WORD = "filter category";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all projects whose category matches the input "
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "A";

    private final CategoryEqualsPredicate predicate;

    public FilterCategoryCommand(CategoryEqualsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredProjectList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_CATEGORY_LISTED_OVERVIEW, model.getFilteredProjectList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCategoryCommand)) {
            return false;
        }

        FilterCategoryCommand otherFilterCommand = (FilterCategoryCommand) other;
        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
