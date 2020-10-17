package com.people.loveme;

import com.people.loveme.customeplugin.GiftPlugin;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.plugin.DefaultLocationPlugin;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.ImagePlugin;
import io.rong.imkit.widget.provider.FilePlugin;
import io.rong.imlib.model.Conversation;

/**
 * Created by kxn on 2018/10/26 0026.
 */

public class MyExtensionModule extends DefaultExtensionModule {
    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> pluginModules = super.getPluginModules(conversationType);
        pluginModules.clear();
        if (AppConsts.isVip) {
            pluginModules.add(new ImagePlugin());
            pluginModules.add(new FilePlugin());
        }
        pluginModules.add(new DefaultLocationPlugin());
        pluginModules.add(new GiftPlugin());
        return pluginModules;
    }

}
