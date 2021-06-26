var list ;
var x, y;


document.getElementById("appendData").onclick = function() {
    let data = document.getElementById("data").value;
    console.clear();
    list.append(data);
    console.log(list);
    drawLinkedList();
}

document.getElementById("preppendData").onclick = function(){
    let data = document.getElementById("data").value;
    list.preppend(document.getElementById("data").value);
    console.clear();
    console.log(list);
    drawLinkedList();
}

document.getElementById("removeButton").onclick = function(){
    list.remove(document.getElementById("idToRemove").value);
    console.clear();
    console.log(list);
    drawLinkedList();
}

/* DRAW LINKED LIST */
function drawLinkedList (){
    ctx.clearRect(0, 0, canvas.width, canvas.height); //clean canvas
    x = 20;
    y = 40;
    for (var i=0;i<list.size();i++){
        let data = list.get(i);
        drawNode(data)
    }
}

/* CANVAS */
var canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");

function drawNode (data){
    ctx.beginPath();
    ctx.arc(x, y, 20, 0, Math.PI*2, false);
    ctx.fillStyle = "green";
    ctx.fill();
    ctx.mozTextStyle = "20pt Arial";
    ctx.fillStyle = "white";
    ctx.fillText(data, x, y);
    ctx.closePath();
    x+=30;
    drawArrow();
    x+=60;
    if (x>=380){
        y+=60;
        x = 20;
    }
    
}

function drawArrow(){
    ctx.beginPath();
    ctx.rect(x, y, 20,0)
    ctx.strokeStyle = "rgba(0, 0, 255, 0.5)";
    ctx.stroke();
    ctx.closePath();
}



/***
 * IMPORTANT: This is use to initialize at 100ms after the page is loaded the linked list
 */
 function loadList() {
    list = new LinkedList();
    x = 20;
    y = 40;
}

setTimeout(loadList, 100);