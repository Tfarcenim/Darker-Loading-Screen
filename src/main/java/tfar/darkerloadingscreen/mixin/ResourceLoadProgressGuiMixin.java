package tfar.darkerloadingscreen.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.ResourceLoadProgressGui;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import tfar.darkerloadingscreen.Hooks;


@Mixin(value = ResourceLoadProgressGui.class,priority = 1001)
public class ResourceLoadProgressGuiMixin {

	@Shadow private float progress;

	@Inject(method = "func_238629_a_",at = @At("HEAD"),cancellable = true)
	private void customBar(MatrixStack stack,int minX, int minY, int maxX, int maxY, float p_228181_5_, CallbackInfo ci){
		ci.cancel();
		int length = MathHelper.ceil((maxX - minX - 1) * this.progress);
		AbstractGui.func_238467_a_(stack,minX - 1, minY - 1, maxX + 1, maxY + 1,Hooks.getBarBackgroundColor(progress));
		AbstractGui.func_238467_a_(stack,minX, minY, maxX, maxY, Hooks.backColor);
		AbstractGui.func_238467_a_(stack,minX + 1, minY + 1, minX + length, maxY - 1, Hooks.getProgressColor(progress));
	}

	//white by default
	@ModifyArg(method = "func_230430_a_",remap = false,
					at = @At(value = "INVOKE",
									target = "Lnet/minecraft/client/gui/ResourceLoadProgressGui;func_238467_a_(Lcom/mojang/blaze3d/matrix/MatrixStack;IIIII)V"),index = 5)
	private int backgroundColor(int old){
		return Hooks.backColor & 0x00ffffff;//remove alpha
	}

	@ModifyArgs(method = "func_230430_a_",at = @At(value = "INVOKE",target = "Lcom/mojang/blaze3d/systems/RenderSystem;color4f(FFFF)V"))
	private void tint(Args args){
		args.set(1,0f);
		args.set(2,0f);
	}
}
