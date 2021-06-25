var list ;


document.getElementById("appendData").onclick = function() {
    console.clear();
    list.append(document.getElementById("data").value);
    console.log(list);
}

document.getElementById("preppendData").onclick = function(){
    list.preppend(document.getElementById("data").value);
    console.clear();
    console.log(list);
}

document.getElementById("removeButton").onclick = function(){
    list.remove(parseInt(document.getElementById("idToRemove").value));
    console.clear();
    console.log(list);
}




/***
 * IMPORTANT: This is use to initialize at 100ms after the page is loaded the linked list
 */
 function loadList() {
    list = new LinkedList();
}

setTimeout(loadList, 100);