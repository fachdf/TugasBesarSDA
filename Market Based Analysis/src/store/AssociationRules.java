package store;

import java.util.ArrayList;
import java.util.HashMap;

/*
    Author  : Fachri Dhia Fauzan
    NIM     : 191524041
*/
public class AssociationRules {
    private Toko toko;
    private double[] confidence;
    private int[] confidenceFactor;
    private double treshold;
    private AssociationRules(int jmlBrg, Toko newToko){
        confidence = new double[jmlBrg];
        confidenceFactor = new int[jmlBrg];
        toko = newToko;
        for(int i = 0; i < jmlBrg; i++){ 
            confidence[i] = 0;
            confidenceFactor[i] = 0;
        }
    }
    
    public static AssociationRules CreateAssociationRules(int jmlbrg, Toko newToko) {
        return new AssociationRules(jmlbrg, newToko);
    }
    
    /* Setter - Getter */
    public Toko getToko(){
        return toko;
    }
    public int[] getConfidenceFactor(){
        return confidenceFactor;
    }
    
    private double[] getConfidence(){
        return confidence;
    }  
  
    /* Process Method */
    
    /* Class untuk memproses persamaan, dimana ada 3 persamaan. */
    public double[] CalculateConfidence(Data dataToko, int[] IDbarang){
        InitializeConfidence(this);        
        CalculateConfidenceFactor(this, IDbarang);
        int suppFactor  =  CalculateSupportFactor(this, IDbarang);
        CalculateConfidence(this, suppFactor);       
        return confidence;
    }
    
    public void printAllData(){
         for(int i = 0 ; i < this.getToko().getData().GetKombiSize(); i++){
            System.out.println(this.getToko().getData().GetKombi(i) + " Support : " + this.getToko().getData().GetSupport(i));
        }
    }
    
    private int CalculateSupportFactor(AssociationRules rule, int[] IDbarang){
        int supportFactorTemp = 0;
            for(int i = 0; i < IDbarang.length; i++){ 
                supportFactorTemp += rule.getToko().getData().GetBarangSupportValue(IDbarang[i]);
            } 
        return supportFactorTemp;
    }
    
    private void CalculateConfidenceFactor(AssociationRules rule, int[] IDbarang){
        int[] confidenceFactorTemp = rule.getConfidenceFactor();
        for(ArrayList<String> kombi : rule.getToko().getData().GetAllItemKombi()){
            for(int i = 0; i < 4; i++){   
                if( (IsNotMemberOf(i+1,IDbarang)) && (IsContainingIDbarang(IDbarang, i+1, kombi)) ){              
                    confidenceFactorTemp[i] = confidenceFactorTemp[i] + rule.getToko().getData().GetAllDataBarang().get(kombi); //menambahkan value support yang
                }
            } 
        }
    }
    private boolean IsNotMemberOf(int num, int[] ListID){
        for(int i = 0 ; i < ListID.length; i++){
            if(num == ListID[i]){
                return false;
            }
        }
        return true;
    }
    private double[] CalculateConfidence(AssociationRules rule, int suppFactor){
        int jmlBarang = rule.getConfidenceFactor().length;
        int[] supportList = new int[jmlBarang];
        double[] ConfidenceTemp = rule.getConfidence();
        int support = suppFactor;
        for(int i = 0;i<jmlBarang;i++){
            if(support == 0){
                ConfidenceTemp[i] = 0;
            }else{
                ConfidenceTemp[i] = (double)confidenceFactor[i] / (double)support;
            }
        }

       return ConfidenceTemp;
    }
    
    private boolean IsContainingIDbarang(int[] IDbarang, int index, ArrayList<String> kombi){

        boolean valid = true;
        if(!kombi.contains(String.valueOf(index))){
            return false;
        }
        for(int i = 0; i < IDbarang.length; i++){
            if(!kombi.contains(String.valueOf(IDbarang[i]))){
                return false;
            }
        }
   
        return true;
    }
    public double CalculateandGetThreshold(){
        double total = 0;
        double count = 0;
        for(int i = 0; i < this.confidence.length; i++){
            total += this.confidence[i];
        }
        //Calculate how much item is != 0
        for(int i = 0; i < this.confidence.length; i++){
            if(confidence[i] != 0.0){
                count++;
            }
        }
        total = total / (double) count;
        this.treshold = total;
        return treshold;
    }
    
    public void Recommend(){
        treshold = CalculateandGetThreshold();
        for(int i = 0; i < this.confidence.length; i++){
            if(confidence[i] >= treshold){
                System.out.println("Merekomendasikan Membeli " + this.toko.searchBarangByID(i+1));
            }
        }
    }
    
    private void InitializeConfidence(AssociationRules newRule){
        int[] confidencefactor = newRule.getConfidenceFactor();
        double[] confidence = newRule.getConfidence();
        int jmlBarang = confidence.length;
        for(int i = 0; i < jmlBarang ; i++){
            confidencefactor[i] = 0;
            confidence[i] = 0.0;
        }
    }
}
