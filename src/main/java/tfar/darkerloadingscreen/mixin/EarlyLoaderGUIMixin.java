package tfar.darkerloadingscreen.mixin;

import net.minecraftforge.fml.client.EarlyLoaderGUI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import tfar.darkerloadingscreen.Hooks;

@Mixin(value = EarlyLoaderGUI.class,priority = 1001)
public class EarlyLoaderGUIMixin {

	//red green blue alpha
	@ModifyArgs(method = "renderTick", remap = false, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V"))
	private void setBackgroundColor(Args args) {
		float r = (Hooks.backColor >> 16 & 0xff)/255f;
		float g = (Hooks.backColor >> 8 & 0xff)/255f;
		float b = (Hooks.backColor & 0xff)/255f;
		args.setAll(r,g,b,args.get(3));
	}
}
