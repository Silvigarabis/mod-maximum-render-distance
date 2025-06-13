package me.silvigarabis.mod_maximum_distance;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModMaximumDistanceModClient implements ClientModInitializer {

   public static final Logger LOGGER = LoggerFactory.getLogger("mod_maximum_distance");

   public static int getMaxViewDistance(){
      return 128;
   }

   @Override
   public void onInitializeClient() {
      // This entrypoint is suitable for setting up client-specific logic, such as rendering.
   }
}
