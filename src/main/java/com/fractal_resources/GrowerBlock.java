package com.fractal_resources;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class GrowerBlock extends Block {
	public static final int MAX_AGE = 7;
	public static final IntProperty AGE;
	public static final DirectionProperty FACING;

	public GrowerBlock() {
		super(QuiltBlockSettings.of(Material.AMETHYST).strength(1.0f).hardness(3.0f).nonOpaque().ticksRandomly());
	}

	public boolean isMature(BlockState state) {
		return state.get(AGE) >= MAX_AGE;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random) {
		if (random.nextInt(2) != 0) return;
		if (this.isMature(state)) {
			var parentBlockState = world.getBlockState(pos.subtract(state.get(FACING).getVector())).getBlock().getDefaultState();
			if (parentBlockState.getProperties().contains(FACING)) {
				parentBlockState = parentBlockState.with(FACING, state.get(FACING));
			}
			world.setBlockState(pos, parentBlockState);
			for (var direction : Direction.values()) {
				var dest = pos.add(direction.getVector());
				var destState = this.getDefaultState()
						.with(FACING, direction)
						.with(AGE, 0);
				if (world.isAir(dest)) {
					world.setBlockState(dest, destState);
				}
			}
		} else {
			world.setBlockState(pos, state.with(AGE, state.get(AGE) + 1), 2);
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
		builder.add(AGE);
		super.appendProperties(builder);
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getSide());
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		super.onPlaced(world, pos, state, placer, itemStack);
	}

	static {
		AGE = Properties.AGE_7;
		FACING = Properties.FACING;
	}
}
