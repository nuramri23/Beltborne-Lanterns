# Credits

## Original Mod

**Beltborne Lanterns** was created by **0xCoDSnet** (also known as **Shadscure**).

- **GitHub**: [0xCoDSnet](https://github.com/0xCoDSnet) / [Shadscure](https://github.com/Shadscure)
- **Original Repository**: [Beltborne-Lanterns](https://github.com/Shadscure/Beltborne-Lanterns)
- **Modrinth**: [beltborne-lanterns](https://modrinth.com/mod/beltborne-lanterns)
- **CurseForge**: [beltborne-lanterns](https://www.curseforge.com/minecraft/mc-mods/beltborne-lanterns)
- **Supported Versions**: Minecraft 1.21.x
- **Supported Loaders**: Fabric, Quilt, NeoForge

The original mod features:
- Belt-mounted lanterns with physics
- Dynamic lighting integration
- Multi-loader support (Fabric/Quilt/NeoForge)
- Multiplayer synchronization
- Extensive compatibility with modded lanterns

## MC 26.x Port (Fabric)

**Ported to Minecraft 26.1-26.2 by:** **nuramri23**

- **GitHub**: [nuramri23](https://github.com/nuramri23)
- **Modrinth**: [beltborne-lanterns MC 26.x port](https://modrinth.com/mod/beltborne-lanterns-unofficial-mc-26.1-port)
- **Port Version**: 1.2.6
- **Supported Versions**: Minecraft 26.1, 26.1.1, 26.1.2, 26.2
- **Supported Loaders**: Fabric only

### Port Changes

This port includes the following updates and fixes for MC 26.x:

1. **Rendering System**
   - Updated to MC 26.x rendering API (Vulkan backend support)
   - Implemented ItemRenderer-based rendering for proper model/texture display
   - Fixed UV mapping issues for all lantern variants

2. **Inventory Management**
   - Improved toggle-off behavior to find empty slots instead of replacing items
   - Smart slot detection (hotbar → main inventory → drop if full)

3. **Configuration**
   - Updated default position values for better visual placement
   - Config accessible via ModMenu

4. **Compatibility**
   - MC 26.2 compatibility fixes (screen field access using reflection)
   - Single JAR supports all MC 26.x versions (26.1 through 26.2)

5. **Multi-lantern Support**
   - Support for all vanilla lantern variants (lantern, soul_lantern)
   - Support for all copper lantern variants (including oxidized and waxed states)

### Development Tools

- Port developed with assistance from [Kiro IDE](https://kiro.dev)
- Kiro is an AI-powered development environment built on VS Code

## License

Both the original mod and this port are licensed under **Apache License 2.0**.

See [LICENSE](LICENSE) file for full license text.

## Acknowledgments

Special thanks to:
- **0xCoDSnet/Shadscure** for creating the original Beltborne Lanterns mod
- **Fabric community** for maintaining excellent modding tools and documentation
- **LambDynamicLights** developers for dynamic lighting API
- **Cloth Config** and **ModMenu** developers for configuration UI support

---

*This port maintains the spirit and functionality of the original mod while adapting it to work with Minecraft's latest rendering systems.*
