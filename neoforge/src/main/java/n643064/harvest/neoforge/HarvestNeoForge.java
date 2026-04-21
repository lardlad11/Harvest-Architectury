package n643064.harvest.neoforge;

import n643064.harvest.Harvest;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod("harvest")
public class HarvestNeoForge {
    public HarvestNeoForge(IEventBus modEventBus) {
        Harvest.init();
    }
}