package io.github.crucible.fixworks.chadmc.enderio;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.IncompatibleClass;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "EnderIO")
@ValidatorClass("crazypants.enderio.enchantment.EnchantmentSoulBound")
@IncompatibleClass("com.gamerforea.enderio.EventConfig")
public class EnderIOFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
