/**
 * It's used to hidde all menu
 */
document.getElementById("menuButton").onclick = function (){
    var nav = document.getElementsByClassName("enlace");
    for (var i=0;i<nav.length;i++){
        nav[i].classList.toggle("visible");
    }
}

/**
 * It's used to hide only dataStructures button
 */
document.getElementById("dataStructuresBut").onclick = function(){
    var nav = document.getElementsByClassName("enlaceEDA");
    for (var i=0;i<nav.length;i++){
        nav[i].classList.toggle("visible");
    }
}

