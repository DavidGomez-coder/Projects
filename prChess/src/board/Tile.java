package board;

import piece.*;

public class Tile {
    private Piece piece;
    private int x;
    private int y;

    public Tile (int x, int y, Piece piece){
        this.piece = piece;
        this.x = x;
        this.y = y;
    }

    public void setPiece (Piece piece){
        this.piece =  piece;
    }

    public void setX (int x){
        this.x = x;
    }

    public void setY (int y){
        this.y = y;
    }

    public Piece getPiece(){
        return  this.piece;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return  this.y;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof  Tile){
            Tile p = (Tile)obj;
            return p.getX() == x && p.getY() == y;
        }
        return false;
    }

    @Override
    public int hashCode (){
        return Integer.hashCode(x) + Integer.hashCode(y);
    }

}
