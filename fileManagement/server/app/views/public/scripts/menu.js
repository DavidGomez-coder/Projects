/**
 * It's used to hidde all menu
 */
document.getElementById("menuButton").onclick = function (){
    var nav = document.getElementsByClassName("enlace");
    for (var i=0;i<nav.length;i++){
        nav[i].classList.toggle("visible");
    }
}



