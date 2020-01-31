package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        assertTrue(BankAccount.isEmailValid("a@b.com"));

        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());

        bankAccount = new BankAccount("a@b.com", 0);

        assertEquals(0, bankAccount.getBalance());

        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", -200));
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw((300)));

        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw((20.00002)));
        bankAccount.withdraw(100);
        assertEquals(0, bankAccount.getBalance());

    }

    @Test
    void depositTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        //Invalid arguments
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit((-10)));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit((10.00002)));
        //Valid arguments
        bankAccount.deposit(100);
        assertEquals(300, bankAccount.getBalance());
        //Border case, 0
        bankAccount.deposit(0);
        assertEquals(300, bankAccount.getBalance());
    }

    @Test
    void transferTest() throws InsufficientFundsException{
        BankAccount a = new BankAccount("a@a.com", 200);
        BankAccount b = new BankAccount("b@b.com", 200);
        //Invalid arguments
        assertThrows(IllegalArgumentException.class, () -> a.transfer(b, -200));
        assertThrows(IllegalArgumentException.class, () -> a.transfer(a, 100));
        assertThrows(IllegalArgumentException.class, () -> a.transfer(b, 5.00003));

        //Valid arguments
        a.transfer(b, 100);
        assertEquals(100, a.getBalance());
        assertEquals(300, b.getBalance());
        b.transfer(a, 200);
        assertEquals(300, a.getBalance());
        assertEquals(100, b.getBalance());
        b.transfer(a, 0);
        assertEquals(300, a.getBalance());
        assertEquals(100, b.getBalance());

        //Insufficient Funds
        assertThrows(InsufficientFundsException.class, () -> a.transfer(b, 500));

    }

    @Test
    void isEmailValidTest(){
        //prefix
        assertFalse(BankAccount.isEmailValid("abc-@mail.com"));
        assertFalse(BankAccount.isEmailValid("abc..def@mail.com"));
        assertFalse(BankAccount.isEmailValid(".abc@mail.com"));
        assertFalse(BankAccount.isEmailValid("abc#def@mail.com"));
        assertTrue( BankAccount.isEmailValid("abc-d@mail.com"));
        assertTrue( BankAccount.isEmailValid("abc.def@mail.com"));
        assertTrue( BankAccount.isEmailValid("abc@mail.com"));
        assertTrue( BankAccount.isEmailValid("abc_def@mail.com"));

        //domain
        assertFalse(BankAccount.isEmailValid("abc.def@mail.c"));
        assertFalse(BankAccount.isEmailValid("abc.def@mail#archive.com"));
        assertFalse(BankAccount.isEmailValid("abc.def@mail"));
        assertFalse(BankAccount.isEmailValid("abc.def@mail..com"));
        assertTrue( BankAccount.isEmailValid("abc.def@mail.cc"));
        assertTrue( BankAccount.isEmailValid("abc.def@mail-archive.com"));
        assertTrue( BankAccount.isEmailValid("abc.def@mail.org"));
        assertTrue( BankAccount.isEmailValid("abc.def@mail.com"));

        //new equivalence class tests
        //prefix dots
        assertTrue(BankAccount.isEmailValid("a.b.c@mail.com"));
        assertFalse(BankAccount.isEmailValid(".abc@mail.com"));
        assertFalse(BankAccount.isEmailValid("a...c@mail.com"));
        assertFalse(BankAccount.isEmailValid("abc.@mail.com"));
        assertTrue(BankAccount.isEmailValid("abc@mail.com"));

        //periods dashes and underscores
        assertTrue(BankAccount.isEmailValid("a-c@mail.com"));
        assertTrue(BankAccount.isEmailValid("a_c@mail.com"));
        assertTrue(BankAccount.isEmailValid("a.c@mail.com"));
        assertFalse(BankAccount.isEmailValid("abc-@mail.com"));
        assertFalse(BankAccount.isEmailValid("abc.@mail.com"));
        assertFalse(BankAccount.isEmailValid("abc_@mail.com"));

        //@
        assertTrue(BankAccount.isEmailValid("abc@mail.com"));
        assertFalse(BankAccount.isEmailValid("abcmail.com"));
        assertFalse(BankAccount.isEmailValid("abc@@mail.com"));
        assertFalse(BankAccount.isEmailValid("a@bc@mail.com"));
        assertFalse(BankAccount.isEmailValid("abc@mail.c@om"));

        //domain
        assertTrue(BankAccount.isEmailValid("abc@mail.com"));
        assertTrue(BankAccount.isEmailValid("abc@ma-il.com"));
        assertTrue(BankAccount.isEmailValid("abc@ma-1-l.com"));
        assertTrue(BankAccount.isEmailValid("abc@ma_il.com"));
        assertTrue(BankAccount.isEmailValid("abc@ma.il.com"));
        assertFalse(BankAccount.isEmailValid("abc@mail.c"));
        assertFalse(BankAccount.isEmailValid("abc@mail."));
        assertFalse(BankAccount.isEmailValid("abc@mail"));




    }

    @Test
    void isAmountValidTest() {
        assertTrue(BankAccount.isAmountValid(20));
        assertTrue(BankAccount.isAmountValid(0));
        assertTrue(BankAccount.isAmountValid(12.5));
        assertTrue(BankAccount.isAmountValid(45.32));
        assertTrue(BankAccount.isAmountValid(123534575674564.34));
        assertFalse(BankAccount.isAmountValid(-100));
        assertFalse(BankAccount.isAmountValid(0.0004));
        assertFalse(BankAccount.isAmountValid(20.0003));
    }


    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("abc@xyz.com", -100));
    }

}