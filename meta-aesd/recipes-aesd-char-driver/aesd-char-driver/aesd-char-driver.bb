SUMMARY = "AESD character device driver"
DESCRIPTION = "AESD character device driver for Assignment 8"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-Rishabh-1803.git;protocol=ssh;branch=master \
           file://S98aesdchar"

SRCREV = "58532b6"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git/aesd-char-driver"

EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit module update-rc.d

INITSCRIPT_NAME = "S98aesdchar"
INITSCRIPT_PARAMS = "defaults 98"

do_configure() {
    :
}

do_compile() {
    oe_runmake modules
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra
    install -m 0644 ${S}/aesdchar.ko \
        ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar.ko

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/S98aesdchar \
        ${D}${sysconfdir}/init.d/S98aesdchar
}

FILES:${PN} += "${sysconfdir}/init.d/S98aesdchar"
