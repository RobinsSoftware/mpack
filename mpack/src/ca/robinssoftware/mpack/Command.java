package ca.robinssoftware.mpack;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ca.robinssoftware.mpack.Language.Fields;

public class Command implements CommandExecutor {

    private final Main main;

    Command(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!Permission.COMMAND.has(sender)) {
            sender.sendMessage(main.language.get(Fields.PREFIX) + main.language.get(Fields.NO_PERMISSION));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(main.language.get(Fields.PREFIX) + main.language.get(Fields.INSUFFICIENT_ARGS));
            return true;
        }

        switch (args[0].toLowerCase()) {
        case "add":
        case "a":
            if (args.length == 1) {
                sender.sendMessage(main.language.get(Fields.PREFIX) + main.language.get(Fields.INSUFFICIENT_ARGS));
                return true;
            }

            return true;
        case "remove":
        case "r":
            if (args.length == 1) {
                sender.sendMessage(main.language.get(Fields.PREFIX) + main.language.get(Fields.INSUFFICIENT_ARGS));
                return true;
            }

            return true;
        case "list":
        case "l":
            return true;
        case "update":
        case "u":
            if (args.length == 1) {
                sender.sendMessage(main.language.get(Fields.PREFIX) + main.language.get(Fields.INSUFFICIENT_ARGS));
                return true;
            }

            return true;
        case "version":
        case "v":
            sender.sendMessage(main.language.get(Fields.PREFIX) + main.language.get(Fields.VERSION, main.getDescription().getVersion()));
            return true;
        case "search":
        case "s":
            return true;
        case "help":
        case "?":
            return true;
        case "overlay":
            return true;
        case "sync":
            new Sync(main, sender);
            return true;
        default:
            sender.sendMessage(main.language.get(Fields.PREFIX) + main.language.get(Fields.COMMAND_NOT_FOUND, args[0].toLowerCase()));
            return true;
        }
    }

}
