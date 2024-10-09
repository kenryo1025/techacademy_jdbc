package jp.co.kiramex.dbSample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import jp.co.kiramex.dbSample.dao.CountryDAO;
import jp.co.kiramex.dbSample.entity.Country;

public class DbConnectSample06 {

    public static void main(String[] args) {
        CountryDAO dao=new CountryDAO();

        System.out.println("検索ワードを入力してください");
        String name= keyIn();

        List<Country> list = dao.getCountryFromName(name);

        for(Country item:list) {
            System.out.println(item.getName());
            System.out.println(item.getPopulation());
        }
    }

    private static String keyIn() {
        String line=null;
        try {
            BufferedReader key =new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        }catch(IOException e) {
        }
        return line;
    }

}
