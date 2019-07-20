package org.fluentness.flow.task;

import org.fluentness.base.generics.Component;

public class Task extends Component {

    private String description;
    private TaskExecutor executable;
    private String[] arguments;

    Task(String description, TaskExecutor executable, String[] arguments) {
        this.description = description;
        this.executable = executable;
        this.arguments = arguments;
    }

    public String getDescription() {
        return description;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void execute(String... arguments) {
        executable.execute(arguments);
    }

}