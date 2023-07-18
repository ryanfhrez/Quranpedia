/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shahih;

import java.io.*;
import java.util.StringTokenizer;
import java.sql.*;
import shahih.utilities.DiacriticsRemover;
/**
 *
 * @author ryan
 */
public class SearchKataBendaDiAyatAlquran {
     public static void main(String args[]) {
        String DB_URL = "jdbc:mysql://localhost/shahihmuslim_db?characterEncoding=UTF-8";
        String DB_USER = "root";
        String DB_PASS = "";
        Connection conn;
        Statement stmt;
        ResultSet rs;
        DiacriticsRemover diacriticRemover = new DiacriticsRemover();
        try {
            // Set the console's encoding to UTF-8
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, "UTF-8"));

            //Saving Tokenizer with prefix AL
            String[] token = new String[500_000];
            String[] kataBenda = new String[500_000];

            StringTokenizer tokenizer;
            
            int i, j, k;
            //initialization tokenizer
            for (i = 0; i < 500_000; i++) {
                token[i] = "";
                kataBenda[i] = "";
            }

            //processing database
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.createStatement();

            String sql = "SELECT * FROM `quran_id`";
            rs = stmt.executeQuery(sql);
            i = 0;
            j = 0;
            k = 0;
            while (rs.next()) {

                tokenizer = new StringTokenizer(rs.getString("ayahText"), " ");

                while (tokenizer.hasMoreTokens()) {
                    token[i++] = tokenizer.nextToken();
                }

                while (j < i) {
                    if (token[j].startsWith("ال")) {
                        kataBenda[k++] = token[j];
                    }
                    j++;
                }

            }


            for (j = 0; j < i; j++) {
                System.out.println(diacriticRemover.removeDiacritics(kataBenda[j]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

} // end class }
