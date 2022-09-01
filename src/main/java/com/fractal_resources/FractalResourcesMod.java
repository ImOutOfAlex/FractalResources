package com.fractal_resources;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.content.registry.api.ItemContentRegistries;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FractalResourcesMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Fractal Resources");

	public static final Block GROWING_BLOCK = new GrowerBlock();
	public static final Item FRACTAL_GROWER = new GrowerItem(GROWING_BLOCK, new QuiltItemSettings().group(ItemGroup.FOOD).maxCount(64));

	@Override
	public void onInitialize(ModContainer mod) {
		Registry.register(Registry.BLOCK, new Identifier("fractal_resources", "grower"), GROWING_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("fractal_resources", "grower"), FRACTAL_GROWER);
		ItemContentRegistries.COMPOST_CHANCE.put(FRACTAL_GROWER, 0.3f);
		LOGGER.info("Added item.");
	}
}
