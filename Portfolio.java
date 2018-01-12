/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Kim
 */
public class Portfolio extends JFrame {

    public static final int HEIGHT = 400;
    public static final int WIDTH = 590;
    public static final int SF_NONEMPTY = 0;
    public static final int SF_ISINT = 1;
    public static final int SF_ISDOUBLE = 2;
    public static final int SF_ISDOUBLE2 = 3;
    private JTextArea textArea;
    private JTextArea messagesArea;
    private JScrollPane scrolledMessages;
    private JPanel commandsPanel;
    private JPanel buyPanel;
    private JPanel sellPanel;
    private JPanel updatePanel;
    private JPanel getGainPanel;
    private JPanel searchPanel;
    private JPanel bigPanel;
    private JComboBox<String> investmentsType;
    private JTextField symbolField;
    private JTextField nameField;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField keywordsField;
    private JTextField lowPriceField;
    private JTextField highPriceField;
    private JTextField getGainField;
    private JButton prevButton;
    private JButton nextButton;

    private String type;
    private String symbol;
    private String name;
    private int quantity;
    private double price;
    private String keywords;
    private double lowPrice;
    private double highPrice;
    private int count = 0;
    private static String filename;

    private ArrayList<Investment> investment;
    //HashMap<String, ArrayList<Integer>> hash = new HashMap<>();

    private class BuyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //Change interface for buying
            buyGui();

        }
    }

    private class SellListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //Open a window for selling
            sellGui();
        }

    }

    private class UpdateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //Open a window for buy
            updateGui();
        }

    }

    private class GetGainListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //Open a window for buy
            GainGui();
        }

    }

    private class SearchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //Open a window for search
            searchGui();
        }

    }

    private class QuitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            print();
            saveInvestments(filename);
            System.exit(0);
        }

    }

    public void print() {
        for (int i = 0; i < this.investment.size(); i++) {
            System.out.println(this.investment.get(i));
        }
    }

    private class ResetBListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            investmentsType.setSelectedIndex(0);
            symbolField.setText("");
            nameField.setText("");
            quantityField.setText("");
            priceField.setText("");
            messagesArea.setText("");
        }
    }

    private class ResetSListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            symbolField.setText("");
            quantityField.setText("");
            priceField.setText("");
            messagesArea.setText("");
        }
    }

    private class ResetSearchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            symbolField.setText("");
            keywordsField.setText("");
            lowPriceField.setText("");
            highPriceField.setText("");
            messagesArea.setText("");
        }
    }

    private class TypeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            type = null;
            JComboBox tempType = (JComboBox) ae.getSource();
            type = (String) tempType.getSelectedItem();
        }
    }

    private class BuyButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            symbol = null;
            name = null;
            quantity = 0;
            price = 0.0;
            //In case the user did not change the type button because he just wants stock...
            if (type == null) {
                type = "Stock";
            }
            if (symbol == null) {
                if (!validateFormat(symbolField.getText(), SF_NONEMPTY)) {
                    investmentsType.setSelectedIndex(0);
                    symbolField.setText("");
                    nameField.setText("");
                    quantityField.setText("");
                    priceField.setText("");
                    messagesArea.setText("Error Messages.\nSymbol field cannot be empty\nPlease try again!");
                } else {
                    symbol = symbolField.getText();
                    if (name == null) {
                        if (!validateFormat(nameField.getText(), SF_NONEMPTY)) {
                            investmentsType.setSelectedIndex(0);
                            symbolField.setText("");
                            nameField.setText("");
                            quantityField.setText("");
                            priceField.setText("");
                            messagesArea.setText("Error Messages.\nName field cannot be empty\nPlease try again!");
                        } else {
                            name = nameField.getText();
                            if (quantity == 0) {
                                if (!validateFormat(quantityField.getText(), SF_ISINT)) {
                                    investmentsType.setSelectedIndex(0);
                                    symbolField.setText("");
                                    nameField.setText("");
                                    quantityField.setText("");
                                    priceField.setText("");
                                    messagesArea.setText("Error Messages.\nQuantity field cannot be empty or invalid input\nPlease try again!");
                                } else {
                                    quantity = Integer.parseInt(quantityField.getText().trim());
                                    if (price == 0.0) {
                                        if (!validateFormat(priceField.getText(), SF_ISDOUBLE)) {
                                            investmentsType.setSelectedIndex(0);
                                            symbolField.setText("");
                                            nameField.setText("");
                                            quantityField.setText("");
                                            priceField.setText("");
                                            messagesArea.setText("Error Messages.\nPrice field cannot be empty or invalid input\nPlease try again!");
                                        } else {
                                            price = Double.parseDouble(priceField.getText().trim());
                                            addInvestment(type, symbol, name, quantity, price);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } //the first if finishes here so we have type,symbol,name, quantity and price
        }
    }

    /**
     * This function takes 5 paarameters which are the attributes of an investment. This functions checks if the arrayList is empty first,
     * then verify if the type chosen by the user is stock or mutual funds. Then add the investment to the arrayList if it does not exist,
     * otherwise it updates the price and quantity of the investment, the bookvalue is updated as well
     * @param type represents a stock investment or mutual fund investment
     * @param symbol represents the symbol of the investment
     * @param name represents the name of the investment
     * @param quantity represents the quantity of the investment
     * @param price represents the price of the investment
     */
    public void addInvestment(String type, String symbol, String name, int quantity, double price) {

        //The list is not empty
        if (!this.investment.isEmpty()) {
            if ("stock".contains(type.toLowerCase().trim())) {
                for (int i = 0; i < this.investment.size(); i++) {
                    if (this.investment.get(i).getSymbol().equals(symbol)) {
                        //System.out.println("This is already in the list and we are updating the quantity");
                        //Updating quantity and price
                        Stock addStock = new Stock();
                        int tempQuantity = this.investment.get(i).getQuantity();
                        tempQuantity = quantity + tempQuantity;
                        this.investment.get(i).setQuantity(tempQuantity);
                        this.investment.get(i).setPrice(price);
                        double newBookValue = addStock.computeBookValueForBuying(quantity, price);
                        //  System.out.println("CHECKING newBOOKVALUE while updatin: " + newBookValue);
                        double tempBookValue = this.investment.get(i).getBookValue();
                        tempBookValue = tempBookValue + newBookValue;
                        this.investment.get(i).setBookValue(tempBookValue);
                        messagesArea.setText("Stock's quantity has been successfully updated to Portfolio");
                        return;
                    }
                }
                //does not match any, just add the new stock
                Stock temp = new Stock();
                double tempBookValue = temp.computeBookValueForBuying(quantity, price);
                Stock tempStock = new Stock(symbol, name, quantity, price, tempBookValue);
                this.investment.add(tempStock);
                messagesArea.setText("Stock has been successfully added to Portfolio");
            } else if ("mutual funds".contains(type.toLowerCase().trim())) {
                for (int i = 0; i < this.investment.size(); i++) {
                    if (this.investment.get(i).getSymbol().equals(symbol)) {
                       // System.out.println("This is already in the list and we are updating the quantity");
                        //Updating quantity and price
                        MutualFunds addFunds = new MutualFunds();
                        int tempQuantity = this.investment.get(i).getQuantity();
                        tempQuantity = quantity + tempQuantity;
                        this.investment.get(i).setQuantity(tempQuantity);
                        this.investment.get(i).setPrice(price);
                        double newBookValue = addFunds.computeBookValueForBuying(quantity, price);
                        double tempBookValue = this.investment.get(i).getBookValue();
                        tempBookValue = tempBookValue + newBookValue;
                        this.investment.get(i).setBookValue(tempBookValue);
                        messagesArea.setText("Mutual Funds' quantity has been successfully updated to Portfolio");
                        return;
                    }
                }
                //does not match any, just add the new stock
                MutualFunds temp = new MutualFunds();
                double tempBookValue = temp.computeBookValueForBuying(quantity, price);
                MutualFunds tempFund = new MutualFunds(symbol, name, quantity, price, tempBookValue);
                this.investment.add(tempFund);
                messagesArea.setText("Mutual Funds has been successfully added to Portfolio");
            }
        } else { //Adding to an empty list
            if ("stock".contains(type.toLowerCase().trim())) {
                Stock temp = new Stock();
                double tempBookValue = temp.computeBookValueForBuying(quantity, price);
                Stock tempStock = new Stock(symbol, name, quantity, price, tempBookValue);
                this.investment.add(tempStock);
                messagesArea.setText("Stock has been successfully added to Portfolio");
            } else if ("mutual funds".contains(type.toLowerCase().trim())) {
                MutualFunds temp = new MutualFunds();
                double tempBookValue = temp.computeBookValueForBuying(quantity, price);
                MutualFunds tempFund = new MutualFunds(symbol, name, quantity, price, tempBookValue);
                this.investment.add(tempFund);
                messagesArea.setText("Mutual Funds has been successfully added to Portfolio");
            } else {
                System.out.println("Unexpected type of investment");
            }
        }
    }

    /**
     * Inner class listerner for the sell buttons in the selling interface
     * Gets the symbol, quantity and price of the investment to be sold and call the sellInvestments method to update the portfolio
     */
    private class SellButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            symbol = null;
            quantity = 0;
            price = 0.0;

            if (symbol == null) {
                if (!validateFormat(symbolField.getText(), SF_NONEMPTY)) {
                    symbolField.setText("");
                    quantityField.setText("");
                    priceField.setText("");
                    messagesArea.setText("Error Messages.\nSymbol field cannot be empty\nPlease try again!");
                } else {
                    symbol = symbolField.getText();
                    if (quantity == 0) {
                        if (!validateFormat(quantityField.getText(), SF_ISINT)) {
                            symbolField.setText("");
                            quantityField.setText("");
                            priceField.setText("");
                            messagesArea.setText("Error Messages.\nQuantity field cannot be empty or invalid input\nPlease try again!");
                        } else {
                            quantity = Integer.parseInt(quantityField.getText().trim());
                            if (price == 0.0) {
                                if (!validateFormat(priceField.getText(), SF_ISDOUBLE)) {
                                    symbolField.setText("");
                                    quantityField.setText("");
                                    priceField.setText("");
                                    messagesArea.setText("Error Messages.\nPrice field cannot be empty or invalid input\nPlease try again!");
                                } else {
                                    price = Double.parseDouble(priceField.getText().trim());
                                   // System.out.println("The price is" + price);
                                    sellInvestments(symbol, quantity, price);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * This function takes in 3 paramaters and its main purpose is to update the quantity and bookvalue of the investment which has 
     * be sold. If all that investment is sold, then it is removed from the arrayList
     * @param symbol represents the symbol of the investment to be sold
     * @param quantity represents the quantity to be sold
     * @param price represents the price at which the investment is to be sold
     */
    public void sellInvestments(String symbol, int quantity, double price) {
        if (!this.investment.isEmpty()) {
            for (int i = 0; i <= this.investment.size() - 1; i++) {
                if (this.investment.get(i).getSymbol().equals(symbol)) {
                    int availableQuantity = this.investment.get(i).getQuantity();
                    if (!checkAvailability(quantity, i)) {
                        symbolField.setText("");
                        quantityField.setText("");
                        priceField.setText("");
                        messagesArea.setText("Error Message.\nThe quantity you input is invalid.Reason: Current quantity is less than the quantity you want to sell\nPlease try again!");
                        return;
                    }
                    int remainingQuantity = availableQuantity - quantity;
                    Investment tempInvestment = new Investment();
                    if (remainingQuantity > 0) {
                        //Update price
                        this.investment.get(i).setPrice(price);
                        //Now to update bookValue, need to know whether is it a stock or funds
                        if (this.investment.get(i).getClass().getCanonicalName().equals("assignment2.Stock")) {
                            // Stock s = (Stock) tempInvestment;
                            Stock s = new Stock();
                            double initialBkValue = this.investment.get(i).getBookValue();
                          //  System.out.println("the quantities are " + availableQuantity + " & " + remainingQuantity + " & " + initialBkValue);
                            double finalBkValue = s.computeBookValueForSelling(availableQuantity, remainingQuantity, initialBkValue);
                            this.investment.get(i).setBookValue(finalBkValue);
                            this.investment.get(i).setQuantity(remainingQuantity);
                            double stockPayment = s.computePayment(quantity, price);
                          //  System.out.println("Your payment for selling " + quantity + "stocks is $ " + stockPayment);
                          //  System.out.println("Your initial bookvalue is " + initialBkValue + " and finalbk :" + finalBkValue);
                            messagesArea.setText("Selling of stock has been successfully");
                            return;
                        } else if (this.investment.get(i).getClass().getCanonicalName().equals("assignment2.MutualFunds")) {
                            //MutualFunds m = (MutualFunds) tempInvestment;
                            MutualFunds m = new MutualFunds();
                            double initialBkValue = m.getBookValue();
                            double finalBkValue = m.computeBookValueForSelling(availableQuantity, remainingQuantity, initialBkValue);
                            this.investment.get(i).setBookValue(finalBkValue);
                            this.investment.get(i).setQuantity(remainingQuantity);
                            double stockPayment = m.computePayment(quantity, price);
                         //   System.out.println("Your payment for selling " + quantity + "funds is $" + stockPayment);
                            messagesArea.setText("Selling of mutual fund has been successfully");
                            return;
                        }
                    } else {
                        //removing from list
                        boolean remove = this.investment.remove(this.investment.get(i));
                        if (this.investment.get(i).getClass().getCanonicalName().equals("assignment2.Stock")) {
                            Stock s = (Stock) tempInvestment;
                            double stockPayment = s.computePayment(quantity, price);
                          //  System.out.println("Your payment for selling " + quantity + "stocks is $" + stockPayment);
                            return;
                        } else if (this.investment.get(i).getClass().getCanonicalName().equals("assignment2.MutualFunds")) {
                            MutualFunds m = (MutualFunds) tempInvestment;
                            double stockPayment = m.computePayment(quantity, price);
                          //  System.out.println("Your payment for selling " + quantity + "funds is $" + stockPayment);
                            return;
                        }
                    }
                }
            }
            symbolField.setText("");
            quantityField.setText("");
            priceField.setText("");
            messagesArea.setText("Error Message.\nThis investment does not exist\nPlease try again!");
        } else {
           // System.out.println("Cannot do a search if list is empty");
            symbolField.setText("");
            quantityField.setText("");
            priceField.setText("");
            messagesArea.setText("Error Message.\nSelling cannot be done because there is no investments in the portfolio\n");
        }
    }

    /**
     * This checks whether the quantity to be sold is less than the quantity available in the portfolio
     * @param toSell an integer which represent the amount of stock the user
     * wishes to sell
     * @param k an integer to indicate which stock we are selling in the
     * arrayList
     * @return true is the amount of available stock is greater than what is
     * requested
     */
    public boolean checkAvailability(int toSell, int k) {
        int availableQuantity = this.investment.get(k).getQuantity();
        return availableQuantity >= toSell;
    }

    /**
     * PrevButtonListener inner class for the button prev
     */
    private class PrevButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
                int lastIndex = lastInvestmentIndex();
                count--;
                if (count == 0) {
                    prevButton.setEnabled(false);
                    nextButton.setEnabled(true);
                    getPrevious(count);
                } else {
                    nextButton.setEnabled(true);
                    prevButton.setEnabled(true);
                    getPrevious(count);
                }
        }
    }
    /**
     * This method gets the symbol and name of an investment and sets these value to the textfield for symbol and name
     * @param i indicates the location of the investment to retrieved from the arrayList
     */
    private void getPrevious(int i) {
        symbolField.setText(this.investment.get(i).getSymbol());
        nameField.setText(this.investment.get(i).getName());
        priceField.setText("");
    }

    /**
     * NextButtonListener inner class for nextButton
     * This allows the user to view the next investment
     */
    private class NextButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
                int lastIndex = lastInvestmentIndex();
                count++;
                if (lastIndex - 1 == count) {
                    nextButton.setEnabled(false);
                    prevButton.setEnabled(true);
                    getNext(count);
                } else if (count == 0) {
                    prevButton.setEnabled(false);
                    nextButton.setEnabled(true);
                    getNext(count);
                } else if (count > 0 && count < lastIndex - 1) {
                    prevButton.setEnabled(true);
                    nextButton.setEnabled(true);
                    getNext(count);
                }
        }
    }
    
    /**
     * This method returns the size of the arrayList of investments
     * @return the size of the arrayList investment
     */
    private int lastInvestmentIndex() {
        return this.investment.size();
    }

    /**
     * This gets the symbol and name of an investments and set it to the textfield for symbol and name
     * @param i indicates the location of the investment in the arrayList
     */
    private void getNext(int i) {
        symbolField.setText(this.investment.get(i).getSymbol());
        nameField.setText(this.investment.get(i).getName());
        priceField.setText("");
    }

    /**
     * SaveButtonListener inner class for saveButton
     * Gets the new updated price from the textfield, verify that it is a double and call the method updatePrice 
     */
    private class SaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            price = 0.0;
            if (price == 0.0) {
                if (!validateFormat(priceField.getText(), SF_ISDOUBLE)) {
                    //symbolField.setText("");
                    priceField.setText("");
                    messagesArea.setText("Error Messages.\nPrice field cannot be empty or invalid input while trying to update the price\nPlease try again!");
                } else {
                    messagesArea.setText("");
                    price = Double.parseDouble(priceField.getText().trim());
                    updatePrice(count);
                }
            }

        }
    }

    /**
     * This sets the price of the investment to the new price and display a message and the details of the investment to the screen 
     * @param i indicates the location of the investment to be updated
     */
    private void updatePrice(int i) {
        this.investment.get(i).setPrice(price);
        messagesArea.append("Investment's price has been successfully updated\n");
        messagesArea.append(this.investment.get(i).toString());
        priceField.setText("");
    }
    
    /**
     * SearchButtonListener inner class for the searchButton
     * Gets the values for symbol, keywords, low price and high price
     */
    private class SearchButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            symbol = null;
            keywords = null;
            lowPrice = 0.0;
            highPrice = 0.0;

            if (symbol == null) {
                symbol = symbolField.getText();
                if (keywords == null) {
                    keywords = keywordsField.getText();
                    if (lowPrice == 0.0) {
                        if (!validateFormat(lowPriceField.getText(), SF_ISDOUBLE2)) {
                            symbolField.setText("");
                            keywordsField.setText("");
                            lowPriceField.setText("");
                            highPriceField.setText("");
                            messagesArea.setText("Error Messages.\nLow Price field cannot be empty or invalid input\nPlease try again!");
                        } else {
                            if (lowPriceField.getText().isEmpty()) {
                                lowPrice = 0.0;
                            } else {
                                lowPrice = Double.parseDouble(lowPriceField.getText().trim());
                            }
                            if (highPrice == 0.0) {
                                if (!validateFormat(highPriceField.getText(), SF_ISDOUBLE2)) {
                                    symbolField.setText("");
                                    keywordsField.setText("");
                                    lowPriceField.setText("");
                                    highPriceField.setText("");
                                    messagesArea.setText("Error Messages.\nHigh Price field cannot be empty or invalid input\nPlease try again!");
                                } else {
                                    if (highPriceField.getText().isEmpty()) {
                                        highPrice = 0.0;
                                    } else {
                                        highPrice = Double.parseDouble(highPriceField.getText().trim());
                                    }
                                    if (lowPrice > highPrice && highPrice != 0.0) {
                                        symbolField.setText("");
                                        keywordsField.setText("");
                                        lowPriceField.setText("");
                                        highPriceField.setText("");
                                        messagesArea.setText("Error Messages.\nHigh Price field cannot be less than Low Price field\nPlease try again!");
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                System.out.println("Error in SearchButtonListener in getting symbol...");
            }
           // System.out.println("symbol: " + symbol + " keywords: " + keywords + " low price: " + lowPrice + " high price: " + highPrice);
            searchInvestment(symbol, keywords, lowPrice, highPrice);
        }
    }

    /**
     * This functions goes through the arrayList and see if there are any investments which matches with the search criteria 
     * @param symbol is used to search if there is any investment which has the same symbol
     * @param keywords represents keywords that would be used to see if there is investments which correspond to them
     * @param lowPrice represents the lower bound of price in which investments should be
     * @param highPrice represents the upper bound of price in which investments should be
     */
    public void searchInvestment(String symbol, String keywords, double lowPrice, double highPrice) {
        messagesArea.setText("");
        int rangeSwitch = 0;   // 0 - not empty and 1 - is empty
        //ArrayList<String> tokens = new ArrayList<>();
        String[] tokens;
        if (lowPrice == 0.0 && highPrice == 0.0) {
            rangeSwitch = 1;
        }

        //first case when all fields are empty
        if ("".equals(symbol) && "".equals(keywords) && lowPrice == 0.0 && highPrice == 0.0) {
            printAllToArea();
        }
        //2nd case where only symbol is filled in   
        if (symbol.length() != 0 && "".equals(keywords) && rangeSwitch == 1) {
            int a = 0;
            for (int i = 0; i <= this.investment.size() - 1; i++) {
                if (this.investment.get(i).getSymbol().equals(symbol)) {
                    printInvestmentToArea(i);
                    a++;
                }
            }
            if (a == 0) {
                messagesArea.setText("No investments corresponding to the search criteria");
            }

        }
        //3rd case, keywords is filled in
        if ("".equals(symbol) && keywords.length() != 0 && rangeSwitch == 1) {
            int b = 0;
            int k = 0;
            int j = 0;
            StringTokenizer words = new StringTokenizer(keywords);
            tokens = new String[words.countTokens()];
            while (words.hasMoreTokens()) {
                tokens[k++] = words.nextToken();
            }
            /*for (j = 0; j < k; j++) {
                System.out.println("Checkin tokens " + tokens[j]);
            }*/
            for (int i = 0; i <= this.investment.size() - 1; i++) {
                boolean stillMatch = true;
                for (j = 0; j < k; j++) {
                    //System.out.println("Comparing against tokens: " + tokens[j] + "comparin with: " + this.investment.get(i).getName().toLowerCase());
                    if (stillMatch == true) {
                        //System.out.println("still match true");
                        int matching = this.investment.get(i).getName().toLowerCase().indexOf(tokens[j].toLowerCase());
                        if (matching == -1) {
                            //System.out.println("Uh oh not matching anymore");
                            stillMatch = false;
                        }
                    }
                }
                if (stillMatch == true) {
                    printInvestmentToArea(i);
                    b++;
                } else {
                    System.out.println("This current investment does not match");
                }
            }
            if (b == 0) {
                messagesArea.setText("No investments corresponding to the search criteria");
            }

        }
        //4th case, price is filled in
        if ("".equals(symbol) && keywords.length() == 0 && rangeSwitch == 0) {
            //low price and high price   
            int c = 0;
            if (highPrice == 0.0) { //price = lowPrice
                for (int i = 0; i <= this.investment.size() - 1; i++) {
                    double tempPrice = this.investment.get(i).getPrice();
                    if (tempPrice == lowPrice) {
                        printInvestmentToArea(i);
                        c++;
                    }
                }
                if (c == 0) {
                    messagesArea.setText("No investments corresponding to the search criteria");
                }
            } else { // lowPrice <price < highPrice
                for (int i = 0; i <= this.investment.size() - 1; i++) {
                    double tempPrice = this.investment.get(i).getPrice();
                    if (tempPrice >= lowPrice && tempPrice <= highPrice) {
                        printInvestmentToArea(i);
                        c++;
                    }
                }
                if (c == 0) {
                    messagesArea.setText("No investments corresponding to the search criteria");
                }
            }
        }
        //5th, symbol and keywords are filled in
        if (symbol.length() != 0 && keywords.length() != 0 && rangeSwitch == 1) {
            int d = 0;
            int k = 0;
            int j = 0;
            StringTokenizer words = new StringTokenizer(keywords);
            tokens = new String[words.countTokens()];
            while (words.hasMoreTokens()) {
                tokens[k++] = words.nextToken();
            }

            for (int i = 0; i <= this.investment.size() - 1; i++) {
                boolean stillMatch = true;
                for (j = 0; j < k; j++) {
                    //System.out.println("Comparing against tokens: " + tokens[j] + "comparin with: " + this.investment.get(i).getName().toLowerCase());
                    if (stillMatch == true) {
                        //System.out.println("still match true");
                        int matching = this.investment.get(i).getName().toLowerCase().indexOf(tokens[j].toLowerCase());
                        if (matching == -1) {
                            //System.out.println("Uh oh not matching anymore");
                            stillMatch = false;
                        }
                    }
                }
                if (stillMatch == true) {
                    if (this.investment.get(i).getSymbol().equals(symbol)) {
                        printInvestmentToArea(i);
                        d++;
                    } else {
                        System.out.println("This current investment does not match");
                    }
                }
            }
            if (d == 0) {
                messagesArea.setText("No investments corresponding to the search criteria");
            }

        }
        //6th, symbol and range are filled in
        if (symbol.length() != 0 && keywords.length() == 0 && rangeSwitch == 0) {
            int e = 0;
            for (int i = 0; i <= this.investment.size() - 1; i++) {
                if (this.investment.get(i).getSymbol().equals(symbol)) {
                    if (highPrice == 0.0) { //price = lowPrice
                        double tempPrice = this.investment.get(i).getPrice();
                        if (tempPrice == lowPrice) {
                            printInvestmentToArea(i);
                            e++;
                        }
                    } else {
                        double tempPrice = this.investment.get(i).getPrice();
                        if (tempPrice >= lowPrice && tempPrice <= highPrice) {
                            printInvestmentToArea(i);
                            e++;
                        }
                    }
                }
            }
            if (e == 0) {
                messagesArea.setText("No investments corresponding to the search criteria");
            }
        }
        //7th, keywords and range are filled in
        if (symbol.length() == 0 && keywords.length() != 0 && rangeSwitch == 0) {
            int f = 0;
            int k = 0;
            int j = 0;
            if (highPrice == 0.0) { //price = lowPrice
                for (int i = 0; i <= this.investment.size() - 1; i++) {
                    double tempPrice = this.investment.get(i).getPrice();
                    if (tempPrice == lowPrice) {
                        StringTokenizer words = new StringTokenizer(keywords);
                        tokens = new String[words.countTokens()];
                        while (words.hasMoreTokens()) {
                            tokens[k++] = words.nextToken();
                        }

                        boolean stillMatch = true;
                        for (j = 0; j < k; j++) {
                            //System.out.println("Comparing against tokens: " + tokens[j] + "comparin with: " + this.investment.get(i).getName().toLowerCase());
                            if (stillMatch == true) {
                                //System.out.println("still match true");
                                int matching = this.investment.get(i).getName().toLowerCase().indexOf(tokens[j].toLowerCase());
                                if (matching == -1) {
                                    //System.out.println("Uh oh not matching anymore");
                                    stillMatch = false;
                                }
                            }
                        }
                        if (stillMatch == true) {
                            printInvestmentToArea(i);
                            f++;
                        } else {
                            System.out.println("This current investment does not match");
                        }
                    }
                }
                if (f == 0) {
                    messagesArea.setText("No investments corresponding to the search criteria");
                }
            } else { // lowPrice <price < highPrice
                for (int i = 0; i <= this.investment.size() - 1; i++) {
                    double tempPrice = this.investment.get(i).getPrice();
                    if (tempPrice >= lowPrice && tempPrice <= highPrice) {
                        StringTokenizer words = new StringTokenizer(keywords);
                        tokens = new String[words.countTokens()];
                        while (words.hasMoreTokens()) {
                            tokens[k++] = words.nextToken();
                        }
                        
                        boolean stillMatch = true;
                        for (j = 0; j < k; j++) {
                            //System.out.println("Comparing against tokens: " + tokens[j] + "comparin with: " + this.investment.get(i).getName().toLowerCase());
                            if (stillMatch == true) {
                                //System.out.println("still match true");
                                int matching = this.investment.get(i).getName().toLowerCase().indexOf(tokens[j].toLowerCase());
                                if (matching == -1) {
                                    //System.out.println("Uh oh not matching anymore");
                                    stillMatch = false;
                                }
                            }
                        }
                        if (stillMatch == true) {
                            printInvestmentToArea(i);
                            f++;
                        } else {
                            System.out.println("This current investment does not match");
                        }
                    }
                }
                if (f == 0) {
                    messagesArea.setText("No investments corresponding to the search criteria");
                }
            }

        }
        //8th, all filled in
        if (symbol.length() != 0 && keywords.length() != 0 && rangeSwitch == 0) {
            int g = 0;
            int k = 0;
            int j = 0;
            if (highPrice == 0.0) { //price = lowPrice
                for (int i = 0; i <= this.investment.size() - 1; i++) {
                    double tempPrice = this.investment.get(i).getPrice();
                    if (tempPrice == lowPrice) {
                        StringTokenizer words = new StringTokenizer(keywords);
                        tokens = new String[words.countTokens()];
                        while (words.hasMoreTokens()) {
                            tokens[k++] = words.nextToken();
                        }

                        boolean stillMatch = true;
                        for (j = 0; j < k; j++) {
                            //System.out.println("Comparing against tokens: " + tokens[j] + "comparin with: " + this.investment.get(i).getName().toLowerCase());
                            if (stillMatch == true) {
                                //System.out.println("still match true");
                                int matching = this.investment.get(i).getName().toLowerCase().indexOf(tokens[j].toLowerCase());
                                if (matching == -1) {
                                    //System.out.println("Uh oh not matching anymore");
                                    stillMatch = false;
                                }
                            }
                        }
                        if (stillMatch == true) {
                            if (this.investment.get(i).getSymbol().equals(symbol)) {
                                printInvestmentToArea(i);
                                g++;
                            } else {
                                System.out.println("This current investment does not match");
                            }
                        } else {
                            System.out.println("This current investment does not match");
                        }
                    }
                }
                if (g == 0) {
                    messagesArea.setText("No investments corresponding to the search criteria");
                }
            } else { // lowPrice <price < highPrice
                for (int i = 0; i <= this.investment.size() - 1; i++) {
                    double tempPrice = this.investment.get(i).getPrice();
                    if (tempPrice >= lowPrice && tempPrice <= highPrice) {
                        StringTokenizer words = new StringTokenizer(keywords);
                        tokens = new String[words.countTokens()];
                        while (words.hasMoreTokens()) {
                            tokens[k++] = words.nextToken();
                        }

                        boolean stillMatch = true;
                        for (j = 0; j < k; j++) {
                            //System.out.println("Comparing against tokens: " + tokens[j] + "comparin with: " + this.investment.get(i).getName().toLowerCase());
                            if (stillMatch == true) {
                                //System.out.println("still match true");
                                int matching = this.investment.get(i).getName().toLowerCase().indexOf(tokens[j].toLowerCase());
                                if (matching == -1) {
                                    //System.out.println("Uh oh not matching anymore");
                                    stillMatch = false;
                                }
                            }
                        }
                        if (stillMatch == true) {
                            if (this.investment.get(i).getSymbol().equals(symbol)) {
                                printInvestmentToArea(i);
                                g++;
                            } else {
                                System.out.println("This current investment does not match");
                            }

                        } else {
                            System.out.println("This current investment does not match");
                        }
                    }
                }
                if (g == 0) {
                    messagesArea.setText("No investments corresponding to the search criteria");
                }
            }

        }
    }

   /**
    * This method displays all the investment to the textArea 
    */
    public void printAllToArea() {
        for (int i = 0; i <= this.investment.size() - 1; i++) {
            messagesArea.append(this.investment.get(i).toString() + "\n");
        }
    }
    
    /**
     * This method displays a specific investment to the text area
     * @param i represents the location of the investment to be retrieved in the arrayList
     */
    public void printInvestmentToArea(int i) {
        messagesArea.append(this.investment.get(i).toString() + "\n");
    }

    /**
     * This method is used to verify if the string of correct format
     * @param input represents the string that needs to verify if it is in the correct format
     * @param format represents the case we want to branch on
     * @return true if the string is of correct format and false if it is not of the correct format
     */
    public boolean validateFormat(String input, int format) {
        switch (format) {
            case SF_NONEMPTY:
                return (!input.isEmpty());
            case SF_ISINT:
                if (input.isEmpty()) {
                    return false;
                } else {
                    try {
                        int temp = Integer.parseInt(input.trim());
                        if (temp >= 0) {
                            return true;
                        } else {
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Uh oh integer?");
                        return false;
                    }
                }
            case SF_ISDOUBLE:
                if (input.isEmpty()) {
                    return false;
                } else {
                    try {
                        double temp = Double.parseDouble(input.trim());
                        if (temp >= 0.0) {
                            return true;
                        } else {
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Uh oh double?");
                        return false;
                    }
                }
            case SF_ISDOUBLE2:
                if (input.isEmpty()) {
                    return true;
                } else {
                    try {
                        double temp = Double.parseDouble(input.trim());
                        if (temp >= 0.0) {
                            return true;
                        } else {
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Uh oh double?");
                        return false;
                    }
                }
            default:
                System.out.print("Something is wrong!");
                return false;
        }

    }

    /**
     * This method is used to set up the interface for "Buying an investment"
     */
    public void buyGui() {
        buyPanel = new JPanel();
        // buyPanel.setBackground(Color.RED);
        commandsPanel.setVisible(false);
        sellPanel.setVisible(false);
        updatePanel.setVisible(false);
        getGainPanel.setVisible(false);
        searchPanel.setVisible(false);
        bigPanel.add(buyPanel, BorderLayout.CENTER);

        buyPanel.setLayout(new BorderLayout());
        JLabel buyInvest = new JLabel("Buying an investment");
        buyPanel.add(buyInvest, BorderLayout.NORTH);

        String[] labels = {"Symbol ", "Name ", "Quantity ", "Price "};
        int numPairs = labels.length;

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel type = new JLabel("Type ");
        infoPanel.add(type, gbc);
        gbc.gridy++;

        for (int i = 0; i < numPairs; i++) {
            JLabel label = new JLabel(labels[i]);
            infoPanel.add(label, gbc);
            gbc.gridy++;
        }

        gbc.gridx++;
        gbc.gridy = 0;
        investmentsType = new JComboBox<>();
        investmentsType.addItem("Stock");
        investmentsType.addItem("Mutual Funds");
        investmentsType.addActionListener(new TypeListener());
        infoPanel.add(investmentsType, gbc);
        gbc.gridy++;

        symbolField = new JTextField(20);
        infoPanel.add(symbolField, gbc);
        gbc.gridy++;
        nameField = new JTextField(20);
        infoPanel.add(nameField, gbc);
        gbc.gridy++;
        quantityField = new JTextField(20);
        infoPanel.add(quantityField, gbc);
        gbc.gridy++;
        priceField = new JTextField(20);
        infoPanel.add(priceField, gbc);
        gbc.gridy++;

        /* for (int i = 0; i < numPairs; i++){
            JTextField field = new JTextField(10);
            infoPanel.add(field, gbc);
            gbc.gridy++;
            }*/
        buyPanel.add(infoPanel, BorderLayout.CENTER);

        JPanel outerPanel = new JPanel();
        JPanel innerPanel = new JPanel();
        // outerPanel.setPreferredSize(new Dimension(160,160));         
        outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.LINE_AXIS));
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.PAGE_AXIS));

        outerPanel.add(Box.createHorizontalStrut(20));
        outerPanel.add(innerPanel);
        outerPanel.add(Box.createHorizontalStrut(20));

        innerPanel.add(Box.createVerticalStrut(20));
        JButton resetButton = new JButton("Reset");
        Dimension d = new Dimension(75, 20);
        resetButton.setSize(d);
        resetButton.setMinimumSize(d);
        resetButton.setMaximumSize(d);
        resetButton.setPreferredSize(d);
        resetButton.addActionListener(new ResetBListener());
        JButton buyButton = new JButton("Buy");
        buyButton.setSize(d);
        buyButton.setMinimumSize(d);
        buyButton.setMaximumSize(d);
        buyButton.setPreferredSize(d);
        buyButton.addActionListener(new BuyButtonListener());
        innerPanel.add(resetButton);
        innerPanel.add(Box.createVerticalStrut(40));
        innerPanel.add(buyButton);
        buyPanel.add(outerPanel, BorderLayout.EAST);

        scrollDisplay(1);
    }

    /**
     * This method is used to set up the interface for "Selling investment"
     */
    public void sellGui() {
        sellPanel = new JPanel();
        commandsPanel.setVisible(false);
        buyPanel.setVisible(false);
        updatePanel.setVisible(false);
        getGainPanel.setVisible(false);
        searchPanel.setVisible(false);
        bigPanel.add(sellPanel, BorderLayout.CENTER);

        sellPanel.setLayout(new BorderLayout());
        JLabel sellInvest = new JLabel("Selling an investment");
        sellPanel.add(sellInvest, BorderLayout.NORTH);

        String[] labels = {"Symbol ", "Quantity ", "Price "};
        int numPairs = labels.length;

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        for (int i = 0; i < numPairs; i++) {
            JLabel label = new JLabel(labels[i]);
            infoPanel.add(label, gbc);
            gbc.gridy++;
        }

        gbc.gridx++;
        gbc.gridy = 0;

        symbolField = new JTextField(20);
        infoPanel.add(symbolField, gbc);
        gbc.gridy++;
        quantityField = new JTextField(20);
        infoPanel.add(quantityField, gbc);
        gbc.gridy++;
        priceField = new JTextField(20);
        infoPanel.add(priceField, gbc);
        gbc.gridy++;

        sellPanel.add(infoPanel, BorderLayout.CENTER);

        JPanel outerPanel = new JPanel();
        JPanel innerPanel = new JPanel();
        // outerPanel.setPreferredSize(new Dimension(160,160));         
        outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.LINE_AXIS));
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.PAGE_AXIS));

        outerPanel.add(Box.createHorizontalStrut(20));
        outerPanel.add(innerPanel);
        outerPanel.add(Box.createHorizontalStrut(20));

        innerPanel.add(Box.createVerticalStrut(20));
        JButton resetButton = new JButton("Reset");
        Dimension d = new Dimension(75, 20);
        resetButton.setSize(d);
        resetButton.setMinimumSize(d);
        resetButton.setMaximumSize(d);
        resetButton.setPreferredSize(d);
        resetButton.addActionListener(new ResetSListener());
        JButton sellButton = new JButton("Sell");
        sellButton.setSize(d);
        sellButton.setMinimumSize(d);
        sellButton.setMaximumSize(d);
        sellButton.setPreferredSize(d);
        sellButton.addActionListener(new SellButtonListener());
        innerPanel.add(resetButton);
        innerPanel.add(Box.createVerticalStrut(40));
        innerPanel.add(sellButton);
        sellPanel.add(outerPanel, BorderLayout.EAST);

        scrollDisplay(2);

    }

    /**
     * This method sets up the scrolled text area for display
     * @param choice indicated whether the scrolled text area is for buying, selling, updating or searching
     */
    public void scrollDisplay(int choice) {
        JPanel messagesPanel = new JPanel();
        JPanel messagesBox = new JPanel();
        messagesPanel.setBackground(Color.LIGHT_GRAY);
        messagesPanel.setLayout(new BorderLayout());
        messagesBox.setLayout(new BorderLayout());
        //messagesPanel.setPreferredSize(new Dimension(75,75));
        JLabel messagesLabel = new JLabel("Messages");
        messagesPanel.add(messagesLabel, BorderLayout.NORTH);
        messagesPanel.add(messagesBox, BorderLayout.SOUTH);

        messagesArea = new JTextArea(15, 25);
        messagesArea.setEditable(false);
        messagesArea.setBackground(Color.WHITE);
        messagesBox.setPreferredSize(new Dimension(175, 175));
        scrolledMessages = new JScrollPane(messagesArea);
        scrolledMessages.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrolledMessages.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrolledMessages.setPreferredSize(new Dimension(175, 175));
        messagesBox.add(scrolledMessages, BorderLayout.SOUTH);
        if (choice == 1) {
            buyPanel.add(messagesPanel, BorderLayout.SOUTH);
        } else if (choice == 2) {
            sellPanel.add(messagesPanel, BorderLayout.SOUTH);
        } else if (choice == 3) {
            updatePanel.add(messagesPanel, BorderLayout.SOUTH);
        } else if (choice == 4) {
            JLabel newLabel = new JLabel("Search results");
            messagesPanel.add(newLabel, BorderLayout.NORTH);
            searchPanel.add(messagesPanel, BorderLayout.SOUTH);
        }

    }

    /**
     * This method sets up the interface for "Updating investment"
     */
    public void updateGui() {
        this.type = null;
        updatePanel = new JPanel();
        commandsPanel.setVisible(false);
        buyPanel.setVisible(false);
        sellPanel.setVisible(false);
        getGainPanel.setVisible(false);
        searchPanel.setVisible(false);
        bigPanel.add(updatePanel, BorderLayout.CENTER);

        updatePanel.setLayout(new BorderLayout());
        JLabel updateInvest = new JLabel("Updating investments");
        updatePanel.add(updateInvest, BorderLayout.NORTH);

        String[] labels = {"Symbol ", "Name ", "Price "};
        int numPairs = labels.length;

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        for (int i = 0; i < numPairs; i++) {
            JLabel label = new JLabel(labels[i]);
            infoPanel.add(label, gbc);
            gbc.gridy++;
        }

        gbc.gridx++;
        gbc.gridy = 0;

        symbolField = new JTextField(20);
        symbolField.setEditable(false);
        infoPanel.add(symbolField, gbc);
        gbc.gridy++;
        nameField = new JTextField(20);
        nameField.setEditable(false);
        infoPanel.add(nameField, gbc);
        gbc.gridy++;
        priceField = new JTextField(20);
        infoPanel.add(priceField, gbc);
        gbc.gridy++;
        updatePanel.add(infoPanel, BorderLayout.CENTER);

        JPanel outerPanel = new JPanel();
        JPanel innerPanel = new JPanel();
        // outerPanel.setPreferredSize(new Dimension(160,160));         
        outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.LINE_AXIS));
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.PAGE_AXIS));

        outerPanel.add(Box.createHorizontalStrut(10));
        outerPanel.add(innerPanel);
        outerPanel.add(Box.createHorizontalStrut(10));

        innerPanel.add(Box.createVerticalStrut(10));
        prevButton = new JButton("Prev");
        Dimension d = new Dimension(75, 20);
        prevButton.setSize(d);
        prevButton.setMinimumSize(d);
        prevButton.setMaximumSize(d);
        prevButton.setPreferredSize(d);
        prevButton.addActionListener(new PrevButtonListener());
        nextButton = new JButton("Next");
        nextButton.setSize(d);
        nextButton.setMinimumSize(d);
        nextButton.setMaximumSize(d);
        nextButton.setPreferredSize(d);
        nextButton.addActionListener(new NextButtonListener());
        JButton saveButton = new JButton("Save");
        saveButton.setSize(d);
        saveButton.setMinimumSize(d);
        saveButton.setMaximumSize(d);
        saveButton.setPreferredSize(d);
        saveButton.addActionListener(new SaveButtonListener());
        innerPanel.add(prevButton);
        innerPanel.add(Box.createVerticalStrut(20));
        innerPanel.add(nextButton);
        innerPanel.add(Box.createVerticalStrut(20));
        innerPanel.add(saveButton);
        updatePanel.add(outerPanel, BorderLayout.EAST);

        scrollDisplay(3);
        count = 0;
        if (this.investment.isEmpty()) {
            messagesArea.setText("MESSAGE: The Portfolio is currently empty.\nYou won't be able to search!");
            prevButton.setEnabled(false);
            nextButton.setEnabled(false);
            saveButton.setEnabled(false);
        } else {
            symbolField.setText(this.investment.get(0).getSymbol());
            nameField.setText(this.investment.get(0).getName());
            prevButton.setEnabled(false);
            if (this.investment.size() == 1) {
                nextButton.setEnabled(false);
            }
        }
    }

    /**
     * This is used to set up the interface for "Search investment"
     */
    public void searchGui() {
        searchPanel = new JPanel();
        commandsPanel.setVisible(false);
        buyPanel.setVisible(false);
        sellPanel.setVisible(false);
        getGainPanel.setVisible(false);
        updatePanel.setVisible(false);
        bigPanel.add(searchPanel, BorderLayout.CENTER);

        searchPanel.setLayout(new BorderLayout());
        JLabel searchLabel = new JLabel("Searching investments");
        searchPanel.add(searchLabel, BorderLayout.NORTH);

        String[] labels = {"Symbol ", "Name keywords ", "Low Price ", "High Price "};
        int numPairs = labels.length;

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        for (int i = 0; i < numPairs; i++) {
            JLabel label = new JLabel(labels[i]);
            infoPanel.add(label, gbc);
            gbc.gridy++;
        }

        gbc.gridx++;
        gbc.gridy = 0;
        symbolField = new JTextField(20);
        infoPanel.add(symbolField, gbc);
        gbc.gridy++;
        keywordsField = new JTextField(20);
        infoPanel.add(keywordsField, gbc);
        gbc.gridy++;
        lowPriceField = new JTextField(20);
        infoPanel.add(lowPriceField, gbc);
        gbc.gridy++;
        highPriceField = new JTextField(20);
        infoPanel.add(highPriceField, gbc);
        gbc.gridy++;
        searchPanel.add(infoPanel, BorderLayout.CENTER);

        JPanel outerPanel = new JPanel();
        JPanel innerPanel = new JPanel();
        // outerPanel.setPreferredSize(new Dimension(160,160));         
        outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.LINE_AXIS));
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.PAGE_AXIS));

        outerPanel.add(Box.createHorizontalStrut(20));
        outerPanel.add(innerPanel);
        outerPanel.add(Box.createHorizontalStrut(20));

        innerPanel.add(Box.createVerticalStrut(20));
        JButton resetButton = new JButton("Reset");
        Dimension d = new Dimension(75, 20);
        resetButton.setSize(d);
        resetButton.setMinimumSize(d);
        resetButton.setMaximumSize(d);
        resetButton.setPreferredSize(d);
        resetButton.addActionListener(new ResetSearchListener());
        JButton searchButton = new JButton("Search");
        searchButton.setSize(d);
        searchButton.setMinimumSize(d);
        searchButton.setMaximumSize(d);
        searchButton.setPreferredSize(d);
        searchButton.addActionListener(new SearchButtonListener());
        innerPanel.add(resetButton);
        innerPanel.add(Box.createVerticalStrut(40));
        innerPanel.add(searchButton);
        searchPanel.add(outerPanel, BorderLayout.EAST);

        scrollDisplay(4);

        if (this.investment.isEmpty()) {
            searchButton.setEnabled(false);
            messagesArea.setText("MESSAGE: The portfolio is empty and thus you cannot search through the portfolio");
        }
    }

    /**
     * This is used to set up the interface for "Get total gains"
     */
    public void GainGui() {
        getGainPanel = new JPanel();
        commandsPanel.setVisible(false);
        buyPanel.setVisible(false);
        sellPanel.setVisible(false);
        updatePanel.setVisible(false);
        searchPanel.setVisible(false);
        bigPanel.add(getGainPanel, BorderLayout.CENTER);

        getGainPanel.setLayout(new BorderLayout());
        JLabel gainLabel = new JLabel("Getting total gain");
        getGainPanel.add(gainLabel, BorderLayout.NORTH);

        String[] labels = {"Total gain "};
        int numPairs = labels.length;

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        for (int i = 0; i < numPairs; i++) {
            JLabel label = new JLabel(labels[i]);
            infoPanel.add(label, gbc);
            gbc.gridy++;
        }

        gbc.gridx++;
        gbc.gridy = 0;
        getGainField = new JTextField(20);
        getGainField.setEditable(false);
        infoPanel.add(getGainField, gbc);
        gbc.gridy++;

        getGainPanel.add(infoPanel, BorderLayout.CENTER);

        JPanel messagesPanel = new JPanel();
        JPanel messagesBox = new JPanel();
        messagesPanel.setBackground(Color.LIGHT_GRAY);
        messagesPanel.setLayout(new BorderLayout());
        messagesBox.setLayout(new BorderLayout());
        //messagesPanel.setPreferredSize(new Dimension(75,75));
        JLabel messagesLabel = new JLabel("Individual gains");
        messagesPanel.add(messagesLabel, BorderLayout.NORTH);
        messagesPanel.add(messagesBox, BorderLayout.SOUTH);

        messagesArea = new JTextArea(400, 400);
        messagesArea.setEditable(false);
        messagesArea.setBackground(Color.WHITE);
        messagesBox.setPreferredSize(new Dimension(250, 250));
        messagesArea.setLineWrap(true);
        messagesArea.setWrapStyleWord(true);
        scrolledMessages = new JScrollPane(messagesArea);
        scrolledMessages.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrolledMessages.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrolledMessages.setPreferredSize(new Dimension(250, 250));
        messagesBox.add(scrolledMessages, BorderLayout.SOUTH);

        getGainPanel.add(messagesPanel, BorderLayout.SOUTH);

        double sumOfStockGain = 0.0;
        double sumOfFundsGain = 0.0;
        double totalGain = 0.0;

        for (int i = 0; i <= this.investment.size() - 1; i++) {
            int tempQty = this.investment.get(i).getQuantity();
            double tempPrice = this.investment.get(i).getPrice();
            double tempBkValue = this.investment.get(i).getBookValue();

            if (this.investment.get(i).getClass().getCanonicalName().equals("assignment2.Stock")) {
                Stock s = new Stock();
                double tempGain = s.computeStockGain(tempQty, tempPrice, tempBkValue);
                messagesArea.append(this.investment.get(i).toString() + "Gain = " + tempGain + "\n\n");
                sumOfStockGain = sumOfStockGain + tempGain;
            }
            if (this.investment.get(i).getClass().getCanonicalName().equals("assignment2.MutualFunds")) {
                MutualFunds m = new MutualFunds();
                double tempGain = m.computeFundsGain(tempQty, tempPrice, tempBkValue);
                messagesArea.append(this.investment.get(i).toString() + "Gain = " + tempGain + "\n\n");
                sumOfFundsGain = sumOfFundsGain + tempGain;
            }
        }
        totalGain = sumOfStockGain + sumOfFundsGain;
        String total = Double.toString(totalGain);
        getGainField.setText(total);

    }

    /**
     * Constructor to set up the JFrame and to set up the first window "Welcome to the Portfolio" and 
     * used to implement a menu bar so as to move to other interfaces such as "Buying Investments" interface
     */
    public Portfolio() {
        super("Investment Portfolio");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JMenu commandsMenu = new JMenu("Commands");

        JMenuItem buy = new JMenuItem("Buy");
        buy.addActionListener(new BuyListener());
        commandsMenu.add(buy);

        JMenuItem sell = new JMenuItem("Sell");
        //sell.addActionListener(this);
        sell.addActionListener(new SellListener());
        commandsMenu.add(sell);

        JMenuItem update = new JMenuItem("Update");
        update.addActionListener(new UpdateListener());
        commandsMenu.add(update);

        JMenuItem getGain = new JMenuItem("GetGain");
        getGain.addActionListener(new GetGainListener());
        commandsMenu.add(getGain);

        JMenuItem search = new JMenuItem("Search");
        search.addActionListener(new SearchListener());
        commandsMenu.add(search);

        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(new QuitListener());
        commandsMenu.add(quit);

        JMenuBar commandsBar = new JMenuBar();
        commandsBar.add(commandsMenu);
        setJMenuBar(commandsBar);

        bigPanel = new JPanel();
        bigPanel.setLayout(new BorderLayout());
        commandsPanel = new JPanel();
        buyPanel = new JPanel();
        sellPanel = new JPanel();
        updatePanel = new JPanel();
        getGainPanel = new JPanel();
        searchPanel = new JPanel();
        commandsPanel.setLayout(new GridLayout(2, 1));
        JLabel welcomeLabel = new JLabel("Welcome to the Investment Portfolio");
        commandsPanel.setBackground(Color.WHITE);
        commandsPanel.add(welcomeLabel);

        JTextArea welcomeArea = new JTextArea(10, 10);
        welcomeArea.setEditable(false);
        welcomeArea.setLineWrap(true);
        welcomeArea.setText("Choose a command from the \"Commands\" menu to buy or sell an investment,update prices for all investment, get gain for the portfolio, search for relevant investment, or quit the program");
        commandsPanel.add(welcomeArea);
        bigPanel.add(commandsPanel, BorderLayout.CENTER);
        add(bigPanel, BorderLayout.CENTER);

        //Things related to investments
        this.investment = new ArrayList<>();
    }

    /**
     * Accessor to get accessed to the arrayList investment
     *
     * @return the content of investment
     */
    public ArrayList<Investment> getInvestment() {
        return this.investment;
    }

    /**
     * This function writes the investments one by one to a file
     *
     * @param tempFilename
     * @return a string to denotes whether the investments have been saved to a
     * file called Investments.txt
     */
    public String saveInvestments(String tempFilename) {
        // String filename = "Investments.txt";
        BufferedWriter writer;

        System.out.println("Quiting and saving the contents in a file. . .");
        if (tempFilename.length() == 0) {
            tempFilename = "Investments.txt";
        }

        try {
            writer = new BufferedWriter(new FileWriter(tempFilename));
            for (int i = 0; i < this.investment.size(); i++) {
                if (this.investment.get(i).getClass().getCanonicalName().equals("assignment2.Stock")) {
                    writer.write("type = stock\n");
                    writer.newLine();
                    writer.write("symbol = " + investment.get(i).getSymbol());
                    writer.newLine();
                    writer.write("name = " + investment.get(i).getName());
                    writer.newLine();
                    writer.write("quantity = " + investment.get(i).getQuantity());
                    writer.newLine();
                    writer.write("price = " + investment.get(i).getPrice());
                    writer.newLine();
                    writer.write("bookValue = " + investment.get(i).getBookValue());
                    writer.newLine();
                    writer.newLine();
                } else if (this.investment.get(i).getClass().getCanonicalName().equals("assignment2.MutualFunds")) {
                    writer.write("type = mutualfund");
                    writer.newLine();
                    writer.write("symbol = " + investment.get(i).getSymbol());
                    writer.newLine();
                    writer.write("name = " + investment.get(i).getName());
                    writer.newLine();
                    writer.write("quantity = " + investment.get(i).getQuantity());
                    writer.newLine();
                    writer.write("price = " + investment.get(i).getPrice());
                    writer.newLine();
                    writer.write("bookValue = " + investment.get(i).getBookValue());
                    writer.newLine();
                    writer.newLine();
                }

            }
            writer.close();
        } catch (IOException e) {
            return "Failed to write to " + tempFilename;
        }

        return "Sucessfully wrote to the file. . . " + tempFilename;
    }

    /**
     * This method loads the contents of a file and save it to the arrayList for
     * all the investments
     *
     * @param filename which represents the file name which obtained from the
     * command line
     * @return a string which denotes whether the file has been successfully
     * opened or not
     */
    public String loadInvestments(String filename) {
        BufferedReader reader;
        int counter = 0;
        int typeFile = 0;
        String symbolFile = "";
        String nameFile = "";
        String tempQuantity = "";
        String tempPrice = "";
        String tempBookValue = "";
        int quantityFile = 0;
        double priceFile = 0.0;
        double bookValue = 0.0;

        System.out.println("Loading the contents of the file. . .");

        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = "";
            while (line != null) {
                line = reader.readLine();
                //count++;
                //  System.out.println("Is reading the file and count is " + count);
                // System.out.println("THE LINE BEING READ IS " + line);
                if (line != null && line.length() != 0) {
                    counter++;
                    switch (counter) {
                        case 1:
                            typeFile = getTypeFromLine(line);
                            break;
                        case 2:
                            symbolFile = getDataFromLine(line);
                            //  System.out.println("symbol is " + symbol);
                            break;
                        case 3:
                            nameFile = getDataFromLine(line);
                            //  System.out.println("name is " + name);
                            break;
                        case 4:
                            tempQuantity = getDataFromLine(line);
                            //  System.out.println("tempQuantity is " + tempQuantity);
                            try {
                                quantityFile = Integer.parseInt(tempQuantity);
                            } catch (NumberFormatException n) {
                                //     System.out.println("LoadInvestment function problem with quantity");
                            }
                            break;
                        case 5:
                            tempPrice = getDataFromLine(line);
                            //  System.out.println("tempPrice is " + tempPrice);
                            try {
                                priceFile = Double.parseDouble(tempPrice);
                            } catch (NumberFormatException n) {
                                //      System.out.println("LoadInvestment function problem with price");
                            }
                            break;
                        case 6:
                            tempBookValue = getDataFromLine(line);
                            //   System.out.println("tempBook is " + tempBookValue);
                            try {
                                bookValue = Double.parseDouble(tempBookValue);
                            } catch (NumberFormatException n) {
                                //      System.out.println("LoadInvestment function problem with bookvalue");
                            }

                            if (typeFile == 1) { //means stock
                                Investment in = getBook(symbolFile, nameFile, quantityFile, priceFile, bookValue);
                                this.investment.add(in);
                                //  System.out.print(this.investment.get(0).toString());
                                //  System.out.println("OKKK");
                            } else if (typeFile == 2) { //mutualfund
                                MutualFunds tempFund = new MutualFunds(symbolFile, nameFile, quantityFile, priceFile, bookValue);
                                this.investment.add(tempFund);
                            } else {
                                System.out.println("Error in getting the type 1 is for stock and 2 is for funds");
                            }
                            counter = 0;
                            break;
                        default:
                            System.out.println("Not supposed to branch here while reading file");
                            counter = 0;
                            break;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            return "Failed to read this file or file does not exist yet.";

        }

        return "Successfully read the file. . .";
    }

    /**
     * This function creates the stock object
     *
     * @param symbol represents the symbol of the investment
     * @param name represents the name of the investment
     * @param quantity represents the quantity of the stock investment
     * @param price denotes the price at which the stocks were bought
     * @param bookValue represents the boolValue of the stock investment
     * @return an obj investment which is actually a stock
     */
    private Investment getBook(String symbol, String name, int quantity, double price, double bookValue) {
        return new Stock(symbol, name, quantity, price, bookValue);
    }

    /**
     * This function allows the user to know whether this investment is a stock
     * or an investment
     *
     * @param line which is read from the file
     * @return an integer to denote whether it is a stock or a mutual fund; 1
     * for stock and 2 for fund
     */
    public int getTypeFromLine(String line) {
        String[] splitLine = line.split("=");

        switch (splitLine[1].trim()) {
            case "stock":
                // System.out.println("yey stock");
                return 1;
            case "mutualfund":
                // System.out.println("yey funds");
                return 2;
            default:
                System.out.println("Error. This type of investment does not exist. Investment read: " + splitLine[1].trim());
        }
        return 0;
    }

    /**
     * This function allows the user to retrieve data such as symbol, name,
     * price, bookValue and quantity
     *
     * @param line which is read from the file
     * @return a respective string if it is a symbol, name, quantity or price or
     * bookValue
     */
    public String getDataFromLine(String line) {
        String[] splitLine = line.split("=");

        switch (splitLine[0].trim()) {
            case "symbol":
                String tempSymbol = splitLine[1].trim();
                //  System.out.println("the split is " + splitLine[1].trim());
                return tempSymbol;
            case "name":
                String tempName = splitLine[1].trim();
                //  System.out.println("the split is " + splitLine[1].trim());
                return tempName;
            case "quantity":
                String tempQuantity = splitLine[1].trim();
                //   System.out.println("the split is " + splitLine[1].trim());
                return tempQuantity;
            case "price":
                String tempPrice = splitLine[1].trim();
                //   System.out.println("the split is " + splitLine[1].trim());
                return tempPrice;
            case "bookValue":
                String bookValue = splitLine[1].trim();
                //  System.out.println("the split is " + splitLine[1].trim());
                return bookValue;
            default:
                // System.out.println("Never supposed to branch on default. Error somewhere");
                break;
        }
        return "There is an error somewhere in getDataFromLine Function";
    }

    public static void main(String[] args) {
        Portfolio gui = new Portfolio();
        gui.setVisible(true);
        filename = "Investments.txt";
        if (args.length != 0) {
            filename = args[0];
            System.out.println("the filename: " + filename + " and thre args: " + args[0]);
            gui.loadInvestments(filename);
        }
    }

}

