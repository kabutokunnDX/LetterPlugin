package kbtdx.letterplugin;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class LetterPlugin extends JavaPlugin implements CommandExecutor {

    public static JavaPlugin plugin;
    public static String pluginTitle;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;
        pluginTitle = "§7§l[§6§lLetter§7§l]§f§r";
        getCommand("letter").setExecutor(this);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("letter.use")){
            sender.sendMessage(pluginTitle+"§c権限がありません!!");
            return true;
        }
        if (!(sender instanceof Player)){
            sender.sendMessage(pluginTitle+"§cコンソールからは実行できません!!");
            return true;
        }
        else {
            if (args.length == 0){
                sender.sendMessage(pluginTitle+"/letter ぶんしょう1 ぶんしょう2 ぶんしょう3...");
                return true;
            }else {
                Player p = (Player) sender;
                if (p.getInventory().getItemInMainHand().getType() != Material.AIR){
                    sender.sendMessage(pluginTitle+"§e手に何も持たないで実行してください!");
                }else {
                    Configuration con = plugin.getConfig();
                    int i;
                    ItemStack letter = new ItemStack(Material.valueOf(con.getString("letter.LetterMaterial")));
                    ItemMeta lettermeta = letter.getItemMeta();
                    List<String> lore = new ArrayList<>();
                    lore.add("§2========================");
                    for (i = 0;i < args.length; i++){
                        lore.add("§e" + args[i]);
                    }
                    lore.add("§2========================");
                    lore.add("§eby" + sender.getName());
                    lettermeta.setDisplayName("§e§lLetter");
                    lettermeta.setLore(lore);
                    lettermeta.setCustomModelData(con.getInt("letter.CustomModelData"));
                    letter.setItemMeta(lettermeta);
                    p.getInventory().setItemInMainHand(letter);
                }
            }
        }
        return true;
    }
}
