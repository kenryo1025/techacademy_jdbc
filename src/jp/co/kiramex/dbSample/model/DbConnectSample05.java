package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DbConnectSample05 {

    public static void main(String[] args) {
        // 3. データベース接続と結果取得のための変数宣言
        Connection con =null;
        PreparedStatement spstmt=null;
        PreparedStatement ipstmt=null;
        ResultSet rs=null;

        try {
            // 1. ドライバのクラスをJava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2. DBと接続する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/world?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "Kenryo_2015"
                    );
            // 4. DBとやりとりする窓口（Statementオブジェクト）の作成
            String slectSql="SELECT * FROM city WHERE CountryCode =?";
            spstmt = con.prepareStatement(slectSql);

            // 5, 6. Select文の実行と結果を格納／代入
            System.out.println("CountryCodeを入力してください>");
            String str1 = keyIn();

            spstmt.setString(1,str1);

            rs=spstmt.executeQuery();

            // 7. 結果を表示する
            System.out.println("更新前===================");
            while(rs.next()) {
                //Name列の値を取得
                String name =rs.getString("Name");
                String countryCode =rs.getString("CountryCode");
                String district =rs.getString("District");
                int population = rs.getInt("Population");
                //取得した値を表示
                System.out.println(name+"\t"+countryCode+"\t"+district+"\t"+population);
            }

            System.out.println("更新処理実行=============");

            String insertSql = "INSERT INTO city(Name,CountryCode,District,Population) VALUES ('Rafah',?,'Rafah',?)";
            ipstmt = con.prepareStatement(insertSql);

            System.out.print("Populationを数字で入力してください");
            int num1=keyInNum();

            ipstmt.setString(1,str1);
            ipstmt.setInt(2, num1);

            int count = ipstmt.executeUpdate();
            System.out.println("更新行数："+count);


            rs.close();
            System.out.println("更新後=========");
            rs = spstmt.executeQuery();
            while(rs.next()) {
                //Name列の値を取得
                String name =rs.getString("Name");
                String countryCode =rs.getString("CountryCode");
                String district =rs.getString("District");
                int population = rs.getInt("Population");
                //取得した値を表示
                System.out.println(name+"\t"+countryCode+"\t"+district+"\t"+population);
            }

            // 8. 接続を閉じる
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        }finally {
            if(rs!= null) {
                try {
                    rs.close();
                }catch(SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if(ipstmt != null) {
                try {
                    ipstmt.close();
                }catch(SQLException e) {
                    System.err.println("PreparedStatementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if(spstmt != null) {
                try {
                    spstmt.close();
                }catch(SQLException e) {
                    System.err.println("PreparedStatementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if(con !=null) {
                try {
            //8.接続を閉じる
            con.close();
            }catch(SQLException e) {
                System.err.println("データベースに異常が発生しました。");
                e.printStackTrace();
            }
        }
    }
}
    private static String keyIn() {
        String line=null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        }catch(IOException e) {

        }
        return line;
    }

    private static int keyInNum() {
        int result=0;
        try {
            result =Integer.parseInt(keyIn());
        }catch(NumberFormatException e) {
        }
        return result;
    }
}