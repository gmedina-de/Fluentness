package ${package}.flow;

import org.fluentness.flow.console.Task;
import org.fluentness.flow.console.TaskProducer;

public class Tasks extends TaskProducer {

    Task dummy = does("Nothing useful",
        arguments -> {
            System.out.println("Dummy");
        }
    );

}
