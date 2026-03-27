package com.softwareanalytics.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for BankAccount (~70% coverage).
 * Intentionally leaves some paths uncovered for Software Analytics course
 * exercise.
 */
@DisplayName("BankAccount Tests")
class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount("Alice", 100.0);
    }

    @Nested
    @DisplayName("Constructor")
    class ConstructorTests {

        @Test
        @DisplayName("valid arguments: asserts owner, balance, and isFrozen")
        void constructorWithValidArguments() {
            BankAccount acc = new BankAccount("Bob", 250.50);

            assertEquals("Bob", acc.getOwner());
            assertEquals(250.50, acc.getBalance());
            assertFalse(acc.isFrozen());
        }
    }

    @Nested
    @DisplayName("deposit")
    class DepositTests {

        @Test
        @DisplayName("happy path: exact balance after deposit")
        void depositHappyPath() {
            account.deposit(50.0);

            assertEquals(150.0, account.getBalance());
        }

        @Test
        @DisplayName("multiple times: exact cumulative balance")
        void depositMultipleTimes() {
            account.deposit(25.0);
            account.deposit(75.0);
            account.deposit(10.0);

            assertEquals(210.0, account.getBalance());
        }
    }

    @Nested
    @DisplayName("withdraw")
    class WithdrawTests {

        @Test
        @DisplayName("happy path: exact balance after withdrawal")
        void withdrawHappyPath() {
            account.withdraw(30.0);

            assertEquals(70.0, account.getBalance());
        }

        @Test
        @DisplayName("exact balance to zero")
        void withdrawExactBalanceToZero() {
            account.withdraw(100.0);

            assertEquals(0.0, account.getBalance());
        }

        @Test
        @DisplayName("insufficient funds: throws IllegalArgumentException")
        void withdrawInsufficientFunds() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> account.withdraw(150.0));
            assertEquals("insufficient funds", ex.getMessage());
        }

        @Test
        @DisplayName("negative amount: throws IllegalArgumentException")
        void withdrawNegativeAmount() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> account.withdraw(-10.0));
            assertEquals("amount must be positive", ex.getMessage());
        }
    }

    @Nested
    @DisplayName("freeze")
    class FreezeTests {

        @Test
        @DisplayName("freeze and check")
        void freezeTest() {
            account.freeze();

            assertTrue(account.isFrozen());
        }
    }

    @Nested
    @DisplayName("unfreeze")
    class UnfreezeTests {

        @Test
        @DisplayName("unfreeze and check")
        void unfreezeTest() {
            account.unfreeze();

            assertFalse(account.isFrozen());
        }
    }

    @Nested
    @DisplayName("transfer")
    class TransferTests {

        @Test
        @DisplayName("null target: throws IllegalArgumentException")
        void transferNullTarget() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> account.transfer(null, 1));
            assertEquals("target cannot be null", ex.getMessage());
        }

        @Test
        @DisplayName("self target: throws IllegalArgumentException")
        void transferSelfTarget() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> account.transfer(account, 1));
            assertEquals("cannot transfer to self", ex.getMessage());
        }

        @Test
        @DisplayName("happy path: transfer works")
        void transferHappyPath() {
            BankAccount acc2 = new BankAccount("Bob", 0);
            account.transfer(acc2, 10);

            assertEquals(10, acc2.getBalance());
        }
    }
}
