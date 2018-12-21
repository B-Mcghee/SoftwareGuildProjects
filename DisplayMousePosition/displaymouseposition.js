var isMouseTracking = false;

function updateMousePosition(){
  if(isMouseTracking){
    //event.clientX and event.clientY gathers mouse position
    //place them in a variable and change the display in an innertext event
    var positionX = document.getElementById("mousePositionX");
    positionX.innerText = event.clientX;
    var positionY = document.getElementById("mousePositionY");
    positionY.innerText = event.clientY;
  }

}
//function to enable toggle tracking
function toggleTracking(){
  //set isMouseTracking to false if it is true or viceversa

  isMouseTracking = !isMouseTracking;

  if(isMouseTracking){
    document.getElementById("trackingStatus").innerText = "TRACKING";
  }else{
    document.getElementById("trackingStatus").innerText = "NOT TRACKING";

  }
}
