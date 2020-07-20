package store;

import java.util.ArrayList;

/*
    Author  : Fachri Dhia Fauzan
    NIM     : 191524041
*/
public class Toko {
    private ArrayList<Barang> BarangList;
    private Data StoreData;
    
    private Toko(ArrayList<String> listbrg){
        int i=1;
        BarangList = new ArrayList<>();
        Barang newBarang;
        for(String s : listbrg){
            newBarang = new Barang(i, s);
            BarangList.add(newBarang);
            i++;
        }
        StoreData = new Data(GetNumberofBarang());
    }
    
    public static Toko CreateToko(ArrayList<String> listbrg) {
        return new Toko(listbrg);
    }
    
    public Data getData(){
        return StoreData;
    }
    
    public ArrayList<Barang> GetAllBarang(){
        return BarangList;
    }
    
    public int GetNumberofBarang(){
        return BarangList.size();
    }
    
    public String searchBarangByID(int ID){
        if(this.GetAllBarang() == null){System.out.println("Toko belum memiliki stok barang.");}
        for(Barang b : this.GetAllBarang()){
            if(ID == b.GetID()){
                return b.GetName();
            }
        }
        System.out.println("Tidak ada barang yang dicari.");
        return null;
    }
    
    public ArrayList<ArrayList<String>> Beli(ArrayList<String> pembelian){
        
        int i = 0;
        char[] addKombi = new char[pembelian.size()];
        ArrayList<ArrayList<String>> newKombi = new ArrayList<>();
        for(String s : pembelian){
            for(Barang b : BarangList){
                if(s.equals(b.GetName())){
                    addKombi[i] = (char) ((char) b.GetID() + '0');
                    i++;
                }
            } 
        }
        /* Masukkan Support ke kombi, lalu return (ke increase support, untuk supportnya ditambah satusatu) */
        Kombi a = new Kombi();
        newKombi = a.getCombiFromInput(addKombi);
        newKombi.remove(0);
//        System.out.println(newKombi);
        return newKombi;

    }
}
