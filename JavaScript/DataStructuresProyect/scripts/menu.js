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

/**
 * This function is used to change de main photo at the center of index.xhtml
 */
function changePhoto(){
    var jsLogo = document.getElementById("jsLogo");
    var csLogo = document.getElementById("cssLogo");
    if (jsLogo.style.visibility == "visible" && csLogo.style.visibility == "hidden"){
       
        jsLogo.style.visibility = "hidden";
        jsLogo.style.display = "none";
        csLogo.style.visibility = "visible"
        csLogo.style.display = "block";
        
    }else{
        csLogo.style.visibility = "hidden"
        csLogo.style.display = "none";
        jsLogo.style.visibility = "visible";
        jsLogo.style.display = "block";
    }
}

setInterval(changePhoto, 10000);


