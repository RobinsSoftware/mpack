package ca.robinssoftware.mpack;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public enum Permission {

    COMMAND("mpack.package"), HELP("mpack.help"), SEARCH("mpack.search"), INSTALL("mpack.install"),
    UPDATE("mpack.update"), REMOVE("mpack.remove"), VERSION("mpack.version"), INFO("mpack.info"),
    OVERLAY("mpack.overlay"), LIST("mpack.list"), SYNC("mpack.sync");

    final String label;

    Permission(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public boolean has(CommandSender sender) {
        return sender.isOp() || sender instanceof ConsoleCommandSender || sender.hasPermission(label);
    }

}
