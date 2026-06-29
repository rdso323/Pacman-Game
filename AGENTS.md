# AGENTS.md

## Cursor Cloud specific instructions

### What this project is
A single **JavaFX desktop game** ("PAC-MAN Dragon Edition"), authored as an Eclipse JDT project. There is no backend, database, or web service — the entire product is one local GUI process (`PACMAN.Main`). The only real source tree is `PACMAN/` (the files under `src/PACMAN/` are empty placeholders). Game assets are loaded with paths relative to the repo root (e.g. `Resources/...`, `highScore.txt`), so always build/run from `/workspace`.

### Toolchain / non-obvious dependencies
- JDK is the system OpenJDK 21. **JavaFX is NOT bundled with JDK 11+**, so it is provided separately as an OpenJFX SDK installed at `~/javafx-sdk` (the update script downloads it if missing). Build/run must pass `--module-path "$HOME/javafx-sdk/lib" --add-modules javafx.controls,javafx.media`.
- The game is a GUI app and requires a display. Use the existing X server on `DISPLAY=:1`.
- **Audio gotcha:** `PACMAN.Main` calls `backGMusic.bMusic()` on startup, which creates a JavaFX `MediaPlayer`. If there is no audio sink, this throws `MediaException: Could not create player!` and the **entire app crashes before any window shows**. The VM has no real sound card, so a PulseAudio null sink must be running. Start it once per session before launching the game:
  ```bash
  pulseaudio --start --exit-idle-time=-1
  pactl load-module module-null-sink sink_name=dummy
  pactl set-default-sink dummy
  ```

### Build and run
There is no build tool (no Maven/Gradle/npm). Compile and run directly with `javac`/`java` from the repo root:
```bash
# Compile (output goes to bin/, which is gitignored for .class files)
javac --module-path "$HOME/javafx-sdk/lib" --add-modules javafx.controls,javafx.media \
  -d bin $(find PACMAN -name "*.java")

# Run (must be from /workspace so relative Resources/ paths resolve)
DISPLAY=:1 java --module-path "$HOME/javafx-sdk/lib" --add-modules javafx.controls,javafx.media \
  -cp bin PACMAN.Main
```

### Lint / test
There are no automated tests and no linter configured in this repo. The only verification available is compiling (`javac`, above) and launching the GUI.

### Known pre-existing behavior (not an environment issue)
Starting a single-player game renders the maze with the player and ghosts, but the original 2018 game logic immediately triggers "Game Over" (and the menu flow can return early on repeated attempts). This is a bug in the original game code, not a setup problem. Do not "fix" it as part of environment work.
