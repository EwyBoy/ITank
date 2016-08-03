package com.ewyboy.ewysstorage.proxy;

import com.ewyboy.ewysstorage.common.loaders.BlockLoader;

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
