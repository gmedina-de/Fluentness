package org.fluentness.command;

import org.fluentness.ClassRegister;

public class HelpCommand implements Command {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Prints all available commands";
    }

    @Override
    public void execute(String[] args) {

        // print commands using colors
        System.out.println("\n" + Console.ANSI_GREEN + "Available commands:\n");
        for (Command command : ClassRegister.getCommandInstances()) {
            String format = Console.ANSI_YELLOW + "%20s " + Console.ANSI_WHITE + "- %s\n";
            System.out.format(format, command.getName(), command.getDescription());
        }
    }
}