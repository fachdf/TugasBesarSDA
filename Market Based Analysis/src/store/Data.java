package store;

import java.util.ArrayList;
import java.util.HashMap;
import static store.Kombi.generate;

/*
    Author  : Fachri Dhia Fauzan
    NIM     : 191524041
*/
public class Data {
    private HashMap<ArrayList<String>, Integer> ItemsetSupport;
    /* ArrayList string = ListBarang, Integer = support */
    
    private ArrayList<ArrayList<String>> kombi;
    
    public Data(int jmlJenisBarang){
        Kombi newkombi = new Kombi();
        kombi = generate(jmlJenisBarang);
       
        ItemsetSupport = new HashMap<>();
        kombi.forEach((b) -> {
            ItemsetSupport.put(b, 0);
        });
    }
    
    void IncreaseSupportByOne(ArrayList<ArrayList<String>> barang){
        int oldValue;
        for(ArrayList<String> oneBarang : barang){
            oldValue = ItemsetSupport.get(oneBarang);
            ItemsetSupport.put(oneBarang, oldValue+1); //Menambah Support dengan 1
        }
    }
    
    int GetBarangSupportValue(int IDbarang){
        ArrayList<String> brg = new ArrayList<>();
        brg.add(String.valueOf(IDbarang));
        if(ItemsetSupport.get(brg) != null){
        return ItemsetSupport.get(brg);
        }
        else{ return 0;}
    }
    
    /* Setter-Getter */
    ArrayList<String> GetKombi(int index){
        return kombi.get(index);
    }
    
    Integer GetSupport(int index){
        return ItemsetSupport.get(kombi.get(index));
    }
    
    int GetKombiSize(){
        return ItemsetSupport.size();
    }
    
    ArrayList<ArrayList<String>> GetAllItemKombi(){
        return kombi;
    }
    
    HashMap<ArrayList<String>, Integer> GetAllDataBarang(){
        return ItemsetSupport;
    }
    
}
