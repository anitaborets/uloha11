SELECT * FROM uloha11.item;
INSERT INTO uloha11.item (partNo,serialNo,name,description,numberInStock,price) VALUES ('47','32768',"Komoda", "Komoda fialova",0,360);
DELETE FROM uloha11.item WHERE price = 480;
UPDATE uloha11.item SET price =380 WHERE id = 88;