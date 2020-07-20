package store;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import static store.AssociationRules.CreateAssociationRules;

/*
    Author  : Fachri Dhia Fauzan
    NIM     : 191524041
*/
public class TestMain {
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) 
    { 
  
        // Create a new ArrayList 
        ArrayList<T> newList = new ArrayList<T>(); 
  
        // Traverse through the first list 
        for (T element : list) { 
  
            // If this element is not present in newList 
            // then add it 
            if (!newList.contains(element)) { 
                newList.add(element); 
            } 
        } 
  
        // return the new list 
        return newList; 
    } 
    
    public static void makeRandomTransactionToCSV(int jmlPembelian, Toko toko) throws IOException{
        // Our example data
        
        Random rand = new Random();
        int IDbarangRandom;
        int jmlBrgdiRandom;
        int jmlBrgdiToko = toko.GetNumberofBarang();
        FileWriter csvWriter = new FileWriter("ListBarang.csv");

        for (int i=1;i<=jmlPembelian;i++){
            jmlBrgdiRandom = (rand.nextInt(jmlBrgdiToko)%jmlBrgdiToko)+1;
            // Jml barang yg dibeli di random juga karena tidak akan selalu membeli semuanya sekaligus.
            
            for(int j=1;j<=jmlBrgdiRandom;j++){
                IDbarangRandom=(rand.nextInt(jmlBrgdiToko)%jmlBrgdiToko)+1; //+1 karena ID barang dimulai dari 1, agar hasil minimal = 0
                csvWriter.append(toko.searchBarangByID(IDbarangRandom)); //input Nama barang sesuai ID yang dirandom tadi
                csvWriter.append(",");
            }
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }
    public static void inputTransactionFromCSV(ArrayList<String> listpembelian, Toko myToko){
        String line = "";
        String splitBy = ",";
        int i = 0;
        try {
            //parsing a CSV file into BufferedReader class constructor  
            BufferedReader br = new BufferedReader(new FileReader("ListBarang.csv"));
            System.out.println("\nHasil:");
            while ((line = br.readLine()) != null) //returns a Boolean value
            {
                i = i + 1;
                String[] employee = line.split(splitBy); // use comma as separator                  
                for(int j=0;j<employee.length;j++){   
                    listpembelian.add(employee[j]);
                }
                listpembelian = removeDuplicates(listpembelian);
                myToko.getData().IncreaseSupportByOne(myToko.Beli(listpembelian));
                listpembelian.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static int mainMenu(){
        System.out.println("--Market Basket Analysis Simulation--");
        System.out.println("--Made by Kelompok 4--");
        System.out.println();
        System.out.println("Main Menu");
        System.out.println("1. Buat random sample data, lalu hitung supportnya. (defaultnya telah dibuat, menu ini untuk menambah sample data)");
        System.out.println("2. Input Barang, Lalu Analisa Confidence Ke Barang Lain");
        System.out.println("3. Tampilkan Kombinasi Barang (Itemset) dan Supportnya");
        System.out.println("0. Keluar");
        Scanner input = new Scanner(System.in);
        System.out.print("Pilih : ");
        return input.nextInt();
    }
    public static void mainMenuSwitch(int menu, AssociationRules newRule) throws IOException{
        int temp =0;
        int i;
        int inputBrgLagi=1;
        int inputBrg;
        boolean valid = false;
        ArrayList<Integer> listInputBrg = new ArrayList<Integer>();
        Scanner input = new Scanner(System.in);
        switch(menu){
            case 1 : 
                System.out.print("Banyak Transaksi : (Max 50)");
                while(!valid){
                    temp = input.nextInt();
                    if(temp < 51 && temp > 0){
                        valid = true;
                    }
                }
                makeRandomTransactionToCSV(temp, newRule.getToko());
                break;
            case 2 :
                System.out.println("List Barang : ");
                i = 1;
                for(Barang b : newRule.getToko().GetAllBarang()){
                    System.out.println(i + ". " + b.GetName());
                    i++;
                }
                while(inputBrgLagi == 1 && listInputBrg.size() < 4){
                    System.out.print("Input No. Barang (barang duplikasi otomatis dihapus, max 3 barang): ");
                    inputBrg = input.nextInt();
                    listInputBrg.add(inputBrg);
                    System.out.print("Input Barang Lagi? (1=Lg/0=Tdk): "); 
                    inputBrgLagi = input.nextInt();
                }
                listInputBrg = removeDuplicates(listInputBrg);
                int[] arr = new int[listInputBrg.size()];
                int index = 0;
                for (final Integer value: listInputBrg) {
                   arr[index] = value;
                   System.out.println(arr[index]);
                   index++;
                   
                }
                double[] conf = newRule.CalculateConfidence(newRule.getToko().getData(), arr);
                for (i=0;i<conf.length;i++){
                    System.out.println(i+1 + ".");
                    System.out.println("Confidence Factor to : " + newRule.getToko().searchBarangByID(i+1) + " is : " + newRule.getConfidenceFactor()[i]);
                    System.out.println("Confidence to : " + newRule.getToko().searchBarangByID(i+1) + " is : " + conf[i]);
                }
                System.out.println(); 
                System.out.println("Treshold : " + newRule.CalculateandGetThreshold()); 
                System.out.println("Rekomendasi : ");          
                newRule.Recommend();
                break;
            case 3 :
                newRule.printAllData();
                break;
            case 0 :
                break;
        }
        
    }
    
    public static void main(String[] args) throws IOException {
        int menu = 1;
        ArrayList<String> listbrgdiToko = new ArrayList<>();
        ArrayList<String> listpembelian = new ArrayList<>();
        listbrgdiToko.add("laptop");
        listbrgdiToko.add("mouse");
        listbrgdiToko.add("keyboard");
        listbrgdiToko.add("headset");
        
        /* Buat object toko, dengan list barang yang telah dibuat diatas */
        Toko myToko = Toko.CreateToko(listbrgdiToko);

        /* Generate random transaction ke dalam csv , lalu ambil datanya */
        makeRandomTransactionToCSV(50, myToko);
        inputTransactionFromCSV(listpembelian,myToko);  //Disini, support sudah terhitung.

        /* initialisasi Association Rules utk menghitung confidence */
        AssociationRules newRule = CreateAssociationRules(listbrgdiToko.size(), myToko);
        
        while(menu != 0){
            menu = mainMenu();
            mainMenuSwitch(menu, newRule);
        }
        newRule.printAllData();
        int[] arr = {1,2};
        double[] conf = newRule.CalculateConfidence(newRule.getToko().getData(), arr);
                for (int i=0;i<conf.length;i++){
                    System.out.println(i+1 + ".");
                    System.out.println("Confidence Factor to : " + newRule.getToko().searchBarangByID(i+1) + " is : " + newRule.getConfidenceFactor()[i]);
                    System.out.println("Confidence to : " + newRule.getToko().searchBarangByID(i+1) + " is : " + conf[i]);
                }
                System.out.println(); 
                System.out.println("Treshold : " + newRule.CalculateandGetThreshold()); 
                System.out.println("Rekomendasi : ");          
                newRule.Recommend();
    }

       
        
}
