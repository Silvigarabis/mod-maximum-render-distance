package me.silvigarabis.mod_maximum_distance.mixin.client;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import org.objectweb.asm.Opcodes;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import me.silvigarabis.mod_maximum_distance.ModMaximumDistanceModClient;

@Mixin(GameOptions.class)
public abstract class ModMaximumDistanceMixin {
   private static final Logger LOGGER = LoggerFactory.getLogger("ModMaxiumDistanceMixin");

   private static SimpleOption<Integer> createModifiedViewDistanceOption(MinecraftClient client){
      int defaultValue;
      if (client.is64Bit()){
         defaultValue = 12;
      } else {
         defaultValue = 8;
      }
      
      return new SimpleOption<Integer>(
         "options.renderDistance",
         SimpleOption.emptyTooltip(),
         (optionText, value) -> GameOptions.getGenericValueText(
            optionText,
            Text.translatable(
               "options.chunks",
               new Object[]{value}
            )
         ),
         new SimpleOption.ValidatingIntSliderCallbacks(2, ModMaximumDistanceModClient.getMaxViewDistance()),
         defaultValue,
         value -> MinecraftClient.getInstance().worldRenderer.scheduleTerrainUpdate()
      );
   }

   @Shadow @Final @Mutable
   private SimpleOption<Integer> viewDistance;

   // 32bit system max view distance (maybe)
   @Shadow @Final @Mutable
   public static int field_32154;

   // 64bit system max view distance (maybe)
   @Shadow @Final @Mutable
   public static int field_32155;

   @Inject(
      method = "<init>",
      at = @At(
         value = "FIELD",
         target = "Lnet/minecraft/client/option/GameOptions;viewDistance:Lnet/minecraft/client/option/SimpleOption;",
         opcode = Opcodes.PUTFIELD,
         shift = At.Shift.AFTER
      )
   )
   private void modifyViewDistanceOption(MinecraftClient client, File optionsFile, CallbackInfo info){

      this.viewDistance = createModifiedViewDistanceOption(client);
      LOGGER.info("FIELD viewDistance modified");

      int maxViewDistance = ModMaximumDistanceModClient.getMaxViewDistance();
      field_32154 = maxViewDistance;
      field_32155 = maxViewDistance;
      LOGGER.info("constant maxViewDistance modified");
   }
}
