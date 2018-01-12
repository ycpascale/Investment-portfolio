/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

/**
 *
 * @author Kim
 */
public class MutualFunds extends Investment{
    
    public static final double REDEMPTION = 9.99;
   /* private String symbol;
    private String name;
    private int quantity;
    private double price;
    private double bookValue;*/
     
    public MutualFunds(String symbol, String name, int quantity, double price, double bookValue){
        super(symbol,name,quantity,price,bookValue);
    }
    
    public MutualFunds(){
        super();
    }
    
    
    /**
     *
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other){
        if (other == this) return true;
        if (!this.getClass().equals(other.getClass())) return false;
        
        MutualFunds mf = (MutualFunds) other;
        return super.equals(other);
    }        
            
    @Override
    public String toString(){
       // return name + " " + symbol + "\n" + "Quantity: " + quantity + "  Price: $" + price + "\nBook Value: $" + bookValue;
       return super.toString();
        
    }
    
    public double computeBookValueForBuying(int amount, double pricing){
        double theBookValue = amount * pricing;
        
        return theBookValue;
    }
    
      public double computeBookValueForSelling(int initialAmount, int remainingAmount, double initialBookValue){
        double finalBookValue = initialBookValue * (remainingAmount/initialAmount);
        
        return finalBookValue;
    }
    
    public double computePayment(int amount, double pricing){
        double payment = amount * pricing - REDEMPTION;
        
        return payment;
    }
    
    public double computeFundsGain(int currentQty, double updatedPrice, double theBookValue){
        double gain = (currentQty * updatedPrice - REDEMPTION) - theBookValue;
        
        return gain;
    }
    
}
