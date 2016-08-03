package com.ewyboy.itank.proxy;

import com.ewyboy.itank.common.loaders.BlockLoader;

public class ClientProxy extends CommonProxy {

    @Override
    public void loadModels() {
        BlockLoader.initModels();
    }

    @Override
    public void loadItemModels() {
        BlockLoader.initItemModels();
    }
}
