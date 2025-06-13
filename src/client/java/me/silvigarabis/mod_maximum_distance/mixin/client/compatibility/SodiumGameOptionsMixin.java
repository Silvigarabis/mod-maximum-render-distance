package me.silvigarabis.mod_maximum_distance.mixin.client.compatibility;

import me.jellysquid.mods.sodium.client.gui.SodiumGameOptionPages;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.silvigarabis.mod_maximum_distance.ModMaximumDistanceModClient;

@Mixin(SodiumGameOptionPages.class)
public abstract class SodiumGameOptionsMixin {
   private static final Logger LOGGER = LoggerFactory.getLogger("SodiumGameOptionsMixin");

   @ModifyArgs(
      method = "lambda$general$0",
      require = 0,
      at = @At(
         value = "INVOKE",
         target = "Lme/jellysquid/mods/sodium/client/gui/options/control/SliderControl;<init>(Lme/jellysquid/mods/sodium/client/gui/options/Option;IIILme/jellysquid/mods/sodium/client/gui/options/control/ControlValueFormatter;)V",
         ordinal = 0
      ),
      remap = false
   )
   private static void modifyMaximumViewDistance(Args args){
      LOGGER.info("found old sodium, inject it to modify view distance in sodium's game options");

      int maxViewDistance = ModMaximumDistanceModClient.getMaxViewDistance();

      int minViewDistance = (int)args.get(1);
      int originMaxViewDistance = (int)args.get(2);
      int interval = (int)args.get(3);
      
      int normalisedMaxViewDistance = maxViewDistance
         + (maxViewDistance - minViewDistance) % interval;
      
      args.set(2, normalisedMaxViewDistance);
   }
}
