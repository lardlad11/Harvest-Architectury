package n643064.harvest;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class Harvest {
    public static void init() {
        // POST = after vanilla has processed the click (matches your old TAIL mixin)
        ClientRawInputEvent.MOUSE_CLICKED_POST.register((client, button, action, mods) -> {
            if (client.screen == null
                    && client.player != null
                    && !client.player.isShiftKeyDown()
                    && button == 1        // right click
                    && action == 1) {     // press
                harvest(client);
            }
            return EventResult.pass();
        });
    }

    private static void harvest(Minecraft client) {
        HitResult targetHit = client.hitResult;
        if (targetHit != null && targetHit.getType() == HitResult.Type.BLOCK) {
            assert client.level != null;
            assert client.gameMode != null;

            final Direction side = ((BlockHitResult) targetHit).getDirection();
            final BlockPos pos = ((BlockHitResult) targetHit).getBlockPos();
            final Block block = client.level.getBlockState(pos).getBlock();

            if (block instanceof final CropBlock cBlock) {
                if (cBlock.isMaxAge(client.level.getBlockState(pos))) {
                    client.gameMode.startDestroyBlock(pos, side);
                    client.player.swing(InteractionHand.MAIN_HAND);
                }
            } else if (block instanceof NetherWartBlock) {
                if (client.level.getBlockState(pos).getValue(NetherWartBlock.AGE) == 3) {
                    client.gameMode.startDestroyBlock(pos, side);
                    client.player.swing(InteractionHand.MAIN_HAND);
                }
            }
        }
    }
}