package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameEqualsPredicate;
import seedu.address.model.project.Project;
import seedu.address.model.project.Task;

/**
 * Adds a deadline to a task.
 */
public class SetDeadlineTaskCommand extends SetDeadlineCommand {


    public static final String MESSAGE_USAGE = COMMAND_WORD + " DEADLINE /to TASK_NAME /in PROJECT_NAME";

    public static final String MESSAGE_SUCCESS = "The task %1$s has been set with the following deadline %2$s.";

    public static final String MESSAGE_PROJECT_NOT_FOUND = "Project %1$s not found: "
            + "Please make sure the project exists.";

    public static final String MESSAGE_TASK_NOT_FOUND = "Task %1$s not found: "
            + "Please make sure the task exists in project %2$s";

    public static final String MESSAGE_WRONG_FORMAT_DEADLINE = "The deadline %1s has been entered in the wrong format. "
            + "An example of the correct format is Mar 15 2024";

    private final Task task;
    private final String deadline;
    private final Project project;

    private final String datePattern = "\\b(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\b \\d{1,2} \\d{4}\\b";

    /**
     * Creates an SetDeadlineTaskCommand to add the specified {@code Task}
     */
    public SetDeadlineTaskCommand(String deadline, Task task, Project project) {
        requireNonNull(task);
        this.task = task;
        this.deadline = deadline;
        this.project = project;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!deadline.matches(datePattern)) {
            throw new CommandException(String.format(MESSAGE_WRONG_FORMAT_DEADLINE, deadline));
        }

        if (!model.hasProject(project)) {
            throw new CommandException(String.format(
                    MESSAGE_PROJECT_NOT_FOUND,
                    Messages.format(project)));
        }

        Project deadlineProject = model.findProject(project.getName());
        if (!deadlineProject.hasTask(task)) {
            throw new CommandException(String.format(
                    MESSAGE_TASK_NOT_FOUND,
                    Messages.format(task),
                    Messages.format(project)));
        }
        Task deadlineTask = deadlineProject.findTask(task.getName());

        deadlineTask.setDeadline(deadline);

        model.updateCurrentProject(
            new NameEqualsPredicate(
                model.getCurrentProject().get(0).getName().fullName));

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(deadlineTask), deadline));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetDeadlineTaskCommand)) {
            return false;
        }

        SetDeadlineTaskCommand otherSetDeadlineTaskCommand = (SetDeadlineTaskCommand) other;
        return project.equals(otherSetDeadlineTaskCommand.project)
                && task.equals(otherSetDeadlineTaskCommand.task)
                && deadline.equals(otherSetDeadlineTaskCommand.deadline);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("setDeadline", deadline)
                .toString();
    }
}
