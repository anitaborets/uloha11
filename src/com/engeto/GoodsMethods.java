package com.engeto;

import java.math.BigDecimal;
import java.util.List;

public interface GoodsMethods {

    //pridava Objekt do DB
    static void saveItem(Item item) {
    };

    //vrati Objekt podle id
    static Item loadItemById(Integer id) {
        return null;
    }


    //odstranit tovary ktorych stav na sklade = 0
    static void deleteAllOutOfStockItems() {
    }

    //vrati seznam ojektu
   static  List<Item> loadAllAvailableItems() {
        return null;
   }

    //meni cenu
    static void updatePrice(Integer id, BigDecimal newPrice) {
            }
}
