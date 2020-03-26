package transactions;

import transactions.Trader;
import transactions.Transaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Dieter Holz
 */
public class TransactionList {
    private final List<Transaction> allTransactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {

        allTransactions.add(transaction);
    }

    public int size() {

        return allTransactions.size();
    }

    /**
     * @param year the year
     * @return all transactions from 2011 ordered by value
     */
    public List<Transaction> transactionsInYear(int year) {
        return allTransactions.stream().filter(t -> t.getYear() == 2011).sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());
    }

    public List<String> cities() {
        return allTransactions.stream().map(t -> t.getTrader().getCity()).distinct().collect(Collectors.toList());
    }

    /**
     * @param city the trader's city
     * @return all traders from given city sorted by name.
     */
    public List<Trader> traders(String city) {
        return allTransactions.stream().filter(t -> t.getTrader().getCity().equals(city)).map(Transaction::getTrader).distinct().sorted(Comparator.comparing(Trader::getName)).collect(Collectors.toList());
    }

    /**
     * Returns a Map of all transactions.
     *
     * @return a Map with the year as key and a list of all transaction of this year as value
     */
    public Map<Integer, List<Transaction>> transactionsByYear() {
        return allTransactions.stream().collect(Collectors.groupingBy(Transaction::getYear));
    }

    /**
     * @param city the city
     * @return true if there are any trader based in given city
     */
    public boolean traderInCity(String city) {
        return allTransactions.stream().anyMatch(t -> t.getTrader().getCity().equals(city));
    }

    /**
     * @param from the trader's current location
     * @param to   the trader's new location
     */
    public void relocateTraders(String from, String to) {
        allTransactions.stream().filter(t -> t.getTrader().getCity().equals(from)).forEach(t -> t.getTrader().setCity(to));
    }

    /**
     * @return the highest value in all the transactions
     */
    public int highestValue() {
        return allTransactions.stream().max(Comparator.comparing(Transaction::getValue)).get().getValue();
    }

    /**
     * @return the sum of all transaction values
     */
    public int totalValue() {
        return allTransactions.stream().mapToInt(Transaction::getValue).sum();
    }

    /**
     * @return the transactions.Transaction with the lowest value
     */
    public Transaction getLowestValueTransaction(){
        return allTransactions.stream().min(Comparator.comparing(Transaction::getValue)).get();
    }

    /**
     * @return a string of all traders’ names sorted alphabetically
     */
    public String traderNames() {
        return allTransactions.stream().map(t -> t.getTrader().getName()).distinct().sorted().collect(Collectors.joining());
    }

}
