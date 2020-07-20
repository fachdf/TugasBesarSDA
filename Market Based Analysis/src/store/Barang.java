package store;

/*
    Author  : Fachri Dhia Fauzan
    NIM     : 191524041
*/
public class Barang {
    private String name;
    private int id;
    public Barang (int id, String name){
        this.name = name;
        this.id = id;
    }
    public int GetID(){
        return this.id;
    }    
    public String GetName(){
        return this.name;
    }
}
