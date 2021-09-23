package piece;

import board.*;

public abstract class Piece {

    private boolean isWhite = false;
    private boolean isKilled = false;

    public Piece (boolean isWhite){
        this.isWhite = isWhite;
    }

    public boolean isWhite (){
        return  this.isWhite;
    }

    public boolean isKilled(){
        return  this.isKilled;
    }

    public  void setWhite(boolean isWhite){
        this.isWhite = isWhite;
    }

    public void setKilled (boolean isKilled){
        this.isKilled = isKilled;
    }

    public abstract boolean canMove (Board board, Tile start, Tile end);
}
