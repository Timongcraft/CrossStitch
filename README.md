# CrossStitch

CrossStitch is a Minecraft mod designed to improve Minecraft proxy compatibility with other Minecraft mods.
This mod is supported by the [Velocity](https://papermc.io/software/velocity) team and is developed against Velocity
and Fabric, but the principles used are intended to be generic enough to apply to any Minecraft proxy and to
any Minecraft modding platform. (Once we have modern Forge support in Velocity, for instance, we'll have a version
for Forge, for instance.)

## Installation

Download the mod from [Modrinth](https://modrinth.com/mod/crossstitch), [CurseForge](https://www.curseforge.com/minecraft/mc-mods/crossstitch) or
the releases section here.

CrossStitch needs to be installed on the Fabric server itself - Velocity supports CrossStitch as
of Velocity 1.1.2.

Once installed, make sure to only use Velocity to connect to your server, as CrossStitch isn't available
client-side - it's designed to work only with Velocity.

## Additional Components

To support Velocity's modern player information forwarding, you need to install and configure [FabricProxy-Lite](https://modrinth.com/mod/fabricproxy-lite)

## Troubleshooting

### Too many known packs
If you encounter the following issue on Velocity:
```
io.netty.handler.codec.CorruptedFrameException: Error decoding class com.velocitypowered.proxy.protocol.packet.config.KnownPacksPacket
[...]
Caused by: com.velocitypowered.proxy.util.except.QuietDecoderException: too many known packs
```

You might want to raise the known packs limit using the [`velocity.max-known-packs`](https://docs.papermc.io/velocity/reference/system-properties/#velocitymax-known-packs) property,
see the Velocity [docs](https://docs.papermc.io/velocity/reference/system-properties/#how-they-work) for details.

## Why?

Mojang has been increasing the opportunities that mod developers have to add new content to the game.
Unfortunately, some of those improvements do conflict with traditional Minecraft proxies. CrossStitch
is our attempt to improve compatibility.

## Features

* Allows custom Brigadier argument types to be used
  * Previously, this would have not been possible, _or_ we would have had to write an argument serializer to
    cover every mod ever, which is not sustainable. CrossStitch implements a generic approach that will work
    with every mod and in theory every proxy as well.
* More as the need arises.
