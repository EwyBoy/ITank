package com.ewyboy.itank.common.register;

import com.ewyboy.itank.client.color.ColoredBlock;
import com.ewyboy.itank.client.color.ColoredItem;
import com.ewyboy.itank.common.content.tank.TankBlock;
import com.ewyboy.itank.common.content.tank.TankItem;
import com.ewyboy.itank.common.content.tank.TankTile;
import com.ewyboy.itank.common.states.TankColor;
import com.ewyboy.itank.util.ColorHandler;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import oshi.annotation.concurrent.Immutable;

import java.util.Set;

import static com.ewyboy.itank.ITank.MOD_ID;

public class Register {

    public static void init() {
        BLOCK.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEM.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE.TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final class BLOCK {

        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);

        public static final RegistryObject<TankBlock> TANK = BLOCKS.register("tank", () -> new TankBlock(TankColor.GRAY));
        public static final RegistryObject<TankBlock> TANK_WHITE = BLOCKS.register("tank_white", () -> new TankBlock(TankColor.WHITE));
        public static final RegistryObject<TankBlock> TANK_ORANGE = BLOCKS.register("tank_orange", () -> new TankBlock(TankColor.ORANGE));
        public static final RegistryObject<TankBlock> TANK_MAGENTA = BLOCKS.register("tank_magenta", () -> new TankBlock(TankColor.MAGENTA));
        public static final RegistryObject<TankBlock> TANK_LIGHT_BLUE = BLOCKS.register("tank_light_blue", () -> new TankBlock(TankColor.LIGHT_BLUE));
        public static final RegistryObject<TankBlock> TANK_YELLOW = BLOCKS.register("tank_yellow", () -> new TankBlock(TankColor.YELLOW));
        public static final RegistryObject<TankBlock> TANK_LIME = BLOCKS.register("tank_lime", () -> new TankBlock(TankColor.LIME));
        public static final RegistryObject<TankBlock> TANK_PINK = BLOCKS.register("tank_pink", () -> new TankBlock(TankColor.PINK));
        public static final RegistryObject<TankBlock> TANK_LIGHT_GRAY = BLOCKS.register("tank_light_gray", () -> new TankBlock(TankColor.LIGHT_GRAY));
        public static final RegistryObject<TankBlock> TANK_CYAN = BLOCKS.register("tank_cyan", () -> new TankBlock(TankColor.CYAN));
        public static final RegistryObject<TankBlock> TANK_PURPLE = BLOCKS.register("tank_purple", () -> new TankBlock(TankColor.PURPLE));
        public static final RegistryObject<TankBlock> TANK_BLUE = BLOCKS.register("tank_blue", () -> new TankBlock(TankColor.BLUE));
        public static final RegistryObject<TankBlock> TANK_BROWN = BLOCKS.register("tank_brown", () -> new TankBlock(TankColor.BROWN));
        public static final RegistryObject<TankBlock> TANK_GREEN = BLOCKS.register("tank_green", () -> new TankBlock(TankColor.GREEN));
        public static final RegistryObject<TankBlock> TANK_RED = BLOCKS.register("tank_red", () -> new TankBlock(TankColor.RED));
        public static final RegistryObject<TankBlock> TANK_BLACK = BLOCKS.register("tank_black", () -> new TankBlock(TankColor.BLACK));

        public static final Set<RegistryObject<TankBlock>> TANKS = ImmutableSet.of(TANK,
                TANK_WHITE,
                TANK_ORANGE,
                TANK_MAGENTA,
                TANK_LIGHT_BLUE,
                TANK_YELLOW,
                TANK_LIME,
                TANK_PINK,
                TANK_LIGHT_GRAY,
                TANK_CYAN,
                TANK_PURPLE,
                TANK_BLUE,
                TANK_BROWN,
                TANK_GREEN,
                TANK_RED,
                TANK_BLACK
        );
    }

    public static final class ITEM {

        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

        public static final RegistryObject<TankItem> TANK = ITEMS.register("tank", () -> new TankItem(BLOCK.TANK.get(), new Item.Properties()));
        public static final RegistryObject<TankItem> TANK_WHITE = ITEMS.register("tank_white", () -> new TankItem(BLOCK.TANK_WHITE.get(), new Item.Properties()));
        public static final RegistryObject<TankItem> TANK_ORANGE = ITEMS.register("tank_orange", () -> new TankItem(BLOCK.TANK_ORANGE.get(), new Item.Properties()));
        public static final RegistryObject<TankItem> TANK_MAGENTA = ITEMS.register("tank_magenta", () -> new TankItem(BLOCK.TANK_MAGENTA.get(), new Item.Properties()));
        public static final RegistryObject<TankItem> TANK_LIGHT_BLUE = ITEMS.register("tank_light_blue", () -> new TankItem(BLOCK.TANK_LIGHT_BLUE.get(), new Item.Properties()));
        public static final RegistryObject<TankItem> TANK_YELLOW = ITEMS.register("tank_yellow", () -> new TankItem(BLOCK.TANK_YELLOW.get(), new Item.Properties()));
        public static final RegistryObject<TankItem> TANK_LIME = ITEMS.register("tank_lime", () -> new TankItem(BLOCK.TANK_LIME.get(), new Item.Properties()));
        public static final RegistryObject<TankItem> TANK_PINK = ITEMS.register("tank_pink", () -> new TankItem(BLOCK.TANK_PINK.get(), new Item.Properties()));
        public static final RegistryObject<TankItem> TANK_LIGHT_GRAY = ITEMS.register("tank_light_gray", () -> new TankItem(BLOCK.TANK_LIGHT_GRAY.get(), new Item.Properties()));
        public static final RegistryObject<TankItem> TANK_CYAN = ITEMS.register("tank_cyan", () -> new TankItem(BLOCK.TANK_CYAN.get(), new Item.Properties()));
        public static final RegistryObject<TankItem> TANK_PURPLE = ITEMS.register("tank_purple", () -> new TankItem(BLOCK.TANK_PURPLE.get(), new Item.Properties()));
        public static final RegistryObject<TankItem> TANK_BLUE = ITEMS.register("tank_blue", () -> new TankItem(BLOCK.TANK_BLUE.get(), new Item.Properties()));
        public static final RegistryObject<TankItem> TANK_BROWN = ITEMS.register("tank_brown", () -> new TankItem(BLOCK.TANK_BROWN.get(), new Item.Properties()));
        public static final RegistryObject<TankItem> TANK_GREEN = ITEMS.register("tank_green", () -> new TankItem(BLOCK.TANK_GREEN.get(), new Item.Properties()));
        public static final RegistryObject<TankItem> TANK_RED = ITEMS.register("tank_red", () -> new TankItem(BLOCK.TANK_RED.get(), new Item.Properties()));
        public static final RegistryObject<TankItem> TANK_BLACK = ITEMS.register("tank_black", () -> new TankItem(BLOCK.TANK_BLACK.get(), new Item.Properties()));

    }

    public static final class TILE {
        public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);
        public static final RegistryObject<BlockEntityType<TankTile>> TANK = TILES.register("tank", () ->
                BlockEntityType.Builder.of(
                                TankTile::new,
                                BLOCK.TANK.get(),
                                BLOCK.TANK_WHITE.get(),
                                BLOCK.TANK_ORANGE.get(),
                                BLOCK.TANK_MAGENTA.get(),
                                BLOCK.TANK_LIGHT_BLUE.get(),
                                BLOCK.TANK_YELLOW.get(),
                                BLOCK.TANK_LIME.get(),
                                BLOCK.TANK_PINK.get(),
                                BLOCK.TANK_LIGHT_GRAY.get(),
                                BLOCK.TANK_CYAN.get(),
                                BLOCK.TANK_PURPLE.get(),
                                BLOCK.TANK_BLUE.get(),
                                BLOCK.TANK_BROWN.get(),
                                BLOCK.TANK_GREEN.get(),
                                BLOCK.TANK_RED.get(),
                                BLOCK.TANK_BLACK.get()
                        )
                        .build(null)
        );
    }

    public static final class COLORED_BLOCKS {
        public static final ColoredBlock TANK = new ColoredBlock(BLOCK.TANK.get(), ColorHandler.getIntegerColorFromState(TankColor.GRAY));
        public static final ColoredBlock TANK_WHITE = new ColoredBlock(BLOCK.TANK_WHITE.get(), ColorHandler.getIntegerColorFromState(TankColor.WHITE));
        public static final ColoredBlock TANK_ORANGE = new ColoredBlock(BLOCK.TANK_ORANGE.get(), ColorHandler.getIntegerColorFromState(TankColor.ORANGE));
        public static final ColoredBlock TANK_MAGENTA = new ColoredBlock(BLOCK.TANK_MAGENTA.get(), ColorHandler.getIntegerColorFromState(TankColor.MAGENTA));
        public static final ColoredBlock TANK_LIGHT_BLUE = new ColoredBlock(BLOCK.TANK_LIGHT_BLUE.get(), ColorHandler.getIntegerColorFromState(TankColor.LIGHT_BLUE));
        public static final ColoredBlock TANK_YELLOW = new ColoredBlock(BLOCK.TANK_YELLOW.get(), ColorHandler.getIntegerColorFromState(TankColor.YELLOW));
        public static final ColoredBlock TANK_LIME = new ColoredBlock(BLOCK.TANK_LIME.get(), ColorHandler.getIntegerColorFromState(TankColor.LIME));
        public static final ColoredBlock TANK_PINK = new ColoredBlock(BLOCK.TANK_PINK.get(), ColorHandler.getIntegerColorFromState(TankColor.PINK));
        public static final ColoredBlock TANK_LIGHT_GRAY = new ColoredBlock(BLOCK.TANK_LIGHT_GRAY.get(), ColorHandler.getIntegerColorFromState(TankColor.LIGHT_GRAY));
        public static final ColoredBlock TANK_CYAN = new ColoredBlock(BLOCK.TANK_CYAN.get(), ColorHandler.getIntegerColorFromState(TankColor.CYAN));
        public static final ColoredBlock TANK_PURPLE = new ColoredBlock(BLOCK.TANK_PURPLE.get(), ColorHandler.getIntegerColorFromState(TankColor.PURPLE));
        public static final ColoredBlock TANK_BLUE = new ColoredBlock(BLOCK.TANK_BLUE.get(), ColorHandler.getIntegerColorFromState(TankColor.BLUE));
        public static final ColoredBlock TANK_BROWN = new ColoredBlock(BLOCK.TANK_BROWN.get(), ColorHandler.getIntegerColorFromState(TankColor.BROWN));
        public static final ColoredBlock TANK_GREEN = new ColoredBlock(BLOCK.TANK_GREEN.get(), ColorHandler.getIntegerColorFromState(TankColor.GREEN));
        public static final ColoredBlock TANK_RED = new ColoredBlock(BLOCK.TANK_RED.get(), ColorHandler.getIntegerColorFromState(TankColor.RED));
        public static final ColoredBlock TANK_BLACK = new ColoredBlock(BLOCK.TANK_BLACK.get(), ColorHandler.getIntegerColorFromState(TankColor.BLACK));
    }

    public static final class COLORED_ITEMS {
        public static final ColoredItem TANK = new ColoredItem(ITEM.TANK.get(), ColorHandler.getIntegerColorFromState(TankColor.GRAY));
        public static final ColoredItem TANK_WHITE = new ColoredItem(ITEM.TANK_WHITE.get(), ColorHandler.getIntegerColorFromState(TankColor.WHITE));
        public static final ColoredItem TANK_ORANGE = new ColoredItem(ITEM.TANK_ORANGE.get(), ColorHandler.getIntegerColorFromState(TankColor.ORANGE));
        public static final ColoredItem TANK_MAGENTA = new ColoredItem(ITEM.TANK_MAGENTA.get(), ColorHandler.getIntegerColorFromState(TankColor.MAGENTA));
        public static final ColoredItem TANK_LIGHT_BLUE = new ColoredItem(ITEM.TANK_LIGHT_BLUE.get(), ColorHandler.getIntegerColorFromState(TankColor.LIGHT_BLUE));
        public static final ColoredItem TANK_YELLOW = new ColoredItem(ITEM.TANK_YELLOW.get(), ColorHandler.getIntegerColorFromState(TankColor.YELLOW));
        public static final ColoredItem TANK_LIME = new ColoredItem(ITEM.TANK_LIME.get(), ColorHandler.getIntegerColorFromState(TankColor.LIME));
        public static final ColoredItem TANK_PINK = new ColoredItem(ITEM.TANK_PINK.get(), ColorHandler.getIntegerColorFromState(TankColor.PINK));
        public static final ColoredItem TANK_LIGHT_GRAY = new ColoredItem(ITEM.TANK_LIGHT_GRAY.get(), ColorHandler.getIntegerColorFromState(TankColor.LIGHT_GRAY));
        public static final ColoredItem TANK_CYAN = new ColoredItem(ITEM.TANK_CYAN.get(), ColorHandler.getIntegerColorFromState(TankColor.CYAN));
        public static final ColoredItem TANK_PURPLE = new ColoredItem(ITEM.TANK_PURPLE.get(), ColorHandler.getIntegerColorFromState(TankColor.PURPLE));
        public static final ColoredItem TANK_BLUE = new ColoredItem(ITEM.TANK_BLUE.get(), ColorHandler.getIntegerColorFromState(TankColor.BLUE));
        public static final ColoredItem TANK_BROWN = new ColoredItem(ITEM.TANK_BROWN.get(), ColorHandler.getIntegerColorFromState(TankColor.BROWN));
        public static final ColoredItem TANK_GREEN = new ColoredItem(ITEM.TANK_GREEN.get(), ColorHandler.getIntegerColorFromState(TankColor.GREEN));
        public static final ColoredItem TANK_RED = new ColoredItem(ITEM.TANK_RED.get(), ColorHandler.getIntegerColorFromState(TankColor.RED));
        public static final ColoredItem TANK_BLACK = new ColoredItem(ITEM.TANK_BLACK.get(), ColorHandler.getIntegerColorFromState(TankColor.BLACK));

    }

}
