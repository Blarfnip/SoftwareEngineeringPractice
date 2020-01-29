package edu.ithaca.dragon.bank;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if(!isEmailValid(email)) throw new IllegalArgumentException("Email " + email + " is invalid");
        if(!isAmountValid(startingBalance)) throw new IllegalArgumentException("Starting Balance " + startingBalance + " is invalid");

        this.email = email;
        this.balance = startingBalance;
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * @throws InsufficientFundsException if amount is greater than the balance
     */
    public void withdraw (double amount) throws InsufficientFundsException {
        //Check for inputs with non-monetary fractions
        if(!isAmountValid(amount)) throw new IllegalArgumentException();

        if(balance - amount >= 0) {
            balance -= amount;
        } else {
            throw new InsufficientFundsException("");
        }
    }

    /**
     * @param amount input for validity checking
     * @return boolean denoting validity of amount
     */
    public static boolean isAmountValid(double amount) {
        return ((double)(Math.round(amount * 100)) / 100 == amount && amount >= 0);
    }

    /**
     * @param email input for validity checking
     * @return boolean denoting validity of email
     */
    public static boolean isEmailValid(String email){
        //Pattern match regex for valid email address
        String regex = "[\\w-]+(\\.[\\w]+)*(?<![-_])@[\\w-]+([.-]?[\\w]+)*(\\.[a-z]{2,})";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
