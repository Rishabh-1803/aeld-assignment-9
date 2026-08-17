LICENSE = "CLOSED"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-Rishabh-1803.git;protocol=ssh;branch=master \
           file://S98lddmodules \
"

PV = "1.0+git${SRCPV}"
SRCREV = "c3319d6554267c1a48cb5e91c954de7b70476909"

S = "${WORKDIR}/git/misc-modules"

inherit module
inherit update-rc.d

EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra

    for module in hello hellop seq jiq sleepy complete silly faulty kdatasize kdataalign jit; do
        install -m 0644 ${S}/${module}.ko \
            ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    done

    install -d ${D}${sysconfdir}/init.d

    install -m 0755 ${WORKDIR}/S98lddmodules \
        ${D}${sysconfdir}/init.d/S98lddmodules
}

INITSCRIPT_NAME = "S98lddmodules"
INITSCRIPT_PARAMS = "defaults 98"

FILES:${PN} += "${sysconfdir}/init.d/S98lddmodules"
