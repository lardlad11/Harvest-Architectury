package n643064.harvest.fabric;

import n643064.harvest.Harvest;
import net.fabricmc.api.ClientModInitializer;

public class HarvestFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Harvest.init();
    }
}