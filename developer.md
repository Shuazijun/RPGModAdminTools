# ExampleMod 开发文档

## 项目概述

这是一个为 Necesse 游戏开发的示例模组项目，展示了如何创建各种游戏内容，包括物品、生物、武器、投射物等。

## 开发环境

### 系统环境
- **操作系统**: Windows 11
- **Java版本**: Java 17 (要求)
- **构建工具**: Gradle 8.14.3
- **项目类型**: Necesse 游戏模组

### 项目结构
```
ExampleMod/
├── build.gradle          # 项目构建配置
├── settings.gradle       # 项目设置
├── gradlew               # Linux/Mac Gradle包装器
├── gradlew.bat           # Windows Gradle包装器
├── README.md             # 项目说明
├── src/
│   └── main/
│       ├── java/
│       │   └── examplemod/
│       │       ├── ExampleMod.java          # 主模组类
│       │       └── examples/                # 示例代码目录
│       │           ├── ExampleBuff.java     # 增益效果示例
│       │           ├── ExampleChatCommand.java # 聊天命令示例
│       │           ├── ExampleConstructorPatch.java # 构造器补丁示例
│       │           ├── ExampleMaterialItem.java # 材料物品示例
│       │           ├── ExampleMethodPatch.java # 方法补丁示例
│       │           ├── ExampleMob.java      # 生物示例
│       │           ├── ExampleObject.java   # 对象示例
│       │           ├── ExamplePacket.java   # 网络包示例
│       │           ├── ExampleProjectile.java # 投射物示例
│       │           ├── ExampleProjectileWeapon.java # 投射武器示例
│       │           ├── ExampleSwordItem.java # 剑类武器示例
│       │           └── ExampleTile.java     # 图块示例
│       └── resources/
│           ├── preview.png                  # 模组预览图
│           ├── buffs/                       # 增益效果图片
│           ├── items/                       # 物品图片
│           ├── locale/                      # 本地化文件
│           ├── mobs/                        # 生物图片
│           ├── objects/                     # 对象图片
│           ├── player/                      # 玩家相关资源
│           ├── projectiles/                 # 投射物图片
│           └── tiles/                       # 图块图片
```

### 模组配置信息
- **模组ID**: `ixovo.examplemod`
- **模组名称**: `Example Mod`
- **模组版本**: `1.0`
- **目标游戏版本**: `1.0.1`
- **模组描述**: `Necesse Example Mod`
- **作者**: `IXOVO`
- **客户端模式**: `false` (需要服务器支持)

## Gradle 构建指令

### 基础构建指令
```bash
# 编译项目
./gradlew build

# 清理构建文件
./gradlew clean

# 编译Java类
./gradlew compileJava

# 运行测试
./gradlew test
```

### Necesse 模组专用指令

#### 构建相关
```bash
# 构建模组JAR文件
./gradlew buildModJar

# 创建模组信息文件
./gradlew createModInfoFile

# 创建Steam AppID文件
./gradlew createAppID
```

#### 运行和测试
```bash
# 运行客户端（带当前模组）
./gradlew runClient

# 运行开发客户端（带调试模式）
./gradlew runDevClient

# 运行服务器（带当前模组）
./gradlew runServer
```

#### 任务分组
所有Necesse相关任务都在 `necesse` 任务组下，可以通过以下命令查看：
```bash
./gradlew tasks --group necesse
```

### 常用开发工作流

#### 1. 开发构建流程
```bash
# 1. 清理并重新构建
./gradlew clean buildModJar

# 2. 运行客户端测试
./gradlew runClient
```

#### 2. 服务器测试流程
```bash
# 1. 构建模组
./gradlew buildModJar

# 2. 运行服务器
./gradlew runServer
```

#### 3. 开发调试流程
```bash
# 使用开发模式运行客户端
./gradlew runDevClient
```

## 开发注意事项

### 环境要求
1. **Java 17**: 项目要求Java 17或更高版本
2. **Necesse游戏**: 需要安装Necesse游戏到指定目录（当前配置为 `F:\SteamLibrary\steamapps\common\Necesse`）
3. **Gradle**: 项目使用Gradle包装器，无需单独安装Gradle

### 构建输出
- **JAR文件位置**: `build/jar/`
- **JAR文件命名**: `ExampleMod-1.0.1-1.0.jar`
- **编译输出**: `build/mod/`

### 模组开发要点
1. **模组ID**: 只能使用小写字母、数字和点号
2. **版本格式**: 必须遵循 `xx.xx...` 格式
3. **客户端模式**: 设置为 `false` 表示需要服务器支持
4. **依赖管理**: 可以通过 `modDependencies` 和 `modOptionalDependencies` 配置依赖关系

## 故障排除

### 常见问题
1. **找不到游戏目录**: 检查 `build.gradle` 中的 `gameDirectory` 配置
2. **Java版本不匹配**: 确保使用Java 17或更高版本
3. **构建失败**: 运行 `./gradlew clean` 后重新构建

### 环境变量
- **JAVA_HOME**: 需要正确设置Java安装路径
- **GRADLE_OPTS**: 可用于配置Gradle运行参数

## 参考资料
- [Necesse Modding Wiki](https://necessewiki.com/Modding)
- 项目中的示例代码文件
- build.gradle 中的详细配置说明

---

*最后更新: 2025-10-21*