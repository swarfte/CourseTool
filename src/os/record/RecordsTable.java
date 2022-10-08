package os.record;

import java.util.ArrayList;
import java.util.List;

/**
 * @author swarfte
 */

public class RecordsTable {
    private final List<Records> recordsList;

    public RecordsTable() {
        this.recordsList = new ArrayList<>();
    }

    public RecordsTable(List<Records> recordsList) {
        this.recordsList = recordsList;
    }

    public List<Records> getRecordsList() {
        return this.recordsList;
    }

    public void add(Records records) {
        this.recordsList.add(records);
    }

    public boolean isEmpty() {
        return this.recordsList.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Records records : this.recordsList) {
            sb.append(records.toString()).append("\n");
        }
        return sb.toString();
    }
}
