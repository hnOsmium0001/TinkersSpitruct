package powerlessri.bukkit.tinkersspitruct;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import powerlessri.bukkit.tinkersspitruct.commands.CommandSpitructDebug;
import powerlessri.bukkit.tinkersspitruct.eastereggs.MainPranker;
import powerlessri.bukkit.tinkersspitruct.library.annotations.FinalField;
import powerlessri.bukkit.tinkersspitruct.library.event.InventoryEvents;
import powerlessri.bukkit.tinkersspitruct.library.event.calls.EventCalls;
import powerlessri.bukkit.tinkersspitruct.library.inventory.InventoryBuilder;
import powerlessri.bukkit.tinkersspitruct.library.lang.LangMap;
import powerlessri.bukkit.tinkersspitruct.library.tags.CommonItemTags;
import powerlessri.bukkit.tinkersspitruct.library.tags.PluginTagHelper;
import powerlessri.bukkit.tinkersspitruct.library.tags.TaggedItemBuilder;

public class TinkersSpitruct extends JavaPlugin { 
    
    @FinalField
    public ConfigurationSection config;
    @FinalField
    public MainPranker pranker;
    @FinalField
    public Map<String, EventCalls> eventCalls;
    
    public LangMap lang;
    
    @Override
    public void onEnable() {
        Reference.clearPlugins();
        Reference.addPlugin(this);
        
        this.config = getConfig();
        this.pranker = new MainPranker();
        this.eventCalls = new HashMap<String, EventCalls>();
        
        this.reloadLang("en_US");
        
        this.getCommand("spitruct").setExecutor(new CommandSpitructDebug());
        
        
        Runnable testCall = () -> {
            getLogger().info("item got clicked!");
        };
        int callId = addEventCall("test", testCall);
        
        InventoryBuilder builder = InventoryBuilder.createBuilder(3, "test inventory");
        ItemStack stack = new ItemStack(Material.DIAMOND);
        net.minecraft.server.v1_12_R1.ItemStack nms = CraftItemStack.asNMSCopy(stack);
        
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString(CommonItemTags.CLICK_EVENT_CATEGORY.getKey(), "test");
        tag.setInt(CommonItemTags.CLICK_EVENT_ID.getKey(), callId);
        
        NBTTagCompound rootTag = new NBTTagCompound();
        rootTag.set(Reference.PLUGIN_ID, tag);
        nms.setTag(rootTag);
        
        stack = CraftItemStack.asCraftMirror(nms);
        
//        TaggedItemBuilder taggedItems = TaggedItemBuilder.builderOf(null);
//        
//        taggedItems.addTagCompound(Reference.PLUGIN_ID);
//        taggedItems.cd(Reference.PLUGIN_ID);
//        
//        taggedItems.addDefaultString(CommonItemTags.CLICK_EVENT_CATEGORY.getKey(), "test");
//        taggedItems.addDefaultInt(CommonItemTags.CLICK_EVENT_ID.getKey(), callId);
        
//        builder.addImmovableSlot(taggedItems.buildItem(Material.DIAMOND), 0);
//        builder.addImmovableSlot(0, new ItemStack(Material.DIAMOND));
        
        builder.addImmovableSlot(0, stack);
        testInventory = builder.makeInventory();
        
        
        getLogger().info("Plugin " + Reference.PLUGIN_ID + " loaded.");
        
        this.pranker.doConsolePranks();
    }
    
    @Override
    public void onDisable() {
    }
    
    
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if(PluginTagHelper.hasPluginTag(event.getCurrentItem())) {
            NBTTagCompound tag = PluginTagHelper.getPluginTag(event.getCurrentItem());
            
            InventoryEvents.processStackClickEvent(tag);
            
            if(InventoryEvents.shouldCancelImmovable(tag)) {
                event.setCancelled(true);
            }
        }
    }
    
    
    public int addEventCall(String category, Runnable call) {
        if(!this.eventCalls.containsKey(category)) {
            this.eventCalls.put(category, new EventCalls());
        }
        
        return this.eventCalls.get(category).registerCall(call);
    }
    
    public EventCalls getEventCalls(String category) {
        if(this.eventCalls.containsKey(category)) {
            return this.eventCalls.get(category);
        }
        
        return this.eventCalls.entrySet().iterator().next().getValue();
    }
    
    public void reloadLang(String file) {
        this.lang = new LangMap();
        this.loadLang(file);
    }
    
    public void loadLang(String file) {
        this.lang.load(file);
    }
    
    
    // Test //
    
    public Inventory testInventory;
    
}
