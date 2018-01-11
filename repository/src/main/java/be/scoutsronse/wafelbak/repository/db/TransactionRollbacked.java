package be.scoutsronse.wafelbak.repository.db;

public class TransactionRollbacked {

    private boolean readOnly;

    private TransactionRollbacked(boolean readOnly) {
        this.readOnly = readOnly;
    }

    static TransactionRollbacked transactionRollbacked(boolean readOnly) {
        return new TransactionRollbacked(readOnly);
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransactionRollbacked that = (TransactionRollbacked) o;
        return readOnly == that.readOnly;

    }

    @Override
    public int hashCode() {
        return (readOnly ? 1 : 0);
    }
}