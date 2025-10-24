package rpgmodadmintools.commands;

import necesse.engine.commands.CmdParameter;
import necesse.engine.commands.CommandLog;
import necesse.engine.commands.ModularChatCommand;
import necesse.engine.commands.PermissionLevel;
import necesse.engine.commands.parameterHandlers.IntParameterHandler;
import necesse.engine.commands.parameterHandlers.PresetStringParameterHandler;
import necesse.engine.commands.parameterHandlers.ServerClientParameterHandler;
import necesse.engine.network.client.Client;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.entity.mobs.PlayerMob;

public class RPGSetPointCN extends ModularChatCommand {

    public RPGSetPointCN() {
        super("RPG设置点数", "为玩家设置技能点", PermissionLevel.ADMIN, true,
                new CmdParameter("player", new ServerClientParameterHandler(true, false), true),
                new CmdParameter("pointType", new PresetStringParameterHandler(
                    "attribute", "属性点",
                    "reset", "重置点",
                    "mastery", "精通点",
                    "class", "职业点"
                ), false),
                new CmdParameter("amount", new IntParameterHandler(), false));
    }

    @Override
    public void runModular(Client client, Server server, ServerClient serverClient, Object[] args, String[] errors, CommandLog commandLog) {
        // 前置检测：检查RPG模组是否加载
        if (!isRPGModLoaded()) {
            commandLog.add("错误: RPG模组未加载，无法执行命令。请确保RPG模组已正确安装并启用。");
            return;
        }

        ServerClient target = (ServerClient) args[0];
        
        if (target == null) {
            if (serverClient == null) {
                commandLog.add("错误: 未指定玩家且无客户端上下文可用。");
                return;
            }
            target = serverClient;
        }

        String pointType = (String) args[1];
        int amount = (Integer) args[2];
        
        PlayerMob player = target.playerMob;
        
        // 使用反射来访问RPG模组的数据
        try {
            Class<?> playerDataListClass = Class.forName("rpgclasses.data.PlayerDataList");
            Class<?> playerDataClass = Class.forName("rpgclasses.data.PlayerData");
            
            // 获取PlayerDataList.getPlayerData方法
            java.lang.reflect.Method getPlayerDataMethod = playerDataListClass.getMethod("getPlayerData", PlayerMob.class);
            Object playerData = getPlayerDataMethod.invoke(null, player);
            
            if (playerData == null) {
                commandLog.add("错误: 无法获取玩家数据，请确保RPG模组已加载");
                return;
            }

            String resultMessage = "";
            
            // 处理中文和英文参数类型
            switch (pointType) {
                case "attribute":
                case "属性点":
                    // 通过增加经验来间接增加属性点
                    java.lang.reflect.Method getLevelMethod = playerDataClass.getMethod("getLevel");
                    java.lang.reflect.Method getExpMethod = playerDataClass.getMethod("getExp");
                    java.lang.reflect.Method getExpRequiredForLevelMethod = playerDataClass.getMethod("getExpRequiredForLevel", int.class);
                    java.lang.reflect.Method modExpSendPacketMethod = playerDataClass.getMethod("modExpSendPacket", ServerClient.class, int.class);
                    
                    int currentLevel = (int) getLevelMethod.invoke(playerData);
                    int currentExp = (int) getExpMethod.invoke(playerData);
                    int expNeeded = (int) getExpRequiredForLevelMethod.invoke(playerData, currentLevel + amount) - currentExp;
                    
                    modExpSendPacketMethod.invoke(playerData, target, expNeeded);
                    resultMessage = String.format("%s 的等级增加了 %d 级，从而增加了属性点",
                        player.getDisplayName(), amount);
                    break;
                    
                case "reset":
                case "重置点":
                    // 直接增加重置点
                    java.lang.reflect.Method modResetsSendPacketMethod = playerDataClass.getMethod("modResetsSendPacket", ServerClient.class, int.class);
                    modResetsSendPacketMethod.invoke(playerData, target, amount);
                    resultMessage = String.format("%s 的重置点增加了 %d 点",
                        player.getDisplayName(), amount);
                    break;
                    
                case "mastery":
                case "精通点":
                    // 增加精通点（通过增加等级来间接增加精通点）
                    java.lang.reflect.Method getLevelMethod2 = playerDataClass.getMethod("getLevel");
                    java.lang.reflect.Method getExpMethod2 = playerDataClass.getMethod("getExp");
                    java.lang.reflect.Method getExpRequiredForLevelMethod2 = playerDataClass.getMethod("getExpRequiredForLevel", int.class);
                    java.lang.reflect.Method modExpSendPacketMethod2 = playerDataClass.getMethod("modExpSendPacket", ServerClient.class, int.class);
                    
                    int currentLevel2 = (int) getLevelMethod2.invoke(playerData);
                    int currentExp2 = (int) getExpMethod2.invoke(playerData);
                    int expNeeded2 = (int) getExpRequiredForLevelMethod2.invoke(playerData, currentLevel2 + amount * 20) - currentExp2;
                    
                    modExpSendPacketMethod2.invoke(playerData, target, expNeeded2);
                    resultMessage = String.format("%s 的等级增加了 %d 级，从而增加了精通点",
                        player.getDisplayName(), amount * 20);
                    break;
                    
                case "class":
                case "职业点":
                    // 增加职业点（通过增加等级来间接增加职业点）
                    java.lang.reflect.Method getLevelMethod3 = playerDataClass.getMethod("getLevel");
                    java.lang.reflect.Method getExpMethod3 = playerDataClass.getMethod("getExp");
                    java.lang.reflect.Method getExpRequiredForLevelMethod3 = playerDataClass.getMethod("getExpRequiredForLevel", int.class);
                    java.lang.reflect.Method modExpSendPacketMethod3 = playerDataClass.getMethod("modExpSendPacket", ServerClient.class, int.class);
                    
                    int currentLevel3 = (int) getLevelMethod3.invoke(playerData);
                    int currentExp3 = (int) getExpMethod3.invoke(playerData);
                    int expNeeded3 = (int) getExpRequiredForLevelMethod3.invoke(playerData, currentLevel3 + amount) - currentExp3;
                    
                    modExpSendPacketMethod3.invoke(playerData, target, expNeeded3);
                    resultMessage = String.format("%s 的等级增加了 %d 级，从而增加了职业点",
                        player.getDisplayName(), amount);
                    break;
                    
                default:
                    commandLog.add("错误: 无效的技能点类型。可用类型: attribute/属性点, reset/重置点, mastery/精通点, class/职业点");
                    return;
            }
            
            commandLog.add(resultMessage);
            
        } catch (ClassNotFoundException e) {
            commandLog.add("错误: RPG模组未加载，无法执行命令");
        } catch (Exception e) {
            commandLog.add("错误: 执行命令时发生异常: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 检测RPG模组是否已加载
     * @return true如果RPG模组已加载，false否则
     */
    private boolean isRPGModLoaded() {
        try {
            // 尝试加载RPG模组的核心类
            Class.forName("rpgclasses.data.PlayerDataList");
            Class.forName("rpgclasses.data.PlayerData");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}