<p align="center">
  <img src="https://cdn.modrinth.com/data/NUFDZAKS/4c2ae7ef695e568902814ab197689b922e530236_96.webp" width="128" height="128" alt="Beltborne Lanterns icon">
</p>

<p align="center" style="display:flex;justify-content:center;gap:8px;margin:6px 0;">
  <a href="https://modrinth.com/project/beltborne-lanterns">
    <img alt="Modrinth" height="48" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy-minimal/available/modrinth_vector.svg">
  </a>&nbsp;
  <a href="https://www.curseforge.com/minecraft/mc-mods/beltborne-lanterns">
    <img alt="CurseForge" height="48" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy-minimal/available/curseforge_vector.svg">
  </a>&nbsp;
  <a href="https://discord.gg/9JRb3JMAD3">
    <img alt="Discord" height="48" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy-minimal/social/discord-plural_vector.svg">
  </a>&nbsp;
  <a href="https://github.com/Shadscure/Beltborne-Lanterns">
    <img alt="GitHub" height="48" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy-minimal/available/github_vector.svg">
  </a>
</p>

<p align="center">
  <a href="https://modrinth.com/project/beltborne-lanterns">
    <img alt="Modrinth Downloads" src="https://img.shields.io/modrinth/dt/NUFDZAKS?style=flat&logo=modrinth">
  </a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/beltborne-lanterns">
    <img alt="CurseForge Downloads" src="https://img.shields.io/curseforge/dt/1337474?style=flat&logo=curseforge">
  </a>
</p>

<p align="center">
 <img alt="Latest release" src="https://img.shields.io/github/v/release/Shadscure/Beltborne-Lanterns?display_name=release&sort=semver&style=flat-square&color=00ae5d">
</p>

# Beltborne Lanterns — MC 26.x Port

**Free your hands, light your path!**

Press **B** while holding a lantern — it snaps onto your belt. Walk, jump, fight — the lantern **sways with physics** and *casts light near you.*
Need it back in your hands? Press **B** again. 💡

> **📝 MC 26.x Port (Fabric):**
> This is a **port** of the original Beltborne Lanterns mod, updated for **Minecraft 26.1 - 26.2**.
> 
> - ✅ **Toggle functionality:** Fully working (press B to attach/detach)
> - ✅ **3D Rendering:** Fixed with proper ItemRenderer API (Vulkan backend support)
> - ✅ **Dynamic lighting:** Works with [LambDynamicLights](https://modrinth.com/mod/lambdynamiclights)
> - ✅ **Item management:** Smart inventory handling (returns to empty slots)
> - ✅ **Multiplayer:** Full sync and persistence
> - ✅ **All lantern variants:** Vanilla lanterns, soul lanterns, copper lanterns (all oxidation states)
> 
> The mod is **fully functional** and ready for use!

## 🧩 Why it’s awesome

* **One key, zero hassle.** Stop digging in your inventory: **B** instantly attaches or detaches the lantern.
* **Living light.** Physics makes the glow feel real — a gentle sway as you move and turn.
* **Immersion without compromise.** Hands free, light by your side — explore, build, survive.

## ⚙️ How to use

1. Hold a lantern.
2. Press **B** — it attaches to your belt.
3. Move around — it sways with you and *casts light near you.*
4. Press **B** again — it detaches.

> *Dynamic lighting note:* the in‑world light effect requires a dynamic lighting mod — see FAQ.

## ⧉ Add-ons
- **Beltborne Lanterns - Accessories Layer**:  [Modrinth](https://modrinth.com/mod/beltborne-lanterns-accessories-layer) / [Curseforge](https://www.curseforge.com/minecraft/mc-mods/beltborne-lanterns-accessories-layer) / [Github](https://github.com/Shadscure/Beltborne-Lanterns-Accessories-Layer)

## 📷 Showcase

<table>
  <tr>
    <td><img src="https://raw.githubusercontent.com/0xCoDSnet/Beltborne-Lanterns/refs/heads/1.21-architectury-loom/demo/demo1.webp" alt="demo 1" width="380"></td>
    <td><img src="https://raw.githubusercontent.com/0xCoDSnet/Beltborne-Lanterns/refs/heads/1.21-architectury-loom/demo/demo2.webp" alt="demo 2" width="380"></td>
  
  </tr>
  <tr>
     <td><img src="https://raw.githubusercontent.com/0xCoDSnet/Beltborne-Lanterns/refs/heads/1.21-architectury-loom/demo/demo3.webp" alt="demo 3" width="380"></td>
     <td><img src="https://raw.githubusercontent.com/0xCoDSnet/Beltborne-Lanterns/refs/heads/1.21-architectury-loom/demo/demo4.webp" alt="demo 4" width="380"></td>
  </tr>
  <tr>
    <td><img src="https://raw.githubusercontent.com/0xCoDSnet/Beltborne-Lanterns/refs/heads/1.21-architectury-loom/demo/demo5.webp" alt="demo 5" width="380"></td>
    <td><img src="https://raw.githubusercontent.com/0xCoDSnet/Beltborne-Lanterns/refs/heads/1.21-architectury-loom/demo/demo6.webp" alt="demo 6" width="380"></td>
  </tr>
</table>

## ❓ FAQ — compatibility & multiplayer

* **Does it work in multiplayer?** — **YES!** *(Requires both client and server)*
* **Does it emit light in vanilla Minecraft?** — **No.** Install a dynamic lighting mod to get light from the belt lantern:
  * **Fabric / Quilt / NeoForge:** [LambDynamicLights](https://modrinth.com/mod/lambdynamiclights)
  * **Alternative (NeoForge):** [Dynamic Lights (AtomicStryker)](https://www.curseforge.com/minecraft/mc-mods/dynamic-lights)
* **Will this work with the Soul Lantern?** — **YES!**
* **Will this work with a lantern from another mod?** — **YES!** Add its **item tag** to the compatible list in the config.
* **Does this work with the [Accessories](https://modrinth.com/mod/accessories)?** — **YES**, Install the **[Beltborne Lanterns: Accessories Layer](https://modrinth.com/mod/beltborne-lanterns-accessories-layer)** add-on.
* **Does this work with the [First-person Model](https://modrinth.com/mod/first-person-model)?** — **YES**, and it looks fantastic! *(screenshot under spoiler)*
  <details><summary>Show screenshot</summary>
    <img width="854" height="480" alt="2025-09-02_22 58 42" src="https://github.com/user-attachments/assets/cdcde99f-b18e-4d9c-946e-888637c1ad8e" />
  </details>


## Dynamic Lights Compatibility
<details><summary>Sodium Dynamic Lights</summary>

**Sodium Dynamic Lights** (fork of LambDynamicLights) is also technically supported, but:
- Issues related to Sodium Dynamic Lights will be closed
- Please use [LambDynamicLights](https://modrinth.com/mod/lambdynamiclights) for full support
- I cannot maintain compatibility with forks that may diverge from the original API

</details>

## Known Limitations

<details><summary>Bliss Shaders Floodfill</summary>

Belt lanterns are **incompatible** with Bliss Shaders' Floodfill colored lighting option.

**Why:** Floodfill voxelizes world blocks to compute lighting. Entity-attached items (like belt lanterns) exist outside this voxel space and cannot be detected.

**Workarounds:**
- Disable Floodfill in Bliss Shaders settings
- Use [LambDynamicLights](https://modrinth.com/mod/lambdynamiclights) for dynamic lighting (fully supported)

</details>

## 🙏 Credits

**Original Mod by:** [0xCoDSnet](https://github.com/0xCoDSnet) / [Shadscure](https://github.com/Shadscure)  
- Original repository: [Beltborne-Lanterns](https://github.com/Shadscure/Beltborne-Lanterns)
- Available on [Modrinth](https://modrinth.com/mod/beltborne-lanterns) and [CurseForge](https://www.curseforge.com/minecraft/mc-mods/beltborne-lanterns)
- Supports: MC 1.21.x, NeoForge, Fabric, Quilt

**Ported to MC 26.x (Fabric) by:** [nuramri23](https://github.com/nuramri23)
- Port repository: [Coming soon]
- Available on [Modrinth](https://modrinth.com/mod/beltborne-lanterns-unofficial-mc-26.1-port)
- Supports: MC 26.1, 26.1.1, 26.1.2, 26.2 (Fabric only)

**Port Changes (v1.2.6):**
- ✅ Updated rendering to MC 26.x API (Vulkan backend support)
- ✅ Fixed UV mapping for proper lantern textures using ItemRenderer
- ✅ Improved inventory handling (returns to empty slot instead of replacing items)
- ✅ Updated default position values for better placement
- ✅ Support for all MC 26.x versions in single JAR
- ✅ Fixed MC 26.2 compatibility issues (screen field access)

**Development:**
- Port developed with assistance from [Kiro IDE](https://kiro.dev) (AI-powered development environment)

## 📜 License

This project is licensed under the **Apache License 2.0** — see the [LICENSE](LICENSE) file for details.  
Full license text: [Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)

<p align="center">
  <sub>Crafted with ❤️ for the Minecraft community</sub>
</p>
