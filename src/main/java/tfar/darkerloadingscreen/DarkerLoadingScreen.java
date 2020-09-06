package tfar.darkerloadingscreen;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import tfar.darkerloadingscreen.mixin.ConfigTrackerAccessor;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DarkerLoadingScreen.MODID)
public class DarkerLoadingScreen
{
    // Directly reference a log4j logger.

    public static final String MODID = "darkerloadingscreen";

    public DarkerLoadingScreen() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Hooks.CLIENT_SPEC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        //force load the config early
        ConfigTracker configTracker = ConfigTracker.INSTANCE;
        ((ConfigTrackerAccessor)configTracker).getConfigSets().get(ModConfig.Type.CLIENT)
                .stream().filter(modConfig -> modConfig.getModId().equals(MODID)).findFirst()
                .ifPresent(modConfig -> ((ConfigTrackerAccessor)configTracker)
                        .$openConfig(modConfig, FMLPaths.CONFIGDIR.get()));
    }

    private void setup(final ModConfig.ModConfigEvent event) {
        if (event.getConfig().getModId().equals(MODID)) {
            Hooks.parse();
        }
    }
}
