package powerlessri.bukkit.tinkersspitruct.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class CommandBranchedBase extends CommandBase {
    
    private final Map<String, BiConsumer<CommandSender, String[]>> options;
    
    public CommandBranchedBase() {
        options = new HashMap<String, BiConsumer<CommandSender, String[]>>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return false;
    }
    
    
    
    protected void addOption(String label, BiConsumer<CommandSender, String[]> lambd) {
        if(!this.options.containsKey(label)) {
            this.options.put(label, lambd);
        }
    }

}