package powerlessri.bukkit.library.tags;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import powerlessri.bukkit.library.tags.wrapper.NBTWrapper;

/** Use ItemMetaWrapper and it's applyTraits method instead. */
public class TaggedItemBuilder extends NBTWrapper {



    public static TaggedItemBuilder builderOf(ItemStack patternStack) {
        //TODO add traits from patternStack
        TaggedItemBuilder builder = new TaggedItemBuilder();
        return builder;
    }





    private List<Enchantment> enchantments;
    private List<Integer> levels;

    private String displayName;
    private List<String> lore;

    public TaggedItemBuilder() {
        super(new NBTTagCompound());

        this.enchantments = new ArrayList<Enchantment>();
        this.levels = new ArrayList<Integer>();
        this.lore = new ArrayList<String>();
    }






    public void addEnchant(Enchantment enchantment, int level) {
        this.enchantments.add(enchantment);
        this.levels.add(level);
    }

    public void setDisplayName(String name) {
        this.displayName = name;
    }

    public void addLore(String lore) {
        this.lore.add(lore);
    }


    public ItemStack buildItem(Material material) {
        return this.buildItem(material, null);
    }

    public ItemStack buildItem(Material material, String name) {
        NBTTagCompound tag = (NBTTagCompound) this.rootTag.clone();
        ItemStack result = TagHelper.getStackWithTag(new ItemStack(material), tag);

        // Use methods ItemMeta give to add certain vanilla properties
        ItemMeta meta = result.getItemMeta();

        if(this.enchantments != null && this.levels != null) {
            for(int i = 0; i < this.enchantments.size(); i++) {
                meta.addEnchant(this.enchantments.get(i), this.levels.get(i), true);
            }
        }

        meta.setDisplayName(name == null ? this.displayName : name);

        if(this.lore != null) {
            meta.setLore(this.lore);
        }

        result.setItemMeta(meta);

        return result;
    }

}