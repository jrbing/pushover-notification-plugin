# makefile
pluginname=pushover-notification-plugin
pluginversion=0.0.4
pluginjar=$(pluginname)-$(pluginversion).jar

.PHONY: clean

$(pluginjar):
	gradle

clean:
	@-rm -rf build/libs/$(pluginfile)
