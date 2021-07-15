
const ChromecastAPI = require('chromecast-api');
const client = new ChromecastAPI();

module.exports = app => {
    //used to chromecast a video
    app.get("/chromecast", (req, res) => {
        client.on('device', function(device){
            console.log(client.devices);
            // var mediaURL = "/media/" + req.query.filePath;
            // console.log("Playing on : " + device.name);
            // device.play(mediaURL, function(err){
            //     if (!err)  console.log("Video playing on chromecast");
            // })
        })
    });
}