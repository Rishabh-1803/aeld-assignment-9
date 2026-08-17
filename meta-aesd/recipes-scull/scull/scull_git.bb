# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "CLOSED"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-Rishabh-1803.git;protocol=ssh;branch=master \
           file://scull-init \
"

PV = "1.0+git${SRCPV}"
SRCREV = "c3319d6554267c1a48cb5e91c954de7b70476909"

S = "${WORKDIR}/git/scull"

inherit module
inherit update-rc.d

EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra

    install -m 0644 ${S}/scull.ko \
        ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/

    install -d ${D}${sysconfdir}/init.d

    install -m 0755 ${WORKDIR}/scull-init \
        ${D}${sysconfdir}/init.d/S98scull
}

INITSCRIPT_NAME = "S98scull"
INITSCRIPT_PARAMS = "defaults 98"

FILES:${PN} += "${sysconfdir}/init.d/S98scull"
