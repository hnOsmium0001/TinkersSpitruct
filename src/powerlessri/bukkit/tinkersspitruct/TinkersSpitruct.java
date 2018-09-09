package powerlessri.bukkit.tinkersspitruct;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import powerlessri.bukkit.library.effects.ItemGlowingEffect;
import powerlessri.bukkit.library.inventory.InventorySequence;
import powerlessri.bukkit.library.registry.ItemBase;
import powerlessri.bukkit.library.registry.Registry;
import powerlessri.bukkit.library.string.LangMap;
import powerlessri.bukkit.library.tags.CommonTags;
import powerlessri.bukkit.library.tags.TagHelper;
import powerlessri.bukkit.tinkersspitruct.commands.CommandBase;
import powerlessri.bukkit.tinkersspitruct.commands.CommandSpitructDebug;
import powerlessri.bukkit.tinkersspitruct.eastereggs.MainPranker;
import powerlessri.bukkit.tinkersspitruct.events.InventoryClickHandler;
import powerlessri.bukkit.tinkersspitruct.events.WorldInteractionHandler;
import powerlessri.bukkit.tinkersspitruct.inventory.machines.InventoryToolBuilder;

public class TinkersSpitruct extends JavaPlugin { 
    
    /** Reference to the current plugin instance */
    public static TinkersSpitruct plugin;
    
    /** ID for internal usage */
    public static final String PLUGIN_ID = "tinkersspitruct";
    /** A human-readable name for the plugin */
    public static final String PLUGIN_NAME = "Tinker's Spitruct";
    
    
    @FinalField
    public MainPranker pranker;
    
    public Registry<ItemBase> itemRegistry;
    
    public InventoryClickHandler clickHandler;
    public WorldInteractionHandler interactionHandler;
    
    public InventoryToolBuilder toolBuilders;
//    public PositionalInventoryStorage partBuilders;
//    public PositionalInventoryStorage stencilTables;
//    
//    public PositionalInventoryStorage partternChests;
//    public PositionalInventoryStorage partChests;
    
    public ItemGlowingEffect glow;
    
    public LangMap lang;
    
    @Override
    public void onEnable() {
        CommonTags.resetItemTagFixers(PLUGIN_ID);
        
        plugin = this;
        PluginReference.clearPlugins();
        PluginReference.addPlugin(this);
        
        this.pranker = new MainPranker(this);
        
        this.itemRegistry = new Registry<>();
        
        this.glow = ItemGlowingEffect.registerGlow(getLogger());
        
        this.reloadLang("en_US");
        
        registerCommand(new CommandSpitructDebug(this));
        
        this.clickHandler = new InventoryClickHandler(this);
        this.interactionHandler = new WorldInteractionHandler(this);
        registerEvent(this.clickHandler);
        registerEvent(this.interactionHandler);
        
        this.toolBuilders = new InventoryToolBuilder(this);
        
        // Test //
        
        ItemStack metaSource = new ItemStack(Material.STONE);
        
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInt("test", 1);
        tag.setString("owner", "hello");
        
        metaSource = TagHelper.getStackWithTag(metaSource, tag);
        
        ItemMeta meta = metaSource.getItemMeta();
        meta.setDisplayName("test - meta source");
        
        metaTest1 = new ItemStack(Material.STICK);
        metaTest1.setItemMeta(meta);
        
        meta.setLore(new ArrayList<String>() {{
            add("1");
            add("233");
        }});
        
        // Test //
        
        this.pranker.doConsolePranks();
        this.pranker.haha();
    }
    
    @Override
    public void onDisable() {
    }
    
    
    
    private void registerEvent(Listener event) {
        Bukkit.getPluginManager().registerEvents(event, this);
    }
    
    private void registerCommand(CommandBase command) {
        this.getCommand(command.getName()).setExecutor(command);
    }
    
    
    private void reloadLang(String file) {
        this.lang = new LangMap(getLogger(), PLUGIN_ID);
        this.loadLang(file);
    }
    
    private void loadLang(String file) {
        this.lang.load(file);
    }
    
    
    public String translate(String key) {
        return this.lang.get(key);
    }
    
    public String translate(String key, int index) {
        return this.lang.getList(key).get(index);
    }
    
    // Test //
    
    public InventorySequence toolBuilder;
    public ItemStack metaTest1;
    
}
