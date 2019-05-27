<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
  <head>  
    <meta charset="UTF-8">  
    <title></title>  
    <style>
      #canvas,#video {
        float: left;  
        margin-right: 10px;  
        background: #fff;  
      }      
      .box {  
        overflow: hidden;  
        margin-bottom: 10px;  
      }
    </style>
	<script type="text/javascript" src="js/jquery.min.js"></script>
  </head>  
  <body>  
    <div class="box">
      <video id="video" width="500" height="400"></video>
      <canvas id="canvas"></canvas>
    </div>
    <button id="live">打开摄像头</button>
    <button id="snap">登录验证</button>
    <script>  
      var video = document.getElementById('video');
      var canvas = document.getElementById('canvas');  
      var ctx = canvas.getContext('2d');  
      var width = video.width;  
      var height = video.height;  
      canvas.width = width;  
      canvas.height = height;   
      function liveVideo(){  
        var URL = window.URL || window.webkitURL;   // è·åå°window.URLå¯¹è±¡
        navigator.getUserMedia({  
          video: true  
        }, function(stream){  
          video.src = URL.createObjectURL(stream);   // å°è·åå°çè§é¢æµå¯¹è±¡è½¬æ¢ä¸ºå°å
          video.play();   // æ­æ¾
          //ç¹å»æªå¾     
          document.getElementById("snap").addEventListener('click', function() {  
            ctx.drawImage(video, 0, 0, width, height); 
            $("#canvas").hide(); 
		   // Generate the image dataï¼å°Canvasçåå®¹ä¿å­ä¸ºå¾çåå©toDataURLæ¥å®ç°ï¼ æ¹æ³è¿åä¸ä¸ªåå«å¾çå±ç¤ºç data URI ã  
                var Pic = canvas.toDataURL("image/jpg");                
                //å¯¹å¶è¿è¡base64ç¼ ä¹åçå­ç¬¦ä¸²  
                Pic = Pic.replace(/^data:image\/(png|jpg);base64,/,"")                 
                // Sending the image data to Server  
                $.ajax({  
                    type: 'POST',  
                    url:  "http://localhost:8086/login",  
                    data: { "imageData" : Pic},  
                    success: function (msg) {  
                       alert("success:"+msg);  
                       window.location.href = 'http://localhost:8086/index.jsp';
                    }  
                });  
          });
        }, function(error){  
          console.log(error.name || error);  
        });  
      }  
      
     
      document.getElementById("live").addEventListener('click',function(){  
        liveVideo();  
      });  
      
    </script>  
  </body> 
</html>