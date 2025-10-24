package rpgmodadmintools;

import rpgmodadmintools.commands.RPGASetPoint;
import rpgmodadmintools.commands.RPGSetPointCN;
import necesse.engine.commands.CommandsManager;
import necesse.engine.modLoader.annotations.ModEntry;

@ModEntry
public class RPGModAdminTools {

    public void init() {
        System.out.println("RPGModAdminTools 模组已加载");
    }

    public void postInit() {
        // Register RPG set point commands (English and Chinese)
        CommandsManager.registerServerCommand(new RPGASetPoint());
        CommandsManager.registerServerCommand(new RPGSetPointCN());
    }

}