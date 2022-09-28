
import lombok.Data;

import java.util.List;

/**
 * @author ht
 */
@Data
public class JsonRootBean {

    private int total;
    private int totalHits;
    private List<Hits> hits;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public List<Hits> getHits() {
        return hits;
    }

    public void setHits(List<Hits> hits) {
        this.hits = hits;
    }
}