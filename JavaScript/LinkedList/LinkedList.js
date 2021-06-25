/**
 * This class represent a node, 
 */
 class Node {
    constructor(data, node){
        this.data = data;
        this.next = node;
    }
}

/**
 * This class represent a linked list
 */
class LinkedList {

    constructor (){
        this.first = null;
        this.last  = null;
        this.listSize  = 0;
    }
 
    /**
     * 
     * @returns Returns the current size of the linked list
     */
    size(){
        return this.listSize;
    }

    /**
     * 
     * @returns Returns true if the current linked list is empty
     */
    isEmpty(){
        return this.listSize == 0;
    }

    /**
     * 
     * @returns Returns the las element of the linked list
     */
    getLast(){
        return this.last;
    }

    /**
     * 
     * @returns Returns the first element of the linked list
     */
    getFirst(){
        return this.first;
    }

    /**
     * 
     * @param {Index} i 
     * @returns Return the element of the current linked list at the index i
     */
    get(i){
        if (i<0 || i>=this.listSize){
            throw "Invalid position " + i;
        }

        let aux = this.first;
        for (var pos = 0; pos<i; pos++){
            aux = aux.next;
        }

        return aux;
    }

    /**
     * Insert a new data at the end of the linked list
     * @param {Data to Insert} data 
     */
    append(data){
        let newNode = new Node(data, null); //new node to insert 
        if (this.listSize == 0){
            this.first = newNode;
        }else{
            this.last.next = newNode;
        }
        this.last = newNode;
        this.listSize++;
    }

    /**
     * This method insert a new data at the beginning of the linked list
     * @param {Data to insert} data 
     */
    preppend(data){
        this.first = new Node(data, this.first);
        if (this.listSize == 0){
            this.last = this.first;
        }
        this.listSize++;
    }

    /**
     * Remove the element at the index i
     * @param {Index} i 
     */
    remove(i){
        if (i<0 || i>=this.listSize){
            throw "Invalid position " + i;
        }
        if (i==0){ //remove the first element
            this.first = this.first.next;

            if (this.first == null){//check if after remove the first element, it was also the last one
                this.last = null;
            }
        }else{
            let prev = this.get(i-1);
            prev.next = prev.next.next;

            if (i==(this.listSize-1)){
                this.last = prev;
            }
        }
        this.listSize--;
    }

}



