package com.engeto;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main implements GoodsMethods {

    private static final String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
    private static final String user = "root";
    private static final String password = "root";
    private static Connection con;
    public static Statement stmt;
    private static ResultSet rs;


    public static void main(String[] args) throws SQLException {
        Item row = new Item();
        row.setName("Kreslo");
        row.setDescription("Kreslo cervene");
        row.setSerialNo("25056");
        row.setPartNo("8");
        row.setNumberInStock(12);
        row.setPrice(BigDecimal.valueOf(480));

        //metoda 1
        saveItem(row);

        Scanner scanner = new Scanner(System.in);
        System.out.println("*******************");
        System.out.println("Zadajte ID");
        Integer position = scanner.nextInt();
        System.out.println(loadItemById(position));

        //metoda 2
       deleteAllOutOfStockItems();

        //metoda 3
        ArrayList <Item> list = new ArrayList<>(loadAllAvailableItems());
        int count = 1;
        System.out.println("*******************");
        System.out.println("Tovar na sklade");
        for (Item item : list){
            System.out.print(count + ". ");
            System.out.println(item);
            count ++;
        }

        //metoda 4
        System.out.println("*******************");
        System.out.println("Nová cena tovaru bude 1000 CZK");
        System.out.println("Zadajte ID");
        Integer idChangePrice = scanner.nextInt();

        //metoda 5
        updatePrice(idChangePrice, BigDecimal.valueOf(1000));
        System.out.println(loadItemById(idChangePrice));
    }

        //metody - kod
    private static Connection getConnection() {

            Connection dbConnection = null;
            try {
                dbConnection = DriverManager.getConnection(url, user, password);
                return dbConnection;
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
            return dbConnection;
            }


    private static void saveItem (Item item)  {
        String insertTableSQL =  "INSERT INTO uloha11.item (partNo,serialNo,name, numberInStock, description, price)" + "VALUES ("
                + item.getPartNo() + "," + item.getSerialNo() + ",'" + item.getName()+ "'," + item.getNumberInStock()
                +  ",'" + item.getDescription() + "'," + item.getPrice() + ");";

       try (Connection conn = DriverManager.getConnection(url, user, password)) {
           Statement statement = conn.createStatement();
           statement.executeUpdate(insertTableSQL);
      }
      catch(Exception e){
       System.out.println("Connection failed...");
        }
    }

    private static Item loadItemById(Integer position) throws SQLException {
        Item item = new Item();
        String query = "SELECT * FROM uloha11.item WHERE id = " + position + ";";
        Statement statement = getConnection().createStatement();
        ResultSet result = statement.executeQuery(query);
        while (result.next()){
            String partNumber = result.getString("partNo");
            item.setPartNo(partNumber);
            String serialNumber = result.getString("serialNo");
            item.setSerialNo(serialNumber);
            String name = result.getString("name");
            item.setName(name);
            String descript = result.getString("description");
            item.setDescription(descript);
            int amount = result.getInt("numberInStock");
            item.setNumberInStock(amount);
            int price = result.getInt("price");
            item.setPrice(BigDecimal.valueOf(price));
        }
        return item;
    }


    private static void deleteAllOutOfStockItems() {
        String delete = "DELETE FROM uloha11.item WHERE NumberInStock = 0;";
        try (Connection conn = DriverManager.getConnection(url, user, password)){
            Statement statement = conn.createStatement();
            statement.executeUpdate(delete);
            System.out.println("*******************");
            System.out.println("Nulové položky odstranene");
        }
        catch(Exception e) {
            System.out.println("Connection failed...");
        }
    }


    private static List<Item> loadAllAvailableItems() {
        String query = "SELECT * FROM uloha11.item;";
        try {
        ArrayList <Item> list = new ArrayList<>();
        Statement statement = getConnection().createStatement();
        ResultSet result = statement.executeQuery(query);
        while (result.next()) {
            Item item = new Item();
            String partNumber = result.getString("partNo");
            item.setPartNo(partNumber);
            String serialNumber = result.getString("serialNo");
           item.setSerialNo(serialNumber);
            String name = result.getString("name");
            item.setName(name);
           String descript = result.getString("description");
           item.setDescription(descript);
           int amount = result.getInt("numberInStock");
           item.setNumberInStock(amount);
           int price = result.getInt("price");
           item.setPrice(BigDecimal.valueOf(price));

           list.add(item);
        }
                return list;
    }
        catch (SQLException exception) {
            System.out.println("Connection failed...");
        }
        return null;
    }

    private static void updatePrice(Integer id, BigDecimal newPrice) {
        String updatePrice = "UPDATE uloha11.item SET price =" + newPrice + " WHERE id = " + id + ";";
         try (Connection conn = DriverManager.getConnection(url, user, password)){
            Statement statement = conn.createStatement();
            statement.executeUpdate(updatePrice);
            System.out.println("*******************");
            System.out.println("Cena je zmenena");
        }
        catch(Exception e) {
            System.out.println("Connection failed...");
        }
    }

}
