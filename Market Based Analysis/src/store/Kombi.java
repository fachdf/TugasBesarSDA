package store;
/*
* To change this license header, choose License Headers in Project
Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
*
* @author urip
*/
public class Kombi {

    static int column[];
    static ArrayList<String> ndx;
    static ArrayList<ArrayList<String>> ndxs;
    static int ke;

    public static void combinations(int k, int r, int n)
    {
        column[k]=column[k-1];
        while(column[k] < n-r+k)
        {
            column[k]=column[k]+1;
            if(k<r)
            combinations(k+1,r,n);
            else
            {
                 ndx = new ArrayList<String>();
                 for(int j=1; j<=r; j++)
                {
                     ndx = new ArrayList<String>();
                     for(j=1; j<=r; j++)
                    {
                         ndx.add(String.valueOf(column[j]));
                     }
                     ke++;
                     ndxs.add(ke, ndx);
                     ndx=ndxs.get(ke);
                 }
            }
        }
    }
    public static ArrayList<ArrayList<String>> generate (int n)
    {
        // TODO code application logic here
        column=new int[n+1];
        ndxs = new ArrayList<ArrayList<String>>();
        ke=-1;
        for(int r=1; r<=n; r++)
        {
            column[0]=0;
            combinations(1,r,n);
        }
         return ndxs;
     }
    
    /* manual generation */
    public ArrayList<ArrayList<String>> getCombiFromInput(char[] input) {
        ArrayList<String> r = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<ArrayList<String>> hasil = new ArrayList<>();
        Arrays.sort(input);
        return getCombiFromInput(input, 0, r, hasil, temp);
    }

    public ArrayList<ArrayList<String>> getCombiFromInput(char[] input, int pos, ArrayList<String> r, ArrayList<ArrayList<String>> hasil, ArrayList<String> temp) {

        temp = new ArrayList<>(r);
        hasil.add(temp);
        //r.forEach(r1 -> System.out.print(r1 + " "));
        
        //System.out.println();
        for (int i = pos; i < input.length; i++) {
            if (i != pos && input[i] == input[i-1]) {
                continue;
            }
            r.add(String.valueOf(input[i]));
            
            getCombiFromInput(input, i + 1, r, hasil,temp);
            r.remove(r.size() - 1);
        }
        return hasil;
    }
}
