package ca.robinssoftware.mpack;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabComplete implements TabCompleter {

    private final Main main;

    TabComplete(Main main) {
        this.main = main;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (Permission.COMMAND.has(sender))
            return null;
        
        List<String> completions = new ArrayList<>(), packages = new ArrayList<>(), categories = new ArrayList<>();

        for (Package p : main.loaded)
            packages.add(p.getName());
        
        for (Category c : Category.values())
            categories.add(c.toString());

        switch (args.length) {
        case 1:
            if (Permission.INSTALL.has(sender))
                completions.add("add");
            if (Permission.REMOVE.has(sender))
                completions.add("remove");
            if (Permission.HELP.has(sender))
                completions.add("help");
            if (Permission.LIST.has(sender))
                completions.add("list");
            if (Permission.UPDATE.has(sender))
                completions.add("update");
            if (Permission.VERSION.has(sender))
                completions.add("version");
            if (Permission.SEARCH.has(sender))
                completions.add("search");
            if (Permission.INFO.has(sender))
                completions.add("info");
            if (Permission.OVERLAY.has(sender))
                completions.add("overlay");
            break;
        case 2:
            if(Permission.SEARCH.has(sender) && (args[0].toLowerCase() == "search" || args[0].toLowerCase() == "s"))
                completions.addAll(categories);
            break;
        }

        return completions;
    }

}
