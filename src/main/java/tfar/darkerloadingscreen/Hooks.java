package tfar.darkerloadingscreen;

import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Hooks {

	private static ForgeConfigSpec.ConfigValue<String> main_background_color;
	private static ForgeConfigSpec.ConfigValue<String> progress_start_color;
	private static ForgeConfigSpec.ConfigValue<String> progress_end_color;

	public static int backColor = 0, progStartColor = 0, progEndColor = 0;

	public Hooks(ForgeConfigSpec.Builder builder) {
		builder.push("general");
		main_background_color = builder.define("main_background_color","#2B2B2B");
		progress_start_color = builder.define("progress_start_color","#FF0000");
		progress_end_color = builder.define("progress_end_color","#00FF00");
		builder.pop();
	}

	public static void parse() {
		backColor = Integer.decode(main_background_color.get()) | 0xff000000;
		progStartColor = Integer.decode(progress_start_color.get()) | 0xff000000;
		progEndColor = Integer.decode(progress_end_color.get()) | 0xff000000;
	}

	public static int getProgressColor(float progress){

		int r1 = (progStartColor >> 16) & 0xff;
		int g1 = (progStartColor >> 8) & 0xff;
		int b1 = progStartColor & 0xff;

		int r2 = (progEndColor >> 16) & 0xff;
		int g2 = (progEndColor >> 8) & 0xff;
		int b2 = progEndColor & 0xff;

		int r = (int) MathHelper.lerp(progress, r1, r2) << 16;
		int g =	(int)MathHelper.lerp(progress, g1, g2) << 8;
		int b =	(int)MathHelper.lerp(progress, b1, b2);

		return 0xff000000| r | g | b;
	}

	public static int getBarBackgroundColor(float progress){

		float a = 1 - progress;

		int r = Math.round(a * 255F) << 16;
		int g = Math.round(a * 255F) << 8;
		int b = Math.round(a * 255F);

		return 0xff000000 | r | g | b;
	}

	public static final Hooks CLIENT;
	public static final ForgeConfigSpec CLIENT_SPEC;

	static {
		final Pair<Hooks, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Hooks::new);
		CLIENT_SPEC = specPair.getRight();
		CLIENT = specPair.getLeft();
	}
}
