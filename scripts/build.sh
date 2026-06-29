#!/usr/bin/env bash
# Build a native Pac-Man Dragon Edition app image (Linux).
# Usage: ./scripts/build.sh [variant]
#   variant defaults to "baseline" -> PacManDragon-baseline
#   use "improved" on the improvements branch -> PacManDragon-improved

set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

VARIANT="${1:-baseline}"
APP_NAME="PacManDragon-${VARIANT}"
JFX="${JAVAFX_HOME:-/usr/share/openjfx}/lib"

if [[ ! -d "$JFX" ]]; then
  echo "JavaFX not found at: $JFX"
  echo "Install OpenJFX (e.g. sudo apt install openjfx) or set JAVAFX_HOME to the SDK root."
  exit 1
fi

if ! command -v jpackage >/dev/null 2>&1; then
  echo "jpackage not found. Install JDK 17+ and ensure it is on PATH."
  exit 1
fi

echo "Building ${APP_NAME} (Linux)..."

rm -rf "build/classes" "build/pacman.jar" "build/sources.txt"
mkdir -p build/classes "dist/${VARIANT}"

find PACMAN src -name "*.java" > build/sources.txt
javac --module-path "$JFX" \
  --add-modules javafx.controls,javafx.media,javafx.fxml \
  -d build/classes \
  @build/sources.txt

jar --create --file build/pacman.jar --main-class PACMAN.Launcher -C build/classes .

rm -rf "dist/${VARIANT}/${APP_NAME}"
jpackage --name "$APP_NAME" \
  --input build \
  --main-jar pacman.jar \
  --main-class PACMAN.Launcher \
  --type app-image \
  --module-path "$JFX" \
  --add-modules javafx.controls,javafx.media,javafx.fxml \
  --dest "dist/${VARIANT}"

# Game assets load from the working directory (Resources/, highScore.txt).
LAUNCHER_DIR="dist/${VARIANT}/${APP_NAME}/bin"
cp -r Resources highScore.txt "$LAUNCHER_DIR/"

cat > "dist/${VARIANT}/run.sh" <<EOF
#!/usr/bin/env bash
cd "\$(dirname "\$0")/${APP_NAME}/bin"
exec "./${APP_NAME}" "\$@"
EOF
chmod +x "dist/${VARIANT}/run.sh"

echo ""
echo "Done. Run the game with:"
echo "  dist/${VARIANT}/run.sh"
echo ""
echo "Or directly:"
echo "  dist/${VARIANT}/${APP_NAME}/bin/${APP_NAME}"
