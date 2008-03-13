
//
// This script is executed by Grails after plugin was installed to project.
// This script is a Gant script so you can use all special variables provided
// by Gant (such as 'baseDir' which points on project base dir). You can
// use 'Ant' to access a global instance of AntBuilder
//
// For example you can create directory under project tree:
// Ant.mkdir(dir:"/home/prule/temp/selenium/grails-app/jobs")
//

Ant.property(environment:"env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"

includeTargets << new File ( "${grailsHome}/scripts/Init.groovy" )  
checkVersion()
configureProxy()

seleniumCoreVersion = "0.8.3"
seleniumCoreUrl="http://release.openqa.org/selenium-core/${seleniumCoreVersion}/selenium-core-${seleniumCoreVersion}.zip"
seleniumCoreLocal = "${grailsHome}/downloads/selenium-core-${seleniumCoreVersion}.zip"

Ant.sequential {
    mkdir(dir:"${grailsHome}/downloads")

    event("StatusUpdate", ["Downloading SeleniumCore ${seleniumCoreVersion}"])

    get(dest:seleniumCoreLocal,
        src:seleniumCoreUrl,
        verbose:true,
        usetimestamp:true)
    
    mkdir(dir:"${basedir}/web-app/selenium")
    mkdir(dir:"${basedir}/web-app/selenium/tests")
    
	unzip(dest:"${basedir}/web-app/selenium",src:seleniumCoreLocal) {patternset{include(name:"core/**")}}
		 
}            
event("StatusFinal", ["Selenium Core ${seleniumCoreVersion} installed successfully"])
