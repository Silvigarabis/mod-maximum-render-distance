package me.silvigarabis.mod_maximum_distance;

import net.fabricmc.api.ClientModInitializer;

public class ModMaximumDistanceModClient implements ClientModInitializer {
   public int getMaxViewDistance(){
      return 128;
   }

   @Override
   public void onInitializeClient() {
      // This entrypoint is suitable for setting up client-specific logic, such as rendering.
   }
}
