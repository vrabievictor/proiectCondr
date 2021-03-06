/**
 *  HTML5 Webcam Barcode Reader with Dynamsoft Barcode Reader SDK.
 *  Support PC & Mobile.
 */
var videoElement = document.querySelector('video');
var canvas = document.getElementById('pcCanvas');
var mobileCanvas = document.getElementById('mobileCanvas');
var ctx = canvas.getContext('2d');
var mobileCtx = mobileCanvas.getContext('2d');
var videoSelect = document.querySelector('select#videoSource');
var videoOption = document.getElementById('videoOption');
var buttonGo = document.getElementById('go');
var barcode_result = document.getElementById('dbr');
var isConnected = false;
var isPaused = false;
var intervalId = 0;
var videoWidth = 640, videoHeight = 480;
var mobileVideoWidth = 240, mobileVideoHeight = 320;
var isPC = true;

// check devices
function browserRedirect() {
   var deviceType;
   var sUserAgent = navigator.userAgent.toLowerCase();
   var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
   var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
   var bIsMidp = sUserAgent.match(/midp/i) == "midp";
   var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
   var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
   var bIsAndroid = sUserAgent.match(/android/i) == "android";
   var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
   var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
   if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) {
    deviceType = 'phone';
   } else {
    deviceType = 'pc';
   }
   return deviceType;
}

if (browserRedirect() == 'pc') {
  isPC = true;
}
else {
  isPC = false;
}


// initialize camera infos for Chrome
function initCameraSource(sourceInfos) {
  for (var i = 0; i < sourceInfos.length; i++) {
    var sourceInfo = sourceInfos[i];
    var option = document.createElement('option');
    option.value = sourceInfo.id;
    if (sourceInfo.kind === 'video') {
      option.text = sourceInfo.label || "Camera " + (videoSelect.length + 1);
      videoSelect.appendChild(option);
    } else {
      console.log("Source info: " + sourceInfo);
    }
  }
  toggleCamera();
}

// stackoverflow: http://stackoverflow.com/questions/4998908/convert-data-uri-to-file-then-append-to-formdata/5100158


function successCallback(stream) {
  window.stream = stream;
  videoElement.src = window.URL.createObjectURL(stream);
  videoElement.play();
}

function errorCallback(error) {
  console.log("Error: " + error);
}

function startCamera(constraints) {
  if (navigator.getUserMedia) {
     navigator.getUserMedia(constraints, successCallback, errorCallback);
  }
  else {
     console.log("getUserMedia not supported");
  }
}

function toggleCamera() {
  var videoSource = videoSelect.value;
  var constraints = {
    video: {
      optional: [{
        sourceId: videoSource
      }]
    }
  };

  startCamera(constraints);
}

var formdata = new FormData();

var http = new XMLHttpRequest();
var url = "Web/Controller/Scan";
http.open("POST", url, true);
http.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
http.onreadystatechange = function() {//Call a function when the state changes.
    if(http.readyState == 4 && http.status == 200) {
        console.log("ceva este");
    } else {
    	console.log("ceva nu a mers bine");
    }
};

// add button event
buttonGo.onclick = function() {
  window.clearInterval(intervalId);
  console.log("clean id: " + intervalId);
  if (isPC) {
    canvas.style.display = 'none';
  }
  else {
    mobileCanvas.style.display = 'none';
  }

  isPaused = false;
  scanBarcode();
};

// query supported Web browsers
if (navigator.getUserMedia || navigator.mozGetUserMedia) {
  navigator.getUserMedia = navigator.getUserMedia || navigator.mozGetUserMedia;
  startCamera({
    video: {
      // mandatory: {
      //   maxWidth: 640,
      //   maxHeight: 480
      // }
    }
  });
}
else if (navigator.webkitGetUserMedia) {
  videoOption.style.display  = 'block';
  navigator.getUserMedia = navigator.webkitGetUserMedia;
  MediaStreamTrack.getSources(initCameraSource);
  videoSelect.onchange = toggleCamera;
}

// scan barcode
function scanBarcode() {
//  intervalId = window.setInterval(function() {
//    
//
//  }, 200);
  
  barcode_result.innerHTML ="haha";
  var data = null, newblob = null;
 
  
  if (isPC) {
    ctx.drawImage(videoElement, 0, 0, videoWidth, videoHeight);
    // convert canvas to base64
    data = encodeURIComponent(canvas.toDataURL('image/png',1.0));
    // convert base64 to binary
  console.log(data);
  
   http.send("image="+data);
  }
  else {

    mobileCtx.drawImage(videoElement, 0, 0, mobileVideoWidth, mobileVideoHeight);
    // convert canvas to base64
    data = encodeURIComponent(mobileCanvas.toDataURL('image/png', 1.0));
    http.send("image="+data);
  }
  console.log("create id: " + intervalId);
}
