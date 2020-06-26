package tfar.darkerloadingscreen.mixin;

import net.minecraft.client.gui.ResourceLoadProgressGui;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.darkerloadingscreen.Hooks;

import static net.minecraft.client.gui.AbstractGui.fill;

@Mixin(value = ResourceLoadProgressGui.class,priority = 1001)
public class ResourceLoadProgressGuiMixin {

	@Shadow private float progress;

	@Inject(method = "renderProgressBar",at = @At("HEAD"),cancellable = true)
	private void customBar(int minX, int minY, int maxX, int maxY, float p_228181_5_, CallbackInfo ci){
		ci.cancel();
		int length = MathHelper.ceil((maxX - minX - 1) * this.progress);
		fill(minX - 1, minY - 1, maxX + 1, maxY + 1,Hooks.getBarBackgroundColor(progress));
		fill(minX, minY, maxX, maxY, Hooks.backColor);
		fill(minX + 1, minY + 1, minX + length, maxY - 1, Hooks.getProgressColor(progress));
	}

	//white by default
	@ModifyConstant(method = "render",constant = @Constant(intValue = 16777215))
	private int backgroundColor(int old){
		return Hooks.backColor & 0x00ffffff;//remove alpha
	}

	@ModifyArg(method = "render",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/gui/ResourceLoadProgressGui;fill(IIIII)V",ordinal = 1),index = 4)
	private int backgroundColor1(int old){
		return Hooks.backColor;
	}

	@ModifyArg(method = "render",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/gui/ResourceLoadProgressGui;fill(IIIII)V",ordinal = 2),index = 4)
	private int backgroundColor2(int old){
		return Hooks.backColor;
	}
}
