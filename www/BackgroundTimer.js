var exec = require("cordova/exec");
module.exports = {
	start: function(success, failure, config) {
        exec(success || function() {},
             failure || function() {},
             'BackgroundTimer',
             'start',
             [config]);
    },
    stop: function(success, failure) {
        exec(success || function() {},
            failure || function() {},
            'BackgroundTimer',
            'stop',
            []);
    }
};
