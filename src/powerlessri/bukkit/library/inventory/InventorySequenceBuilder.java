package powerlessri.bukkit.library.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

public class InventorySequenceBuilder {
    
    public final String id;
    
    private List<InventoryBuilder> builders;
    
    public InventorySequenceBuilder(String id) {
        this(id, new ArrayList<InventoryBuilder>());
    }
    
    public InventorySequenceBuilder(String id, InventoryBuilder... builders) {
        this(id, new ArrayList<InventoryBuilder>(Arrays.asList(builders)));
    }
    
    public InventorySequenceBuilder(String id, List<InventoryBuilder> builders) {
        this.id = id;
        this.builders = builders;
    }
    
    
    
    public void addInventory(InventoryBuilder builder) {
        this.builders.add(builder);
    }
    
    
    
    public InventorySequence makeInventory() {
        InventorySequence sequence = new InventorySequence();
        
        for(int i = 0; i < this.builders.size(); i++) {
            InventoryBuilder builder = this.builders.get(i);
            sequence.addInventory(builder.id, builder.makeInventory());
        }
        
        return sequence;
    }
    
}