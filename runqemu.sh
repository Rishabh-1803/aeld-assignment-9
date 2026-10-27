#!/bin/bash
set -eo pipefail

TOPDIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
cd "$TOPDIR"

# Initialize the existing Yocto environment.
source poky/oe-init-build-env build

# Ports required by the assignment tests.
export QB_SLIRP_OPT="-netdev user,id=net0,hostfwd=tcp::10022-:22,hostfwd=tcp::9000-:9000"

# Find the existing QEMU boot configuration.
IMGDIR="$TOPDIR/build/tmp/deploy/images/qemuarm64"

CONF="$(find "$IMGDIR" -maxdepth 1 -type f -name '*.qemuboot.conf' \
    -printf '%T@ %p\n' | sort -nr | head -n 1 | cut -d' ' -f2-)"

if [[ -z "$CONF" || ! -f "$CONF" ]]; then
    echo "ERROR: No QEMU boot configuration found in $IMGDIR"
    exit 1
fi

# Use the native ARM64 QEMU installed on the Oracle host.
sed -i -E \
    's#^([[:space:]]*staging_bindir_native[[:space:]]*=[[:space:]]*).*#\1/usr/bin#I' \
    "$CONF"

echo "Using existing image configuration: $CONF"
echo "Using host QEMU: /usr/bin/qemu-system-aarch64"

# Explicit configuration avoids bitbake -e path discovery.
exec runqemu "$CONF" slirp nographic
