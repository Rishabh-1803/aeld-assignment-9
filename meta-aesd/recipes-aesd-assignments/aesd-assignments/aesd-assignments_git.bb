LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-Rishabh-1803.git;protocol=ssh;branch=master"

PV = "1.0+git${SRCPV}"

SRCREV = "58532b6"

S = "${WORKDIR}/git/server"

FILES:${PN} += "${bindir}/aesdsocket"
FILES:${PN} += "${sysconfdir}/init.d/S99aesdsocket"

TARGET_LDFLAGS += "-pthread -lrt"
EXTRA_OEMAKE += "USE_AESD_CHAR_DEVICE=1"

inherit update-rc.d

INITSCRIPT_NAME = "S99aesdsocket"
INITSCRIPT_PARAMS = "defaults 99"

do_configure() {
    :
}

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/aesdsocket ${D}${bindir}/aesdsocket

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/aesdsocket-start-stop \
        ${D}${sysconfdir}/init.d/S99aesdsocket
}
