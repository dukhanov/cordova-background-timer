# cordova-background-timer
Background Timer plugin for Cordova
===================================


## Supported Platforms
- __Android__

## Installation

#### Automatic Installation using PhoneGap/Cordova CLI (iOS and Android)
1. Make sure you update your projects to Cordova iOS version 3.5.0+ before installing this plugin.

        cordova platform update android

2. Install this plugin using PhoneGap/Cordova cli:

        cordova plugin add https://github.com/dukhanov/cordova-background-timer.git
		
## Usage

#start a timer

	var successCallback = function(){
	// timer tick
	}
	
	var errorCallback = function(e){
	// an error occured
	}
	
	var settings = {
		timerInterval: 15000, // interval between ticks of the timer in milliseconds (Default: 10000)
		startOnBoot: true, // enable this to start timer after the device was restarted (Default: false)
		stopOnTerminate: true, // set to true to force stop timer in case the app is terminated (User closed the app and etc.) (Default: true)
		isForeground: true, // set to true to run timer service as foreground (Default: true)
		notificationTitle: 'TITLE', // title for the notification (Default: '')
		notificationText: 'TEXT' // text for the notification (Default: '')
	}
	
    window.BackgroundTimer.start(successCallback, errorCallback, settings);
	
#stop a timer

	var successCallback = function(){
	// timer tick
	}
	
	var errorCallback = function(e){
	// an error occured
	}
	
	window.BackgroundTimer.stop(successCallback, errorCallback);