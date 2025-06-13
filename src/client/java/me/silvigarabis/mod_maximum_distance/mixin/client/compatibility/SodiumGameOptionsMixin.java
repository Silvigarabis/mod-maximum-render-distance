package me.silvigarabis.mod_maximum_distance.mixin.client.compatibility;

import net.caffeinemc.mods.sodium.client.gui.SodiumGameOptionPages;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import me.silvigarabis.mod_maximum_distance.ModMaximumDistanceModClient;
import static me.silvigarabis.mod_maximum_distance.ModMaximumDistanceModClient.LOGGER;

@Mixin(SodiumGameOptionPages.class)
public abstract class SodiumGameOptionsMixin {

   @ModifyArgs(
      method = "lambda$general$0",
      require = 0,
      at = @At(
         value = "INVOKE",
         target = "Lnet/caffeinemc/mods/sodium/client/gui/options/control/SliderControl;<init>(Lnet/caffeinemc/mods/sodium/client/gui/options/Option;IIILnet/caffeinemc/mods/sodium/client/gui/options/control/ControlValueFormatter;)V",
         ordinal = 0
      ),
      remap = false
   )
   private static void modifyMaximumViewDistance(Args args){
      LOGGER.info("found Sodium, inject it to modify view distance in Sodium's game options");

      int maxViewDistance = ModMaximumDistanceModClient.getMaxViewDistance();

      int minViewDistance = (int)args.get(1);
//      int originMaxViewDistance = (int)args.get(2);
      int interval = (int)args.get(3);
      
      int normalisedMaxViewDistance = maxViewDistance
         + (maxViewDistance - minViewDistance) % interval;
      
      args.set(2, normalisedMaxViewDistance);
   }
}
