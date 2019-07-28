package org.fluentness;

import org.fluentness.base.Base;
import org.fluentness.base.common.Environment;
import org.fluentness.base.common.exception.DefinitionException;
import org.fluentness.base.common.exception.ExecutionException;
import org.fluentness.base.common.exception.TaskNotFoundException;
import org.fluentness.base.common.exception.WrongUseOfTaskException;
import org.fluentness.data.Data;
import org.fluentness.flow.Flow;
import org.fluentness.flow.component.task.Task;
import org.fluentness.flow.provider.FluentnessTaskProvider;

public final class Fluentness {

    private Fluentness() {
        // instances of the class Fluentness are only used as proxy, avoiding instantiations or illegal modification of
        // base, data and flow
    }

    public static void bootstrap(Environment app, String[] args) {
        Fluentness proxy = new Fluentness();
        try {
            Base base = Base.getInstance(proxy);
            app.define(base);
            base.disallowDefinition();

            Data data = Data.getInstance(proxy);
            app.define(data);
            data.disallowDefinition();

            Flow flow = Flow.getInstance(proxy);
            app.define(flow);
            flow.disallowDefinition();

            execute(args);
        } catch (DefinitionException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void execute(String[] args) throws ExecutionException {
        try {
            FluentnessTaskProvider taskProvider = new FluentnessTaskProvider();

            if (args.length == 0) {
                taskProvider.getComponent("help").execute(args);
                System.exit(0);
            }

            String taskName = args[0];
            Task taskToExecute = null;
            String[] declaredArguments = new String[0];
            for (Task task : taskProvider.getAllTasks()) {
                if (taskName.equals(task.getName())) {
                    taskToExecute = task;
                    declaredArguments = task.getArguments();
                    break;
                }
            }

            if (taskToExecute == null) {
                throw new TaskNotFoundException("No %s task found", taskName);
            }

            if (declaredArguments.length != args.length - 1) {
                throw new WrongUseOfTaskException("Wrong use of task %s, expected %s arguments, got %s",
                    taskToExecute.getName(),
                    declaredArguments.length,
                    args.length - 1
                );
            }

            String[] arguments = new String[declaredArguments.length];
            System.arraycopy(args, 1, arguments, 0, args.length - 1);
            taskToExecute.execute(arguments);

        } catch (TaskNotFoundException | WrongUseOfTaskException e) {
            throw new ExecutionException(e);
        }
    }
}